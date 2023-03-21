package io.muenchendigital.digiwf.message.process.api.out;

import io.muenchendigital.digiwf.message.process.impl.dto.TechnicalErrorDto;
import io.muenchendigital.digiwf.message.process.impl.model.Message;

/**
 * TechnicalErrorPort interface.
 */
public interface TechnicalErrorPort {

    /**
     * Sends a message to the technical error message destination.
     *
     * @param message    the message to send
     * @param destination the destination to send the message to
     * @return true if the message was sent successfully
     */
    boolean sendTechnicalErrorMessage(Message<TechnicalErrorDto> message, String destination);

}
