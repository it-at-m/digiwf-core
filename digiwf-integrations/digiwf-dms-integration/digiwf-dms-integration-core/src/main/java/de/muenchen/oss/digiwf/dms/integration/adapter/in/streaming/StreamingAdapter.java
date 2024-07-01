package de.muenchen.oss.digiwf.dms.integration.adapter.in.streaming;

import de.muenchen.oss.digiwf.dms.integration.application.port.in.*;
import de.muenchen.oss.digiwf.dms.integration.domain.DocumentResponse;
import de.muenchen.oss.digiwf.dms.integration.domain.DocumentType;
import de.muenchen.oss.digiwf.dms.integration.domain.Procedure;
import de.muenchen.oss.digiwf.message.process.api.ErrorApi;
import de.muenchen.oss.digiwf.message.process.api.ProcessApi;
import de.muenchen.oss.digiwf.message.process.api.error.BpmnError;
import de.muenchen.oss.digiwf.message.process.api.error.IncidentError;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

import static de.muenchen.oss.digiwf.message.common.MessageConstants.*;

@Configuration
@RequiredArgsConstructor
public class StreamingAdapter {

    private final ProcessApi processApi;
    private final ErrorApi errorApi;
    private final CreateFileInPort createFileInPort;
    private final CreateProcedureInPort createProcedureInPort;
    private final CreateDocumentInPort createDocumentInPort;
    private final UpdateDocumentInPort updateDocumentInPort;
    private final DepositObjectInPort depositObjectInPort;
    private final CancelObjectInPort cancelObjectInPort;
    private final ReadContentInPort readContentInPort;
    private final SearchFileInPort searchFileInPort;
    private final SearchSubjectAreaInPort searchSubjectAreaInPort;

    public Consumer<Message<CreateFileDto>> createFile() {
        return message -> {
            withErrorHandling(message, () -> {
                final CreateFileDto createFileDto = message.getPayload();
                final String file = this.createFileInPort.createFile(
                        createFileDto.getTitle(),
                        createFileDto.getApentryCOO(),
                        createFileDto.getUser()
                );

                this.correlateMessage(Objects.requireNonNull(message.getHeaders().get(DIGIWF_PROCESS_INSTANCE_ID)).toString(),
                        Objects.requireNonNull(message.getHeaders().get(TYPE)).toString(),
                        Objects.requireNonNull(message.getHeaders().get(DIGIWF_INTEGRATION_NAME)).toString(), Map.of("fileCOO", file));
            });
        };
    }

    public Consumer<Message<CreateProcedureDto>> createProcedure() {
        return message -> {
            withErrorHandling(message, () -> {
                final CreateProcedureDto createProcedureDto = message.getPayload();
                final Procedure vorgang = this.createProcedureInPort.createProcedure(
                        createProcedureDto.getTitle(),
                        createProcedureDto.getFileCOO(),
                        createProcedureDto.getFileSubj(),
                        createProcedureDto.getUser()
                );

                this.correlateMessage(Objects.requireNonNull(message.getHeaders().get(DIGIWF_PROCESS_INSTANCE_ID)).toString(),
                        Objects.requireNonNull(message.getHeaders().get(TYPE)).toString(),
                        Objects.requireNonNull(message.getHeaders().get(DIGIWF_INTEGRATION_NAME)).toString(), Map.of("procedureCOO", vorgang.getCoo()));
            });
        };
    }

    public Consumer<Message<DepositObjectDto>> depositObject() {
        return message -> {
            withErrorHandling(message, () -> {
                final DepositObjectDto depositObjectDto = message.getPayload();
                this.depositObjectInPort.depositObject(
                        depositObjectDto.getObjectCoo(),
                        depositObjectDto.getUser()
                );

                this.correlateMessage(message.getHeaders().get(DIGIWF_PROCESS_INSTANCE_ID).toString(),
                        message.getHeaders().get(TYPE).toString(),
                        message.getHeaders().get(DIGIWF_INTEGRATION_NAME).toString(), Map.of());
            });
        };
    }

    public Consumer<Message<CreateDocumentDto>> createDocument() {
        return message -> {
            withErrorHandling(message, () -> {
                final CreateDocumentDto createDocumentDto = message.getPayload();
                DocumentResponse documentResponse = this.createDocumentInPort.createDocument(
                        createDocumentDto.getProcedureCoo(),
                        createDocumentDto.getTitle(),
                        createDocumentDto.getDate(),
                        createDocumentDto.getUser(),
                        DocumentType.valueOf(createDocumentDto.getType()),
                        createDocumentDto.getFilepathsAsList(),
                        createDocumentDto.getFileContext(),
                        message.getHeaders().get(DIGIWF_PROCESS_DEFINITION).toString()
                );

                this.correlateMessage(message.getHeaders().get(DIGIWF_PROCESS_INSTANCE_ID).toString(),
                        message.getHeaders().get(TYPE).toString(),
                        message.getHeaders().get(DIGIWF_INTEGRATION_NAME).toString(), Map.of(
                                "documentCoo", documentResponse.getDocumentCoo(),
                                "contentCoos", documentResponse.getContentCoos()
                        ));
            });
        };
    }

