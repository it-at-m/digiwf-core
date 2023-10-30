package de.muenchen.oss.digiwf.dms.integration.adapter.in;

import de.muenchen.oss.digiwf.dms.integration.application.port.in.*;
import de.muenchen.oss.digiwf.dms.integration.domain.DocumentType;
import de.muenchen.oss.digiwf.dms.integration.domain.Procedure;
import de.muenchen.oss.digiwf.message.process.api.ErrorApi;
import de.muenchen.oss.digiwf.message.process.api.ProcessApi;
import de.muenchen.oss.digiwf.message.process.api.error.BpmnError;
import de.muenchen.oss.digiwf.message.process.api.error.IncidentError;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

import javax.validation.ValidationException;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

import static de.muenchen.oss.digiwf.message.common.MessageConstants.DIGIWF_MESSAGE_NAME;
import static de.muenchen.oss.digiwf.message.common.MessageConstants.DIGIWF_PROCESS_INSTANCE_ID;

@Configuration
@RequiredArgsConstructor
public class MessageProcessor {

    private final ProcessApi processApi;
    private final ErrorApi errorApi;
    private final CreateProcedureUseCase createProcedureUseCase;
    private final CreateDocumentUseCase createDocumentUseCase;
    private final UpdateDocumentUseCase updateDocumentUseCase;
    private final DepositObjectUseCase depositObjectUseCase;
    private final CancelObjectUseCase cancelObjectUseCase;
    private final ReadContentUseCase readContentUseCase;

    public Consumer<Message<CreateProcedureDto>> createProcedure() {
        return message -> {
            withErrorHandling(message, () -> {
                final CreateProcedureDto createProcedureDto = message.getPayload();
                final Procedure vorgang = this.createProcedureUseCase.createProcedure(
                        createProcedureDto.getTitle(),
                        createProcedureDto.getFileCOO(),
                        createProcedureDto.getUser()
                );

                this.correlateMessage(Objects.requireNonNull(message.getHeaders().get(DIGIWF_PROCESS_INSTANCE_ID)).toString(),
                        Objects.requireNonNull(message.getHeaders().get(DIGIWF_MESSAGE_NAME)).toString(), Map.of("procedureCOO", vorgang.getCoo()));
            });
        };
    }

    public Consumer<Message<DepositObjectDto>> depositObject() {
        return message -> {
            withErrorHandling(message, () -> {
                final DepositObjectDto depositObjectDto = message.getPayload();
                this.depositObjectUseCase.depositObject(
                        depositObjectDto.getObjectCoo(),
                        depositObjectDto.getUser()
                );

                this.correlateMessage(message.getHeaders().get(DIGIWF_PROCESS_INSTANCE_ID).toString(),
                        message.getHeaders().get(DIGIWF_MESSAGE_NAME).toString(), Map.of());
            });
        };
    }

    public Consumer<Message<CreateDocumentDto>> createDocument() {
        return message -> {
            withErrorHandling(message, () -> {
                final CreateDocumentDto createDocumentDto = message.getPayload();
                final String document = this.createDocumentUseCase.createDocument(
                        createDocumentDto.getProcedureCoo(),
                        createDocumentDto.getTitle(),
                        createDocumentDto.getUser(),
                        DocumentType.valueOf(createDocumentDto.getType()),
                        createDocumentDto.getFilepathsAsList(),
                        createDocumentDto.getFileContext()
                );

                this.correlateMessage(message.getHeaders().get(DIGIWF_PROCESS_INSTANCE_ID).toString(),
                        message.getHeaders().get(DIGIWF_MESSAGE_NAME).toString(), Map.of("documentCoo", document));
            });
        };
    }

    public Consumer<Message<UpdateDocumentDto>> updateDocument() {
        return message -> {
            withErrorHandling(message, () -> {
                final UpdateDocumentDto updateDocumentDto = message.getPayload();
                this.updateDocumentUseCase.updateDocument(
                        updateDocumentDto.getDocumentCoo(),
                        updateDocumentDto.getUser(),
                        DocumentType.valueOf(updateDocumentDto.getType()),
                        updateDocumentDto.getFilepathsAsList(),
                        updateDocumentDto.getFileContext()
                );

                this.correlateMessage(message.getHeaders().get(DIGIWF_PROCESS_INSTANCE_ID).toString(),
                        message.getHeaders().get(DIGIWF_MESSAGE_NAME).toString(), Map.of());
            });
        };
    }

    public Consumer<Message<CancelObjectDto>> cancelObject() {
        return message -> {
            withErrorHandling(message, () -> {
                final CancelObjectDto cancelObjectDto = message.getPayload();
                this.cancelObjectUseCase.cancelObject(
                        cancelObjectDto.getObjectCoo(),
                        cancelObjectDto.getUser()
                );

                this.correlateMessage(message.getHeaders().get(DIGIWF_PROCESS_INSTANCE_ID).toString(),
                        message.getHeaders().get(DIGIWF_MESSAGE_NAME).toString(), Map.of());
            });
        };
    }

    public Consumer<Message<ReadContentDto>> readContent() {
        return message -> {
            withErrorHandling(message, () -> {
                final ReadContentDto readContentDto = message.getPayload();
                this.readContentUseCase.readContent(
                        readContentDto.getContentCoos(),
                        readContentDto.getUser(),
                        readContentDto.getFilePath(),
                        readContentDto.getFileContext()
                );
                this.correlateMessage(message.getHeaders().get(DIGIWF_PROCESS_INSTANCE_ID).toString(),
                        message.getHeaders().get(DIGIWF_MESSAGE_NAME).toString(), Map.of());
            });
        };
    }

    private void withErrorHandling(final Message<?> message, final Runnable runnable) {
        try {
            runnable.run();
        } catch (final BpmnError bpmnError) {
            this.errorApi.handleBpmnError(message.getHeaders(), bpmnError);
        } catch (final IncidentError incidentError) {
            this.errorApi.handleIncident(message.getHeaders(), incidentError);
        } catch (final ValidationException validationException) {
            this.errorApi.handleIncident(message.getHeaders(), new IncidentError(validationException.getMessage()));
        }
    }

    public void correlateMessage(final String processInstanceId, final String messageName, final Map<String, Object> message) {
        this.processApi.correlateMessage(processInstanceId, messageName, message);
    }
}
