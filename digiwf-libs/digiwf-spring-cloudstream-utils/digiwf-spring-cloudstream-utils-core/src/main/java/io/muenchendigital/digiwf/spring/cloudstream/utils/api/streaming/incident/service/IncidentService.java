package io.muenchendigital.digiwf.spring.cloudstream.utils.api.streaming.incident.service;

import io.muenchendigital.digiwf.spring.cloudstream.utils.api.streaming.infrastructure.StreamingHeaders;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Sinks;

import static io.muenchendigital.digiwf.spring.cloudstream.utils.api.streaming.infrastructure.StreamingHeaders.DIGIWF_MESSAGE_NAME;
import static io.muenchendigital.digiwf.spring.cloudstream.utils.api.streaming.infrastructure.StreamingHeaders.DIGIWF_PROCESS_INSTANCE_ID;

@Slf4j
@Service
@RequiredArgsConstructor
@Deprecated
public class IncidentService {

    private static final String MESSAGE_TYPE = "createIncident";

    private final Sinks.Many<Message<String>> incidentSink;

    /**
     * Sends an incident to the bus that will be forwarded to the process instance.
     *
     * @param messageHeaders incoming message headers
     * @param errorMessage   the specific error message
     *
     * This function is deprecated. Use digiwf-message instead {@link io.muenchendigital.digiwf:digiwf-message:0.18.0}.
     * @deprecated This function is no longer supported and may be removed in a future release
     *
     * @return success flag
     */
    @Deprecated
    public boolean sendIncident(final MessageHeaders messageHeaders, final String errorMessage) {
        final Message<String> message = MessageBuilder
                .withPayload(errorMessage)
                .setHeader(StreamingHeaders.TYPE, MESSAGE_TYPE)
                .setHeader(DIGIWF_PROCESS_INSTANCE_ID, messageHeaders.get(DIGIWF_PROCESS_INSTANCE_ID))
                // bpmn token is waiting on that message receive event. Thats where we want our incident to be placed:
                .setHeader(DIGIWF_MESSAGE_NAME, messageHeaders.get(DIGIWF_MESSAGE_NAME))
                .build();

        final Sinks.EmitResult emitResult = this.incidentSink.tryEmitNext(message);

        if (emitResult.isSuccess()) {
            log.info("The incident {} was successfully delivered to the eventbus.", message.getHeaders().get(MessageHeaders.ID));
        } else {
            log.error("The incident {} couldn't be delivered to the eventbus.", message.getHeaders().get(MessageHeaders.ID));
        }
        log.debug("Message: {}", message);
        return emitResult.isSuccess();
    }

}
