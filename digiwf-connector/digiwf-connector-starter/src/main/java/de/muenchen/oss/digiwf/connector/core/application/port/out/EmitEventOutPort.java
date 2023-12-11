package de.muenchen.oss.digiwf.connector.core.application.port.out;

import java.util.Map;

/**
 * Service that is used to emit events to specific topics.
 */
public interface EmitEventOutPort {

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

}
