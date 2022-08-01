package io.muenchendigital.digiwf.connector.api.output;

import org.springframework.messaging.support.MessageBuilder;

import java.util.Map;

/**
 * Service that is used to emit events to specific topics.
 */
public interface OutputService {

    /**
     * Emit a message to a specific topic
     *
     * @param messageName name of the message to respond to
     * @param destination name of the topic to write the message to
     * @param type        type header of the message
     * @param instanceId  id of the process instance
     * @param data        payload data
     */
    void emitEvent(
            String messageName,
            String destination,
            String type,
            String instanceId,
            Map<String, Object> data);

    /**
     * Emit a message to a specific topic
     *
     * @param destination name of the topic to write the message to
     * @param type        type header of the message
     * @param instanceId  id of the process instance
     * @param data        payload data
     */
    void emitEvent(
            String destination,
            String type,
            String instanceId,
            Map<String, Object> data);


    /**
     * Create a message that can be emitted.
     *
     * @param destination name of the topic to write the message to
     * @param type        type header of the message
     * @param instanceId  id of the process instance
     * @param data        payload data
     * @return Message Builder
     */
    MessageBuilder<Map<String, Object>> createMessage(
            final String destination,
            final String type,
            final String instanceId,
            final Map<String, Object> data);
}
