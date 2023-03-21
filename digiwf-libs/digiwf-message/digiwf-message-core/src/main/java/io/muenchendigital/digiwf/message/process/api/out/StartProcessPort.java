package io.muenchendigital.digiwf.message.process.api.out;

import io.muenchendigital.digiwf.message.process.impl.dto.StartProcessDto;
import io.muenchendigital.digiwf.message.process.impl.model.Message;

/**
 * StartProcessPort interface.
 */
public interface StartProcessPort {

    /**
     * Sends a message to the start process destination.
     *
     * @param message    the message to send
     * @param destination the destination to send the message to
     * @return true if the message was sent successfully
     */
    boolean startProcess(Message<StartProcessDto> message, String destination);
}
