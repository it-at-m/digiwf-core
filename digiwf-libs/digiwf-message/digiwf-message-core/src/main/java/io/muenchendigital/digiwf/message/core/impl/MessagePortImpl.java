package io.muenchendigital.digiwf.message.core.impl;

import io.muenchendigital.digiwf.message.core.api.out.SendMessagePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import reactor.core.publisher.Sinks;

import java.util.Map;

/**
 * Implementation of a {@link SendMessagePort} for spring cloud stream.
 */
@Slf4j
@RequiredArgsConstructor
public class MessagePortImpl implements SendMessagePort {

    private final Sinks.Many<org.springframework.messaging.Message<Object>> messageSink;

    public static final String SPRING_CLOUD_STREAM_SENDTO_DESTINATION = "spring.cloud.stream.sendto.destination";

    /**
     * Sends a message to a spring cloud stream topic.
     *
     * @param message The message that should be sent to the destination
     * @param destination is the topic the message is sent to
     * @return true if the message was successfully sent, false otherwise
     */
    @Override
    public boolean sendMessage(final io.muenchendigital.digiwf.message.core.impl.model.Message message, final String destination) {
        final Map<String, Object> headers = message.getHeaders();

        // send the message to the given destination by setting the sendto.destination header
        headers.put(SPRING_CLOUD_STREAM_SENDTO_DESTINATION, destination);

        final Message<Object> msg = MessageBuilder.createMessage(message.getPayload(), new MessageHeaders(headers));
        final Sinks.EmitResult emitResult = this.messageSink.tryEmitNext(msg);
        if (emitResult.isSuccess()) {
            log.info("The message {} was successfully delivered to the eventbus (topic {}).", message.getHeaders().get(MessageHeaders.ID), destination);
        } else {
            log.error("The message {} couldn't be delivered to the eventbus (topic {}).", message.getHeaders().get(MessageHeaders.ID), destination);
        }
        log.debug("Message: {}", message);
        return emitResult.isSuccess();
    }

}
