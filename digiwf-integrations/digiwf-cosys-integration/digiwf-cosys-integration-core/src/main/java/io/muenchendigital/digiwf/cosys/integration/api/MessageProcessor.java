package io.muenchendigital.digiwf.cosys.integration.api;

import io.muenchendigital.digiwf.cosys.integration.domain.model.GenerateDocument;
import io.muenchendigital.digiwf.cosys.integration.domain.service.CosysService;
import io.muenchendigital.digiwf.message.process.api.ProcessApi;
import io.muenchendigital.digiwf.message.process.api.ErrorApi;
import io.muenchendigital.digiwf.message.process.api.error.BpmnError;
import io.muenchendigital.digiwf.message.process.api.error.IncidentError;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.function.Consumer;

import static io.muenchendigital.digiwf.message.common.MessageConstants.DIGIWF_PROCESS_INSTANCE_ID;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessageProcessor {

    private final CosysService cosysService;

    private final ProcessApi processApi;

    private final ErrorApi errorApi;

    /**
     * All messages from the route "generateDocument" go here.
     *
     * @return the consumer
     */
    @Bean
    public Consumer<Message<GenerateDocument>> createCosysDocument() {
        return message -> {
            try {
            log.info("Processing generate document request from eventbus");
            final GenerateDocument document = message.getPayload();
            log.debug("Generate document request: {}", document);
                this.cosysService.createDocument(document);
                this.correlateMessage((message.getHeaders().get(DIGIWF_PROCESS_INSTANCE_ID).toString()), "documentCreated", Map.of("status", true));
            } catch (final BpmnError bpmnError) {
                this.errorApi.handleBpmnError(message.getHeaders(), bpmnError);
            } catch (final IncidentError incidentError) {
                this.errorApi.handleIncident(message.getHeaders(), incidentError);
            }
        };
    }

    public void correlateMessage(final String processInstanceId, final String messageName, final Map<String, Object> message) {
        this.processApi.correlateMessage(processInstanceId, messageName, message);
    }

}
