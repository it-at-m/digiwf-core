package io.muenchendigital.digiwf.message.core.api;

import java.util.Map;

/**
 * Interface representing a Message API that sends messages to a destination with an optional set of headers.
 */
public interface MessageApi {

    /**
     * Sends a message to the specified destination with the given payload.
     * @param payload The message payload.
     * @param destination The destination to send the message to.
     * @return true if the message was successfully sent, false otherwise.
     */
    boolean sendMessage(Object payload, String destination);

    /**
     * Sends a message to the specified destination with the given payload and headers.
     * @param payload The message payload.
     * @param headers A map of headers to include with the message.
     * @param destination The destination to send the message to.
     * @return true if the message was successfully sent, false otherwise.
     */
    boolean sendMessage(Object payload, Map<String, Object> headers, String destination);

}
