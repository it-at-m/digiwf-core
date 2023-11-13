package de.muenchen.oss.digiwf.task.polyflow.kafka;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;

/**
 * Router to decide where to publish events to.
 */
@FunctionalInterface
public interface KafkaTopicRouter {
    /**
     * Retrieves the topic name for given payload type.
     *
     * @param payloadType payload type.
     * @return topic or null, if the event should be dropped.
     */
    @Nullable
    String topicForPayloadType(@NotNull Class<?> payloadType);
}
