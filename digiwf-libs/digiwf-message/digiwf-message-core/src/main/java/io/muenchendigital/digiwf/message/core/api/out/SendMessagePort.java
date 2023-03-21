package io.muenchendigital.digiwf.message.core.api.out;

import io.muenchendigital.digiwf.message.core.impl.model.Message;

/**
 * Interface representing a port for sending messages to a specified destination.
 */
public interface SendMessagePort {

    /**
     * Sends the given message to the specified destination.
     * @param message The message to be sent.
     * @param destination The destination to send the message to.
     * @return true if the message was successfully sent, false otherwise.
     */
    boolean sendMessage(Message message, String destination);

}
