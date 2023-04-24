package io.muenchendigital.digiwf.task.polyflow.axon;

import io.holunda.polyflow.taskpool.collector.CamundaTaskpoolCollectorProperties;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import io.muenchendigital.digiwf.task.metrics.MetricsHelper;
import org.axonframework.config.Configurer;
import org.axonframework.config.ConfigurerModule;
import org.axonframework.config.MessageMonitorFactory;
import org.axonframework.extensions.kafka.eventhandling.producer.KafkaPublisher;
import org.axonframework.micrometer.MessageTimerMonitor;
import org.axonframework.monitoring.MultiMessageMonitor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import static io.muenchendigital.digiwf.task.envprocessor.AxonKafkaPropertiesEnvironmentPostProcessor.PROFILE_DISABLED;
import static io.muenchendigital.digiwf.task.metrics.MetricsHelper.POLYFLOW_AXON_KAFKA_EVENTS_SENT;

@Profile("!" + PROFILE_DISABLED)
@Configuration
public class PolyflowAxonMetricsConfiguration {

    @Bean
    public ConfigurerModule kafkaEventbusMetricsConfigurer(MeterRegistry meterRegistry, CamundaTaskpoolCollectorProperties collectorProperties) {
        return configurer -> instrumentKafkaPublisher(meterRegistry, configurer, collectorProperties.getApplicationName());
    }

    private void instrumentKafkaPublisher(
            MeterRegistry meterRegistry, Configurer configurer, String applicationName) {
        MessageMonitorFactory messageMonitorFactory = (configuration, componentType, componentName) -> {
            // We want to count the messages sent via Kafka
            var messageCounter = TagSupportingMessageCountingMonitor.buildMonitor(POLYFLOW_AXON_KAFKA_EVENTS_SENT,
                    Tags.of(MetricsHelper.TagNames.PROCESS_ENGINE, applicationName), meterRegistry);
            // And we also want to set a message timer
            var messageTimer = MessageTimerMonitor.builder().meterNamePrefix(POLYFLOW_AXON_KAFKA_EVENTS_SENT).meterRegistry(meterRegistry).build();
            // Which we group in a MultiMessageMonitor
            return new MultiMessageMonitor<>(messageCounter, messageTimer);
        };
        configurer.configureMessageMonitor(KafkaPublisher.class, messageMonitorFactory);
    }

}
