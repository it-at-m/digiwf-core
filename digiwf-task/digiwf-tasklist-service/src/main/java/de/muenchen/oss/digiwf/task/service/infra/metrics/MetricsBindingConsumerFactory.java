package de.muenchen.oss.digiwf.task.service.infra.metrics;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.kafka.KafkaClientMetrics;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.axonframework.extensions.kafka.eventhandling.consumer.ConsumerFactory;

import java.util.UUID;

/**
 * Metrics-aware consumer factory binding Kafka metrics ti meter registry.
 * @param <K> message partition key.
 * @param <V> message encoding.
 */
@RequiredArgsConstructor
@Slf4j
public class MetricsBindingConsumerFactory<K, V> implements ConsumerFactory<K, V> {

    private final MeterRegistry meterRegistry;
    private final ConsumerFactory<K, V> delegateConsumerFactory;

    @Override
    public Consumer<K, V> createConsumer(String groupId) {
        var consumer = delegateConsumerFactory.createConsumer(groupId);
        var metrics = new KafkaClientMetrics(consumer);
        metrics.bindTo(meterRegistry);
        return new MetricsAwareConsumer<>(consumer, metrics);
    }

    @RequiredArgsConstructor
    static class MetricsAwareConsumer<K, V> implements Consumer<K, V> {

        @lombok.experimental.Delegate(excludes = Close.class)
        private final Consumer<K, V> delegate;
        private final KafkaClientMetrics metrics;

        public void close() {
            metrics.close();
            delegate.close();
        }

        /**
         * Type for delegate exclusion.
         */
        private interface Close {
            void close();
        }
    }
}
