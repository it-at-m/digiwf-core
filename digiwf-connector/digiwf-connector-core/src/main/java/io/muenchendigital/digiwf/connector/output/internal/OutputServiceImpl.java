package io.muenchendigital.digiwf.connector.output.internal;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Sinks;

import java.util.Map;

import static io.muenchendigital.digiwf.connector.api.output.StreamingHeaders.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class OutputServiceImpl implements io.muenchendigital.digiwf.connector.api.output.OutputService {

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
                .setHeader(DIGIWF_MESSAGE_NAME, messageName)
                .build();
        log.debug("Emit message {}", message);
        this.dynamicSink.tryEmitNext(message).orThrow();
    }

    @Override
    public MessageBuilder<Map<String, Object>> createMessage(
            final String destination,
            final String type,
            final String instanceId,
            final Map<String, Object> data) {

        return MessageBuilder
                .withPayload(data)
                .setHeader(STREAM_SEND_TO_DESTINATION, destination)
                .setHeader(TYPE, type)
                .setHeader(DIGIWF_PROCESS_INSTANCE_ID, instanceId);
    }

}
