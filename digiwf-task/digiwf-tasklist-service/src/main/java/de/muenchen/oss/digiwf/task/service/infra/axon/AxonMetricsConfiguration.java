package de.muenchen.oss.digiwf.task.service.infra.axon;

import de.muenchen.oss.digiwf.task.service.infra.metrics.ApplicationNameCompositeMessageMonitorWrapper;
import de.muenchen.oss.digiwf.task.service.infra.metrics.EventMessageCountingMonitor;
import io.micrometer.core.instrument.MeterRegistry;
import org.axonframework.config.ConfigurerModule;
import org.axonframework.config.MessageMonitorFactory;
import org.axonframework.eventhandling.TrackingEventProcessor;
import org.axonframework.messaging.Message;
import org.axonframework.monitoring.MessageMonitor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AxonMetricsConfiguration {

    /**
     * Configure message monitor.
     * @param meterRegistry meter registry.
     * @return module with monitoring.
     */
    @Bean
    public ConfigurerModule metricsConfigurer(MeterRegistry meterRegistry) {
        return configurer -> {
            var messageMonitorFactory = new MessageMonitorFactory() {
                @Override
                public MessageMonitor<Message<?>> create(org.axonframework.config.Configuration configuration,
                                                         Class<?> componentType,
                                                         String componentName
                ) {
                    return new ApplicationNameCompositeMessageMonitorWrapper<>(
                        (applicationName) -> new EventMessageCountingMonitor(
                            meterRegistry, "polyflow_axon_kafka_events_received"
                        )
                    );
                }
            };
            configurer.configureMessageMonitor(TrackingEventProcessor.class, messageMonitorFactory);
        };
    }

}
