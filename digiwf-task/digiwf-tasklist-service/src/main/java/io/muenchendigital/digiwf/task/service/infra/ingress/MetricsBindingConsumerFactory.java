package io.muenchendigital.digiwf.task.service.infra.ingress;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.kafka.KafkaClientMetrics;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.axonframework.extensions.kafka.eventhandling.consumer.ConsumerFactory;

import java.util.UUID;

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
        return new MetricsAwareConsumer<K, V>(consumer, metrics);
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
