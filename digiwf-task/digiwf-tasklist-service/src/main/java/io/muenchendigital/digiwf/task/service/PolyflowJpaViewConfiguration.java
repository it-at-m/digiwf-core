package io.muenchendigital.digiwf.task.service;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.kotlin.KotlinModule;
import com.google.common.collect.Lists;
import io.holunda.polyflow.bus.jackson.ObjectMapperConfigurationHelper;
import io.holunda.polyflow.bus.jackson.config.FallbackPayloadObjectMapperAutoConfiguration;
import io.holunda.polyflow.view.TaskQueryClient;
import io.holunda.polyflow.view.jpa.EnablePolyflowJpaView;
import io.micrometer.core.instrument.MeterRegistry;
import io.muenchendigital.digiwf.task.PolyflowObjectMapper;
import io.muenchendigital.digiwf.task.service.ingress.AxonKafkaExtendedProperties;
import io.muenchendigital.digiwf.task.service.ingress.MetricsBindingConsumerFactory;
import org.axonframework.eventhandling.deadletter.jpa.DeadLetterEntry;
import org.axonframework.eventhandling.tokenstore.jpa.TokenEntry;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine;
import org.axonframework.extensions.kafka.KafkaProperties;
import org.axonframework.extensions.kafka.eventhandling.KafkaMessageConverter;
import org.axonframework.extensions.kafka.eventhandling.consumer.ConsumerFactory;
import org.axonframework.extensions.kafka.eventhandling.consumer.DefaultConsumerFactory;
import org.axonframework.extensions.kafka.eventhandling.consumer.Fetcher;
import org.axonframework.extensions.kafka.eventhandling.consumer.streamable.KafkaEventMessage;
import org.axonframework.extensions.kafka.eventhandling.consumer.streamable.SortedKafkaMessageBuffer;
import org.axonframework.extensions.kafka.eventhandling.consumer.streamable.StreamableKafkaMessageSource;
import org.axonframework.modelling.saga.repository.SagaStore;
import org.axonframework.modelling.saga.repository.inmemory.InMemorySagaStore;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.serialization.Serializer;
import org.axonframework.serialization.json.JacksonSerializer;
import org.axonframework.springboot.util.ConditionalOnMissingQualifiedBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@EnablePolyflowJpaView
@EntityScan(basePackageClasses = {
        TokenEntry.class, DeadLetterEntry.class
})
@EnableConfigurationProperties(AxonKafkaExtendedProperties.class)
public class PolyflowJpaViewConfiguration {

    @Value("${HOSTNAME:localhost}")
    private String hostname;


    @Bean
    @Primary
    @Qualifier(FallbackPayloadObjectMapperAutoConfiguration.PAYLOAD_OBJECT_MAPPER)
    public ObjectMapper payloadObjectMapper() {
        return PolyflowObjectMapper.DEFAULT;
    }

    @Bean
    @Qualifier("defaultAxonObjectMapper")
    public ObjectMapper defaultAxonObjectMapper() {
        return PolyflowObjectMapper.DEFAULT;
    }


    /**
     * We will receive events via Kafka, so no event storage is available in this component.
     *
     * @return in-memory storage engine, to make Axon Framework happy.
     */
    @Bean
    public EventStorageEngine inMemoryStorageEngine() {
        return new InMemoryEventStorageEngine();
    }

    /**
     * No sagas should be handled in this component.
     *
     * @return in-memory saga-store to make Axon Framework happy.
     */
    @Bean
    public SagaStore<?> inMemorySagaStore() {
        return new InMemorySagaStore();
    }


    /**
     * Initilizes the client with the query gateway.
     *
     * @param queryGateway gateway to use.
     * @return client.
     */
    @Bean
    public TaskQueryClient taskQueryClient(QueryGateway queryGateway) {
        return new TaskQueryClient(queryGateway);
    }

    /**
     * Consumer factory for tasks.
     *
     * @param properties kafka properties
     * @return consumer factory.
     */
    @Bean
    @Qualifier("polyflowTask")
    public ConsumerFactory<String, byte[]> kafkaConsumerFactoryPolyflowTask(KafkaProperties properties) {
        properties.setClientId("polyflow-task-" + hostname);
        return new DefaultConsumerFactory<>(properties.buildConsumerProperties());
    }

