package de.muenchen.oss.digiwf.cosys.integration.adapter.in;


import de.muenchen.oss.digiwf.cosys.integration.application.port.in.CreateDocumentInPort;
import de.muenchen.oss.digiwf.cosys.integration.model.GenerateDocument;
import de.muenchen.oss.digiwf.message.process.api.ErrorApi;
import de.muenchen.oss.digiwf.message.process.api.error.BpmnError;
import de.muenchen.oss.digiwf.message.process.api.error.IncidentError;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;

import java.util.function.Consumer;

import static de.muenchen.oss.digiwf.message.common.MessageConstants.*;

@Slf4j
@RequiredArgsConstructor
public class MessageProcessor {

    private final CreateDocumentInPort createDocumentInPort;

    private final ErrorApi errorApi;

    public Consumer<Message<GenerateDocument>> createCosysDocument() {
        return message -> {
            try {
                log.info("Processing generate document request from eventbus");
                final GenerateDocument document = message.getPayload();
                log.debug("Generate document request: {}", document);
                this.createDocumentInPort.createDocument(
                        message.getHeaders().get(DIGIWF_PROCESS_INSTANCE_ID, String.class),
                        message.getHeaders().get(TYPE, String.class),
                        message.getHeaders().get(DIGIWF_INTEGRATION_NAME, String.class),
                        document);
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
