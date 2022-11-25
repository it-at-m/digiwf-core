package io.muenchendigital.digiwf.cosys.integration.api;

import io.muenchendigital.digiwf.cosys.integration.domain.model.GenerateDocument;
import io.muenchendigital.digiwf.cosys.integration.domain.service.CosysService;
import io.muenchendigital.digiwf.spring.cloudstream.utils.api.streaming.incident.service.IncidentService;
import io.muenchendigital.digiwf.spring.cloudstream.utils.api.streaming.message.service.CorrelateMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessageProcessor {

    private final CosysService cosysService;
    private final CorrelateMessageService correlateMessageService;
    private final IncidentService incidentService;


    /**
     * All messages from the route "generateDocument" go here.
     *
     * @return the consumer
     */
    @Bean
    public Consumer<Message<GenerateDocument>> createCosysDocument() {
        return message -> {
            log.info("Processing generate document request from eventbus");
            final GenerateDocument document = message.getPayload();
            log.debug("Generate document request: {}", document);
            try {
                this.cosysService.createDocument(document);
                this.emitResponse(message.getHeaders());
            } catch (final Exception err) {
                log.error("Document could not be created: {}", err.getMessage());
                if (!emitIncident(message.getHeaders(), "Document could not be created: " + err.getMessage())){
                    log.error("Emitting registration error failed");
                }


            }
        };
    }

    /**
     * Function to emit a response using the correlateMessageService of digiwf-spring-cloudstream-utils
     *
     * @param messageHeaders The MessageHeaders of the incoming message you want to correlate your answer to
     */
    private void emitResponse(final MessageHeaders messageHeaders) {
        final Map<String, Object> correlatePayload = new HashMap<>();
        correlatePayload.put("status", true);
        this.correlateMessageService.sendCorrelateMessage(messageHeaders, correlatePayload);
    }

    private boolean emitIncident(final MessageHeaders messageHeaders, final String errorMessage) {
        return this.incidentService.sendIncident(messageHeaders, errorMessage);
    }
}
