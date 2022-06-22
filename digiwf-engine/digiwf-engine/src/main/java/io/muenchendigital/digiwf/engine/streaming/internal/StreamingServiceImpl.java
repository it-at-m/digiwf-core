package io.muenchendigital.digiwf.engine.streaming.internal;

import io.muenchendigital.digiwf.engine.data.EngineDataSerializer;
import io.muenchendigital.digiwf.engine.streaming.api.StreamingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Sinks;

import java.util.Map;

import static io.muenchendigital.digiwf.engine.streaming.api.StreamingHeaders.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class StreamingServiceImpl implements StreamingService {

    private final EngineDataSerializer engineDataSerializer;
    private final Sinks.Many<Message<Map<String, Object>>> dynamicSink;

    @Override
    public void emitMessage(
            final String destination,
            final String type,
            final String instanceId,
            final Map<String, Object> data) {
        final Message<Map<String, Object>> message = this.createMessage(destination, type, instanceId, data).build();
        log.debug("Emit message {}", message);
        this.dynamicSink.tryEmitNext(message).orThrow();
    }

    @Override
    public void emitMessage(
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
                .withPayload(this.engineDataSerializer.fromEngineData(data))
                .setHeader(STREAM_SEND_TO_DESTINATION, destination)
                .setHeader(TYPE, type)
                .setHeader(DIGIWF_PROCESS_INSTANCE_ID, instanceId);
    }

}
