package io.muenchendigital.digiwf.spring.cloudstream.utils.api.streaming.process.service;

import io.muenchendigital.digiwf.process.api.StartProcessEvent;
import io.muenchendigital.digiwf.spring.cloudstream.utils.api.streaming.infrastructure.StreamingHeaders;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Sinks;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class StartProcessService {

    private static final String CORRELATEMESSAGEV_01 = "startProcessV01";

    private final Sinks.Many<Message<StartProcessEvent>> startProcessSink;

    /**
     * Starts a process with the given payload.
     *
     * @param processKey key of the process
     * @param payload    Data to start the process
     * @return
     */
    public boolean startProcess(final String processKey, final Map<String, Object> payload) {
        final StartProcessEvent startProcessEvent = StartProcessEvent.builder()
                .key(processKey)
                .data(payload)
                .build();

        final Message<StartProcessEvent> message = MessageBuilder
                .withPayload(startProcessEvent)
                .setHeader(StreamingHeaders.TYPE, CORRELATEMESSAGEV_01)
                .build();

//        // TODO: If spring.cloud.function.definition=sendCorrelateMessage is not set, messaging doesnt work, but no error is thrown.
//        // Same for spring.cloud.stream.bindings.sendCorrelateMessage-out-0.destination=
        final Sinks.EmitResult emitResult = this.startProcessSink.tryEmitNext(message);
//
        if (emitResult.isSuccess()) {
            log.debug("The correlating message {} was successfully delivered to the eventbus.", message.getHeaders().get(MessageHeaders.ID));
        } else {
            log.error("The correlating message {} couldn't be delivered to the eventbus.", message.getHeaders().get(MessageHeaders.ID));
        }

        log.debug("Message: {}", message);
        return emitResult.isSuccess();
    }

}