    public Consumer<Message<UpdateDocumentDto>> updateDocument() {
        return message -> {
            withErrorHandling(message, () -> {
                final UpdateDocumentDto updateDocumentDto = message.getPayload();
                DocumentResponse documentResponse = this.updateDocumentInPort.updateDocument(
                        updateDocumentDto.getDocumentCoo(),
                        updateDocumentDto.getUser(),
                        DocumentType.valueOf(updateDocumentDto.getType()),
                        updateDocumentDto.getFilepathsAsList(),
                        updateDocumentDto.getFileContext(),
                        message.getHeaders().get(DIGIWF_PROCESS_DEFINITION).toString()
                );

                this.correlateMessage(message.getHeaders().get(DIGIWF_PROCESS_INSTANCE_ID).toString(),
                        message.getHeaders().get(TYPE).toString(),
                        message.getHeaders().get(DIGIWF_INTEGRATION_NAME).toString(), Map.of(
                                "contentCoos", documentResponse.getContentCoos()
                        ));
            });
        };
    }

    public Consumer<Message<CancelObjectDto>> cancelObject() {
        return message -> {
            withErrorHandling(message, () -> {
                final CancelObjectDto cancelObjectDto = message.getPayload();
                this.cancelObjectInPort.cancelObject(
                        cancelObjectDto.getObjectCoo(),
                        cancelObjectDto.getUser()
                );

                this.correlateMessage(message.getHeaders().get(DIGIWF_PROCESS_INSTANCE_ID).toString(),
                        message.getHeaders().get(TYPE).toString(),
                        message.getHeaders().get(DIGIWF_INTEGRATION_NAME).toString(), Map.of());
            });
        };
    }

    public Consumer<Message<ReadContentDto>> readContent() {
        return message -> {
            withErrorHandling(message, () -> {
                final ReadContentDto readContentDto = message.getPayload();
                this.readContentInPort.readContent(
                        readContentDto.getContentCoos(),
                        readContentDto.getUser(),
                        readContentDto.getFilePath(),
                        readContentDto.getFileContext(),
                        message.getHeaders().get(DIGIWF_PROCESS_DEFINITION).toString()
                );
                this.correlateMessage(message.getHeaders().get(DIGIWF_PROCESS_INSTANCE_ID).toString(),
                        message.getHeaders().get(TYPE).toString(),
                        message.getHeaders().get(DIGIWF_INTEGRATION_NAME).toString(), Map.of());
            });
        };
    }

    public Consumer<Message<SearchObjectDto>> searchFile() {
        return message -> {
            withErrorHandling(message, () -> {
                final SearchObjectDto searchObjectDto = message.getPayload();
                final List<String> file = this.searchFileInPort.searchFile(
                        searchObjectDto.getSearchString(),
                        searchObjectDto.getUser(),
                        searchObjectDto.getReference(),
                        searchObjectDto.getValue()
                );
                this.correlateMessage(message.getHeaders().get(DIGIWF_PROCESS_INSTANCE_ID).toString(),
                        message.getHeaders().get(TYPE).toString(),
                        message.getHeaders().get(DIGIWF_INTEGRATION_NAME).toString(), Map.of("fileCoo", file));
            });
        };
    }

    public Consumer<Message<SearchObjectDto>> searchSubjectArea() {
        return message -> {
            withErrorHandling(message, () -> {
                final SearchObjectDto searchObjectDto = message.getPayload();
                final String subjectArea = this.searchSubjectAreaInPort.searchSubjectArea(
                        searchObjectDto.getSearchString(),
                        searchObjectDto.getUser()
                );
                this.correlateMessage(message.getHeaders().get(DIGIWF_PROCESS_INSTANCE_ID).toString(),
                        message.getHeaders().get(TYPE).toString(),
                        message.getHeaders().get(DIGIWF_INTEGRATION_NAME).toString(), Map.of("subjectAreaCoo", subjectArea));
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

    public void correlateMessage(final String processInstanceId, final String type, final String integrationName, final Map<String, Object> message) {
        this.processApi.correlateMessage(processInstanceId, type, integrationName, message);
    }
}
