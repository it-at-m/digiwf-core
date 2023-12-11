package de.muenchen.oss.digiwf.connector.core.adapter.out;

import de.muenchen.oss.digiwf.connector.core.adapter.in.streaming.StreamingHeaders;
import de.muenchen.oss.digiwf.connector.core.application.port.out.EmitEventOutPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Sinks;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventEmitterAdapter implements EmitEventOutPort {

    private final Sinks.Many<Message<Map<String, Object>>> dynamicSink;

    @Override
    public void emitEvent(
            final String destination,
            final String type,
            final String instanceId,
            final Map<String, Object> data) {
        final Message<Map<String, Object>> message = this.createMessage(destination, type, instanceId, data).build();
        log.debug("Emit message {}", message);
        this.dynamicSink.tryEmitNext(message).orThrow();
    }

    @Override
    public void emitEvent(
            final String messageName,
            final String destination,
            final String type,
            final String instanceId,
            final Map<String, Object> data) {
        final Message<Map<String, Object>> message = this.createMessage(destination, type, instanceId, data)
                .setHeader(StreamingHeaders.DIGIWF_MESSAGE_NAME, messageName)
                .build();
        log.debug("Emit message {}", message);
        this.dynamicSink.tryEmitNext(message).orThrow();
    }

    private MessageBuilder<Map<String, Object>> createMessage(
            final String destination,
            final String type,
            final String instanceId,
            final Map<String, Object> data) {

        return MessageBuilder
                .withPayload(data)
                .setHeader(StreamingHeaders.STREAM_SEND_TO_DESTINATION, destination)
                .setHeader(StreamingHeaders.TYPE, type)
                .setHeader(StreamingHeaders.DIGIWF_PROCESS_INSTANCE_ID, instanceId);
    }

}
