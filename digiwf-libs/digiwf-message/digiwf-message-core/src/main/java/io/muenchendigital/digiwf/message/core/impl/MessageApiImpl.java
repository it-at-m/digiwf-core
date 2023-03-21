package io.muenchendigital.digiwf.message.core.impl;

import io.muenchendigital.digiwf.message.core.api.MessageApi;
import io.muenchendigital.digiwf.message.core.api.out.SendMessagePort;
import io.muenchendigital.digiwf.message.core.impl.model.Message;
import lombok.RequiredArgsConstructor;

import java.util.Map;

import static io.muenchendigital.digiwf.message.common.MessageConstants.TYPE;

/**
 * Implementation of {@link io.muenchendigital.digiwf.message.core.api.MessageApi}
 */
@RequiredArgsConstructor
public class MessageApiImpl implements MessageApi {

    private final SendMessagePort sendMessagePort;

    /**
     * Sends a message to the specified destination with the given payload.
     * @param payload The message payload.
     * @param destination The destination to send the message to.
     * @return true if the message was successfully sent, false otherwise.
     */
    @Override
    public boolean sendMessage(final Object payload, final String destination) {
        return this.sendMessage(payload, Map.of(), destination);
    }

    /**
     * Sends a message to the specified destination with the given payload and headers.
     * @param payload The message payload.
     * @param headers A map of headers to include with the message.
     * @param destination The destination to send the message to.
     * @return true if the message was successfully sent, false otherwise.
     */
    @Override
    public boolean sendMessage(final Object payload, final Map<String, Object> headers, final String destination) {
        if (destination == null || destination.isEmpty()) {
            return false;
        }
        if (payload == null) {
            return false;
        }

        final Message message = new Message();
        message.addHeader(TYPE, destination);
        for (final Map.Entry<String, Object> entry : headers.entrySet()) {
            message.addHeader(entry.getKey(), entry.getValue().toString());
        }
        message.addPayload(payload);
        return this.sendMessagePort.sendMessage(message, destination);
    }
}
