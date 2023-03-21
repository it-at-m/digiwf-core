package io.muenchendigital.digiwf.message.process.impl;

import io.muenchendigital.digiwf.message.core.api.MessageApi;
import io.muenchendigital.digiwf.message.process.api.out.BpmnErrorPort;
import io.muenchendigital.digiwf.message.process.api.out.CorrelateMessagePort;
import io.muenchendigital.digiwf.message.process.api.out.IncidentPort;
import io.muenchendigital.digiwf.message.process.api.out.StartProcessPort;
import io.muenchendigital.digiwf.message.process.impl.dto.BpmnErrorDto;
import io.muenchendigital.digiwf.message.process.impl.dto.CorrelateMessageDto;
import io.muenchendigital.digiwf.message.process.impl.dto.StartProcessDto;
import io.muenchendigital.digiwf.message.process.impl.model.Message;
import lombok.RequiredArgsConstructor;

/**
 * Default implementation of {@link StartProcessPort}, {@link CorrelateMessagePort}, {@link IncidentPort} and {@link BpmnErrorPort}.
 */
@RequiredArgsConstructor
public class ProcessPortImpl implements StartProcessPort, CorrelateMessagePort, IncidentPort, BpmnErrorPort {

    private final MessageApi messageApi;

    /**
     * Sends a start process message to the start process destination using the {@link MessageApi}.
     * @param message The message to be sent.
     * @param destination The destination to send the message to.
     * @return true if the message was sent successfully, false otherwise.
     */
    @Override
    public boolean startProcess(final Message<StartProcessDto> message, final String destination) {
        return this.messageApi.sendMessage(message.getPayload(), message.getHeaders(), destination);
    }

    /**
     * Sends a correlate message to the correlate message destination using the {@link MessageApi}.
     * @param message The message to be sent.
     * @param destination The destination to send the message to.
     * @return true if the message was sent successfully, false otherwise.
     */
    @Override
    public boolean sendCorrelateMessage(final Message<CorrelateMessageDto> message, final String destination) {
        return this.messageApi.sendMessage(message.getPayload(), message.getHeaders(), destination);
    }

    /**
     * Sends an incident message to the incident destination using the {@link MessageApi}.
     * @param message The message to be sent.
     * @param destination The destination to send the message to.
     * @return true if the message was sent successfully, false otherwise.
     */
    @Override
    public boolean sendIncidentMessage(final Message<Object> message, final String destination) {
        return this.messageApi.sendMessage(message.getPayload(), message.getHeaders(), destination);
    }

    /**
     * Sends a technical error message to the technical error destination using the {@link MessageApi}.
     * @param message The message to be sent.
     * @param destination The destination to send the message to.
     * @return true if the message was sent successfully, false otherwise.
     */
    @Override
    public boolean sendBpmnError(final Message<BpmnErrorDto> message, final String destination) {
        return this.messageApi.sendMessage(message.getPayload(), message.getHeaders(), destination);
    }
}
