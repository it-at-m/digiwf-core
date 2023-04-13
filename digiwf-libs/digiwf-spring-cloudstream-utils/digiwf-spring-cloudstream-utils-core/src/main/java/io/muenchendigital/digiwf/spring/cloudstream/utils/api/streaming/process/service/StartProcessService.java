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

/**
 * Service that can be used to start processes in the digiwf platform.
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Deprecated
public class StartProcessService {

    private static final String STARTPROCESS_V01 = "startProcessV01";

    private final Sinks.Many<Message<StartProcessEvent>> startProcessSink;

    /**
     * Starts a process with the given payload.
     *
     * @param processKey key of the process
     * @param payload    Data to start the process
     *
     * This function is deprecated. Use digiwf-message instead {@link io.muenchendigital.digiwf:digiwf-message:0.18.0}.
     * @deprecated This function is no longer supported and may be removed in a future release
     *
     * @return the emit result
     */
    @Deprecated
    public boolean startProcess(final String processKey, final Map<String, Object> payload) {
        return this.startProcess(processKey, null, payload);
    }

    /**
     * Starts a process with the given payload and file context.
     *
     * @param processKey key of the process
     * @param fileContext file context for document storage
     * @param payload    Data to start the process
     * @return the emit result
     */
    @Deprecated
    public boolean startProcess(final String processKey, final String fileContext, final Map<String, Object> payload) {
        final StartProcessEvent startProcessEvent = StartProcessEvent.builder()
                .key(processKey)
                .fileContext(fileContext)
                .data(payload)
                .build();

        final Message<StartProcessEvent> message = MessageBuilder
                .withPayload(startProcessEvent)
                .setHeader(StreamingHeaders.TYPE, STARTPROCESS_V01)
                .build();

//        // TODO: If spring.cloud.function.definition=sendCorrelateMessage is not set, messaging doesnt work, but no error is thrown.
//        // Same for spring.cloud.stream.bindings.sendCorrelateMessage-out-0.destination=
        final Sinks.EmitResult emitResult = this.startProcessSink.tryEmitNext(message);
//
        if (emitResult.isSuccess()) {
            log.debug("The process start {} was successfully delivered to the eventbus.", message.getHeaders().get(MessageHeaders.ID));
        } else {
            log.error("The process start {} couldn't be delivered to the eventbus.", message.getHeaders().get(MessageHeaders.ID));
        }

        log.debug("Message: {}", message);
        return emitResult.isSuccess();
    }

}
