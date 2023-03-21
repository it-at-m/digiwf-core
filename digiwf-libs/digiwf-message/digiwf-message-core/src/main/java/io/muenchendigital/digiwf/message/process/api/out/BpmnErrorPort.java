package io.muenchendigital.digiwf.message.process.api.out;

import io.muenchendigital.digiwf.message.process.impl.dto.BpmnErrorDto;
import io.muenchendigital.digiwf.message.process.impl.model.Message;

/**
 * BpmnErrorPort interface.
 */
public interface BpmnErrorPort {

    /**
     * Sends a message to the bpmn error message destination.
     *
     * @param message    the message to send
     * @param destination the destination to send the message to
     * @return true if the message was sent successfully
     */
    boolean sendBpmnError(Message<BpmnErrorDto> message, String destination);

}
