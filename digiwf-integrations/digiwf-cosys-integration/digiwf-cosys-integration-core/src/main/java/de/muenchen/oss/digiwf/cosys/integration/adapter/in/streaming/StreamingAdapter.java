package de.muenchen.oss.digiwf.cosys.integration.adapter.in.streaming;


import de.muenchen.oss.digiwf.cosys.integration.application.port.in.CreateDocumentInPort;
import de.muenchen.oss.digiwf.message.process.api.ErrorApi;
import de.muenchen.oss.digiwf.message.process.api.ProcessApi;
import de.muenchen.oss.digiwf.message.process.api.error.BpmnError;
import de.muenchen.oss.digiwf.message.process.api.error.IncidentError;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;

import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

import static de.muenchen.oss.digiwf.message.common.MessageConstants.*;

@Slf4j
@RequiredArgsConstructor
public class StreamingAdapter {

    private final CreateDocumentInPort createDocumentInPort;

    private final ProcessApi processApi;
    private final ErrorApi errorApi;

    @Deprecated
    public Consumer<Message<GenerateDocumentPresignedUrlsDTO>> createCosysDocument() {
        return message -> withErrorHandling(message, () -> {
            log.info("Processing generate document request from eventbus");
            final GenerateDocumentPresignedUrlsDTO document = message.getPayload();
            log.debug("Generate document request: {}", document);
            this.createDocumentInPort.createDocument(document, document.getDocumentStorageUrls());
            this.correlateMessage(
                    message.getHeaders(),
                    Map.of("status", true)
            );
        });
    }

    public Consumer<Message<GenerateDocumentDTO>> createCosysDocumentV2() {
        return message -> withErrorHandling(message, () -> {
            log.info("Processing generate document request from eventbus");
            final GenerateDocumentDTO document = message.getPayload();
            log.debug("Generate document request: {}", document);
            this.createDocumentInPort.createDocument(document, document.getFileContext(), document.getFilePath());
            this.correlateMessage(
                    message.getHeaders(),
                    Map.of("status", true)
            );
        });
    }

    private void withErrorHandling(final Message<?> message, final Runnable runnable) {
        try {
            runnable.run();
        } catch (final BpmnError bpmnError) {
            this.errorApi.handleBpmnError(message.getHeaders(), bpmnError);
        } catch (final ValidationException validationException) {
            this.errorApi.handleBpmnError(message.getHeaders(), new BpmnError("VALIDATION_ERROR", validationException.getMessage()));
        } catch (final IncidentError incidentError) {
            this.errorApi.handleIncident(message.getHeaders(), incidentError);
        }
    }

    private void correlateMessage(final Map<String, Object> originMessageHeaders, final Map<String, Object> message) {
        String processInstanceId = Objects.requireNonNull(originMessageHeaders.get(DIGIWF_PROCESS_INSTANCE_ID).toString());
        String type = Objects.requireNonNull(originMessageHeaders.get(TYPE).toString());
        String integrationName = Objects.requireNonNull(originMessageHeaders.get(DIGIWF_INTEGRATION_NAME).toString());
        log.info("sending response message for process {}: {}", processInstanceId, message);
        this.processApi.correlateMessage(processInstanceId, type, integrationName, message);
    }
}
