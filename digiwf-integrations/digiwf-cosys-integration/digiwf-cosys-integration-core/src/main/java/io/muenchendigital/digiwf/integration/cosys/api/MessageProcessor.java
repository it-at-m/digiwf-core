package io.muenchendigital.digiwf.integration.cosys.api;

import io.muenchendigital.digiwf.integration.cosys.domain.model.GenerateDocument;
import io.muenchendigital.digiwf.integration.cosys.domain.service.CosysService;
import io.muenchendigital.digiwf.spring.cloudstream.utils.api.streaming.service.CorrelateMessageService;
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
                this.emitResponse(message.getHeaders(), true);
            } catch (final Exception err) {
                log.error("Document could not be created: {}", err.getMessage());
                this.emitResponse(message.getHeaders(), false);
            }
        };
    }

    /**
     * Function to emit a reponse using the correlateMessageService of digiwf-spring-cloudstream-utils
     *
     * @param messageHeaders The MessageHeaders of the incoming message you want to correlate your answer to
     * @param status         true when the e-mail has been sent, false when an error occured
     */
    public void emitResponse(final MessageHeaders messageHeaders, final boolean status) {
        final Map<String, Object> correlatePayload = new HashMap<>();
        correlatePayload.put("status", status);
        this.correlateMessageService.sendCorrelateMessage(messageHeaders, correlatePayload);
    }
}
