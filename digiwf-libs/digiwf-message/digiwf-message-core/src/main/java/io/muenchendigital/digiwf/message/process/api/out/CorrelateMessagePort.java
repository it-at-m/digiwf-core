package io.muenchendigital.digiwf.message.process.api.out;

import io.muenchendigital.digiwf.message.process.impl.dto.CorrelateMessageDto;
import io.muenchendigital.digiwf.message.process.impl.model.Message;

/**
 * CorrelateMessagePort interface.
 */
public interface CorrelateMessagePort {
    /**
     * Sends a message to the correlate message destination.
     *
     * @param message    the message to send
     * @param destination the destination to send the message to
     * @return true if the message was sent successfully
     */
    boolean sendCorrelateMessage(Message<CorrelateMessageDto> message, String destination);
}