    /**
     * Consumer factory for data entries.
     *
     * @param properties kafka properties
     * @return consumer factory.
     */
    @Bean
    @Qualifier("polyflowData")
    public ConsumerFactory<String, byte[]> kafkaConsumerFactoryPolyflowData(KafkaProperties properties) {
        properties.setClientId("polyflow-data-" + hostname);
        return new DefaultConsumerFactory<>(properties.buildConsumerProperties());
    }

    /**
     * Creates a streamable kafka message source.
     * The name of this bean is referenced in the application.yaml.
     *
     * @param kafkaProperties      standard kafka properties.
     * @param extendedProperties   extended properties for Polyflow.
     * @param kafkaConsumerFactory consumer factory.
     * @param kafkaFetcher         fetcher instance.
     * @param serializer           serializer.
     * @param meterRegistry        meter registry.
     * @return streaming source.
     */
    @Bean("kafkaMessageSourcePolyflowData")
    @ConditionalOnProperty(value = "axon.kafka.consumer.event-processor-mode", havingValue = "TRACKING")
    public StreamableKafkaMessageSource<String, byte[]> kafkaMessageSourcePolyflowData(
            KafkaProperties kafkaProperties,
            AxonKafkaExtendedProperties extendedProperties,
            @Qualifier("polyflowData") ConsumerFactory<String, byte[]> kafkaConsumerFactory,
            Fetcher<String, byte[], KafkaEventMessage> kafkaFetcher,
            @Qualifier("eventSerializer")
            Serializer serializer,
            KafkaMessageConverter<String, byte[]> messageConverter,
            MeterRegistry meterRegistry) {
        return StreamableKafkaMessageSource
                .<String, byte[]>builder()
                .topics(Lists.newArrayList(extendedProperties.getTopicDataEntries()))
                .consumerFactory(new MetricsBindingConsumerFactory(meterRegistry, kafkaConsumerFactory))
                .consumerFactory(kafkaConsumerFactory)
                .serializer(serializer)
                .fetcher(kafkaFetcher)
                .messageConverter(messageConverter)
                .bufferFactory(() -> new SortedKafkaMessageBuffer(kafkaProperties.getFetcher().getBufferSize()))
                .build();
    }

    /**
     * Creates a streamable kafka message source.
     * The name of this bean is referenced in the application.yaml.
     *
     * @param kafkaProperties      standard kafka properties.
     * @param extendedProperties   extended properties for Polyflow.
     * @param kafkaConsumerFactory consumer factory.
     * @param kafkaFetcher         fetcher instance.
     * @param serializer           serializer.
     * @param meterRegistry        meter registry.
     * @return streaming source.
     */
    @Bean("kafkaMessageSourcePolyflowTask")
    @ConditionalOnProperty(value = "axon.kafka.consumer.event-processor-mode", havingValue = "TRACKING")
    public StreamableKafkaMessageSource<String, byte[]> kafkaMessageSourcePolyflowTask(
            KafkaProperties kafkaProperties,
            AxonKafkaExtendedProperties extendedProperties,
            @Qualifier("polyflowTask") ConsumerFactory<String, byte[]> kafkaConsumerFactory,
            Fetcher<String, byte[], KafkaEventMessage> kafkaFetcher,
            @Qualifier("eventSerializer")
            Serializer serializer,
            KafkaMessageConverter<String, byte[]> messageConverter,
            MeterRegistry meterRegistry) {
        return StreamableKafkaMessageSource
                .<String, byte[]>builder()
                .topics(Lists.newArrayList(extendedProperties.getTopicTasks()))
                .consumerFactory(new MetricsBindingConsumerFactory(meterRegistry, kafkaConsumerFactory))
                .consumerFactory(kafkaConsumerFactory)
                .serializer(serializer)
                .fetcher(kafkaFetcher)
                .messageConverter(messageConverter)
                .bufferFactory(() -> new SortedKafkaMessageBuffer(kafkaProperties.getFetcher().getBufferSize()))
                .build();
    }

}
