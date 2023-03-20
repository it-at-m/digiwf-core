package io.muenchendigital.digiwf.spring.cloudstream.utils.api.streaming.bpmnerror.service;

import io.muenchendigital.digiwf.connector.api.bpmnerror.BpmnErrorEvent;
import io.muenchendigital.digiwf.spring.cloudstream.utils.api.streaming.infrastructure.StreamingHeaders;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Sinks;

import static io.muenchendigital.digiwf.spring.cloudstream.utils.api.streaming.infrastructure.StreamingHeaders.DIGIWF_PROCESS_INSTANCE_ID;

@Slf4j
@Service
@RequiredArgsConstructor
@Deprecated
public class BpmnErrorService {

    private static final String MESSAGE_TYPE            = "bpmnerror";
    private static final String BPMN_ERROR_MESSAGE_NAME = "bpmnError";

    private final Sinks.Many<Message<BpmnErrorEvent>> bpmnErrorSink;

    /**
     * Sends an error to the bus that can be correlated by process instance ID.
     *
     * @param messageHeaders incoming message headers
     * @param errorCode      the specific error code
     * @param errorMessage   the specific error message
     *
     * This function is deprecated. Use digiwf-message instead {@link io.muenchendigital.digiwf:digiwf-message:0.18.0}.
     * @deprecated This function is no longer supported and may be removed in a future release
     */
    @Deprecated
    public boolean sendBpmnError(final MessageHeaders messageHeaders, final String errorCode, final String errorMessage) {
        final BpmnErrorEvent.BpmnErrorEventBuilder builder = BpmnErrorEvent.builder()
                .processInstanceId((String) messageHeaders.get(DIGIWF_PROCESS_INSTANCE_ID))
                .errorCode(errorCode)
                .errorMessage(errorMessage)
                .messageName(BPMN_ERROR_MESSAGE_NAME);

        final Message<BpmnErrorEvent> message = MessageBuilder
                .withPayload(builder.build())
                .setHeader(StreamingHeaders.TYPE, MESSAGE_TYPE)
                .build();

        final Sinks.EmitResult emitResult = this.bpmnErrorSink.tryEmitNext(message);

        if (emitResult.isSuccess()) {
            log.info("The error {} was successfully delivered to the eventbus.", message.getHeaders().get(MessageHeaders.ID));
        } else {
            log.error("The error {} couldn't be delivered to the eventbus.", message.getHeaders().get(MessageHeaders.ID));
        }
        log.debug("Message: {}", message);
        return emitResult.isSuccess();
    }

}
