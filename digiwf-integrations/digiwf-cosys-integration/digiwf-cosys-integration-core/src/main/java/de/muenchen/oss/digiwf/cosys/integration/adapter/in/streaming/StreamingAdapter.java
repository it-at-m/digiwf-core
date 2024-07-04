package de.muenchen.oss.digiwf.cosys.integration.adapter.in.streaming;


import de.muenchen.oss.digiwf.cosys.integration.application.port.in.CreateDocumentInPort;
import de.muenchen.oss.digiwf.cosys.integration.domain.model.GenerateDocument;
import de.muenchen.oss.digiwf.message.process.api.ErrorApi;
import de.muenchen.oss.digiwf.message.process.api.ProcessApi;
import de.muenchen.oss.digiwf.message.process.api.error.BpmnError;
import de.muenchen.oss.digiwf.message.process.api.error.IncidentError;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;

import java.util.Map;
import java.util.function.Consumer;

import static de.muenchen.oss.digiwf.message.common.MessageConstants.*;

@Slf4j
@RequiredArgsConstructor
public class StreamingAdapter {

    private final CreateDocumentInPort createDocumentInPort;

    private final ProcessApi processApi;
    private final ErrorApi errorApi;

    public Consumer<Message<GenerateDocument>> createCosysDocument() {
        return message -> {
            try {
                log.info("Processing generate document request from eventbus");
                final GenerateDocument document = message.getPayload();
                log.debug("Generate document request: {}", document);
                this.createDocumentInPort.createDocument(document);
                this.processApi.correlateMessage(
                        message.getHeaders().get(DIGIWF_PROCESS_INSTANCE_ID, String.class),
                        message.getHeaders().get(TYPE, String.class),
                        message.getHeaders().get(DIGIWF_INTEGRATION_NAME, String.class),
                        Map.of("status", true));
            } catch (final BpmnError bpmnError) {
                this.errorApi.handleBpmnError(message.getHeaders(), bpmnError);
            } catch (final ValidationException validationException) {
                this.errorApi.handleBpmnError(message.getHeaders(), new BpmnError("VALIDATION_ERROR", validationException.getMessage()));
            } catch (final IncidentError incidentError) {
                this.errorApi.handleIncident(message.getHeaders(), incidentError);
            }
        };
    }

}
