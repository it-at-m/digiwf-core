package io.muenchendigital.digiwf.message.process.api.out;

import io.muenchendigital.digiwf.message.process.impl.model.Message;

/**
 * IncidentPort interface.
 */
public interface IncidentPort {

    /**
     * Sends a message to the incident message destination.
     *
     * @param message    the message to send
     * @param destination the destination to send the message to
     * @return true if the message was sent successfully
     */
    boolean sendIncidentMessage(Message<Object> message, String destination);

}
