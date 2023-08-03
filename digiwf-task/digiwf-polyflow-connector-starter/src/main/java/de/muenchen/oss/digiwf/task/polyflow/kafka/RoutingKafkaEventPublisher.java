package de.muenchen.oss.digiwf.task.polyflow.kafka;

import org.axonframework.eventhandling.EventMessage;
import org.axonframework.extensions.kafka.eventhandling.producer.KafkaEventPublisher;
import org.axonframework.extensions.kafka.eventhandling.producer.KafkaPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.axonframework.common.BuilderUtils.assertNonNull;

/**
 * A {@link KafkaEventPublisher} that routes incoming events based on payload type.
 *
 * @param <K> key type.
 * @param <V> value type.
 */
public class RoutingKafkaEventPublisher<K, V> extends KafkaEventPublisher<K, V> {

    private static final Logger logger = LoggerFactory.getLogger(RoutingKafkaEventPublisher.class);

    private final KafkaTopicRouter kafkaTopicRouter;

    protected RoutingKafkaEventPublisher(Builder<K, V> builder) {
        super(builder);
        this.kafkaTopicRouter = builder.kafkaTopicRouter;
    }

    @Override
    public Object handle(EventMessage<?> event) {
        if (kafkaTopicRouter.topicForPayloadType(event.getPayloadType()) != null) {
            super.handle(event);
        } else if (logger.isTraceEnabled()) {
            logger.trace("Message will not be published to Kafka because its type is not configured to go to any topic: {}", event.getPayload());
        }
        return null;
    }

    /**
     * Instantiate a Builder to be able to create a {@link RoutingKafkaEventPublisher}.
     * <p>
     * The {@link KafkaPublisher} and {@link KafkaTopicRouter} are <b>hard requirements</b> and as such should be provided.
     *
     * @param <K> a generic type for the key of the {@link KafkaPublisher}
     * @param <V> a generic type for the value of the {@link KafkaPublisher}
     * @return a Builder to be able to create a {@link RoutingKafkaEventPublisher}
     */
    public static <K, V> Builder<K, V> builder() {
        return new Builder<>();
    }

    public static class Builder<K, V> extends KafkaEventPublisher.Builder<K, V> {
        private KafkaTopicRouter kafkaTopicRouter;

        /**
         * Sets the {@link KafkaTopicRouter} to be used by this {@link KafkaEventPublisher} to determine which events to publish.
         *
         * @param kafkaTopicRouter the {@link KafkaTopicRouter} to be used by this {@link KafkaEventPublisher} to determine which events to publish
         * @return the current Builder instance, for fluent interfacing
         */
        public Builder<K, V> kafkaTopicRouter(KafkaTopicRouter kafkaTopicRouter) {
            this.kafkaTopicRouter = kafkaTopicRouter;
            return this;
        }

        @Override
        public Builder<K, V> kafkaPublisher(KafkaPublisher<K, V> kafkaPublisher) {
            super.kafkaPublisher(kafkaPublisher);
            return this;
        }

        @Override
        public RoutingKafkaEventPublisher<K, V> build() {
            return new RoutingKafkaEventPublisher<>(this);
        }

        @Override
        protected void validate() {
            super.validate();
            assertNonNull(kafkaTopicRouter, "The KafkaTopicRouter is a hard requirement and must be provided");
        }
    }
}
