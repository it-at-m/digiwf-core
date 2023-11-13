package de.muenchen.oss.digiwf.task.polyflow.kafka;

import de.muenchen.oss.digiwf.task.envprocessor.AxonKafkaPropertiesEnvironmentPostProcessor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.axonframework.common.AxonConfigurationException;
import org.axonframework.config.EventProcessingConfigurer;
import org.axonframework.eventhandling.EventMessage;
import org.axonframework.eventhandling.PropagatingErrorHandler;
import org.axonframework.extensions.kafka.KafkaProperties;
import org.axonframework.extensions.kafka.autoconfig.KafkaAutoConfiguration;
import org.axonframework.extensions.kafka.eventhandling.DefaultKafkaMessageConverter;
import org.axonframework.extensions.kafka.eventhandling.KafkaMessageConverter;
import org.axonframework.extensions.kafka.eventhandling.producer.KafkaEventPublisher;
import org.axonframework.extensions.kafka.eventhandling.producer.KafkaPublisher;
import org.axonframework.extensions.kafka.eventhandling.producer.ProducerFactory;
import org.axonframework.serialization.Serializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import java.util.Optional;

import static org.axonframework.extensions.kafka.eventhandling.producer.KafkaEventPublisher.DEFAULT_PROCESSING_GROUP;


/**
 * Configure to send polyflow events only if kafka is not disabled (is enabled).
 */
@Profile("!" + AxonKafkaPropertiesEnvironmentPostProcessor.PROFILE_DISABLED)
@Configuration
@AutoConfigureBefore(KafkaAutoConfiguration.class) // we should run before Axon Kafka autoconfiguration
@EnableConfigurationProperties(PolyflowAxonKafkaProperties.class)
public class PolyflowAxonKafkaConfiguration {

    @ConditionalOnMissingBean
    @Bean
    public KafkaTopicRouter kafkaTopicRouter(PolyflowAxonKafkaProperties properties) {
        return payloadType -> properties.getTopics().stream()
                .filter(it -> it.getPayloadType().isAssignableFrom(payloadType))
                .findFirst()
                .map(PolyflowAxonKafkaProperties.PayloadTypeToTopic::getTopic)
                .orElse(null);
    }

    @Bean
    @Primary
    public KafkaMessageConverter<String, byte[]> routingKafkaMessageConverter(
            @Qualifier("eventSerializer") Serializer eventSerializer,
            KafkaTopicRouter kafkaTopicRouter) {
        final KafkaMessageConverter<String, byte[]> defaultConverter = DefaultKafkaMessageConverter.builder().serializer(eventSerializer).build();
        return new KafkaMessageConverter<>() {
            @Override
            public ProducerRecord<String, byte[]> createKafkaMessage(EventMessage<?> eventMessage, String topic) {
                String topicOverride = kafkaTopicRouter.topicForPayloadType(eventMessage.getPayloadType());
                return defaultConverter.createKafkaMessage(eventMessage, topicOverride == null ? topic : topicOverride);
            }

            @Override
            public Optional<EventMessage<?>> readKafkaMessage(ConsumerRecord<String, byte[]> consumerRecord) {
                return defaultConverter.readKafkaMessage(consumerRecord);
            }
        };
    }

    /**
     * Configures a KafkaEventPublisher that sends events to Kafka only if they are routed via kafka event router.
     *
     * @see org.axonframework.extensions.kafka.autoconfig.KafkaAutoConfiguration#kafkaEventPublisher(KafkaPublisher, KafkaProperties, EventProcessingConfigurer)
     */
    @Bean
    public KafkaEventPublisher<String, byte[]> routingKafkaEventPublisher(
            KafkaPublisher<String, byte[]> kafkaPublisher,
            KafkaProperties kafkaProperties,
            EventProcessingConfigurer eventProcessingConfigurer,
            KafkaTopicRouter kafkaTopicRouter
    ) {
        KafkaEventPublisher<String, byte[]> kafkaEventPublisher = RoutingKafkaEventPublisher.<String, byte[]>builder()
                .kafkaPublisher(kafkaPublisher)
                .kafkaTopicRouter(kafkaTopicRouter)
                .build();

        /*
         * Register an invocation error handler which re-throws any exception.
         * This will ensure a TrackingEventProcessor to enter the error mode which will retry, and it will ensure the
         * SubscribingEventProcessor to bubble the exception to the callee. For more information see
         *  https://docs.axoniq.io/reference-guide/configuring-infrastructure-components/event-processing/event-processors#error-handling
         */
        // TODO: Check if this still works. Our publisher is no longer in the default processing group, I think.
        eventProcessingConfigurer.registerEventHandler(configuration -> kafkaEventPublisher)
                .registerListenerInvocationErrorHandler(
                        DEFAULT_PROCESSING_GROUP, configuration -> PropagatingErrorHandler.instance()
                )
                .assignHandlerTypesMatching(
                        DEFAULT_PROCESSING_GROUP,
                        clazz -> clazz.isAssignableFrom(KafkaEventPublisher.class)
                );

        KafkaProperties.EventProcessorMode processorMode = kafkaProperties.getProducer().getEventProcessorMode();
        if (processorMode == KafkaProperties.EventProcessorMode.SUBSCRIBING) {
            eventProcessingConfigurer.registerSubscribingEventProcessor(DEFAULT_PROCESSING_GROUP);
        } else if (processorMode == KafkaProperties.EventProcessorMode.TRACKING) {
            eventProcessingConfigurer.registerTrackingEventProcessor(DEFAULT_PROCESSING_GROUP);
        } else if (processorMode == KafkaProperties.EventProcessorMode.POOLED_STREAMING) {
            eventProcessingConfigurer.registerPooledStreamingEventProcessor(DEFAULT_PROCESSING_GROUP);
        } else {
            throw new AxonConfigurationException("Unknown Event Processor Mode [" + processorMode + "] detected");
        }
        return kafkaEventPublisher;
    }

    // We need to duplicate the bean factory from KafkaAutoConfiguration because there is no way to set `publisherAckTimeout` via configuration properties
    @Bean(destroyMethod = "shutDown")
    public KafkaPublisher<String, byte[]> kafkaAcknowledgingPublisher(
            ProducerFactory<String, byte[]> kafkaProducerFactory,
            KafkaMessageConverter<String, byte[]> kafkaMessageConverter,
            org.axonframework.config.Configuration configuration,
            KafkaProperties properties,
            Serializer serializer
    ) {
        return KafkaPublisher
                .<String, byte[]>builder()
                .producerFactory(kafkaProducerFactory)
                .messageConverter(kafkaMessageConverter)
                .messageMonitor(configuration.messageMonitor(KafkaPublisher.class, "kafkaPublisher"))
                .topicResolver(m -> Optional.of(properties.getDefaultTopic()))
                .serializer(serializer)
                .publisherAckTimeout(Long.parseLong(properties.getProducer().getProperties().getOrDefault("delivery.timeout.ms", "30000")) + 1000)
                .build();
    }
}
