package io.muenchendigital.digiwf.message.core.impl;

import io.muenchendigital.digiwf.message.core.api.MessageApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import reactor.core.publisher.Sinks;

import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of {@link io.muenchendigital.digiwf.message.core.api.MessageApi}
 */
@RequiredArgsConstructor
@Slf4j
public class MessageApiImpl implements MessageApi {

    private final Sinks.Many<org.springframework.messaging.Message<Object>> messageSink;

    public static final String SPRING_CLOUD_STREAM_SENDTO_DESTINATION = "spring.cloud.stream.sendto.destination";

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

        final Map<String, Object> hdrs = new HashMap<>(headers);

        // send the message to the given destination by setting the sendto.destination header
        hdrs.put(SPRING_CLOUD_STREAM_SENDTO_DESTINATION, destination);

        final Message<Object> msg = MessageBuilder.createMessage(payload, new MessageHeaders(hdrs));
        final Sinks.EmitResult emitResult = this.messageSink.tryEmitNext(msg);
        log.debug("Message: {}", payload);
        return emitResult.isSuccess();
    }
}
