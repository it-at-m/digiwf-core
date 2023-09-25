package de.muenchen.oss.digiwf.dms.integration.adapter.in;

import de.muenchen.oss.digiwf.dms.integration.application.port.in.CreateDocumentUseCase;
import de.muenchen.oss.digiwf.dms.integration.application.port.in.CreateProcedureUseCase;
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

    public Consumer<Message<CreateProcedureDto>> createProcedure() {
        return message -> {
            try {
                final CreateProcedureDto createProcedureDto = message.getPayload();
                final Procedure vorgang = this.createProcedureUseCase.createProcedure(
                        createProcedureDto.getTitle(),
                        createProcedureDto.getFileCOO(),
                        createProcedureDto.getUser()
                );

                this.correlateMessage(Objects.requireNonNull(message.getHeaders().get(DIGIWF_PROCESS_INSTANCE_ID)).toString(),
                        Objects.requireNonNull(message.getHeaders().get(DIGIWF_MESSAGE_NAME)).toString(), Map.of("procedureCOO", vorgang.getCoo()));
            } catch (final BpmnError bpmnError) {
                this.errorApi.handleBpmnError(message.getHeaders(), bpmnError);
            } catch (final IncidentError incidentError) {
                this.errorApi.handleIncident(message.getHeaders(), incidentError);
            } catch (final ValidationException validationException) {
                this.errorApi.handleIncident(message.getHeaders(), new IncidentError(validationException.getMessage()));
            }
        };
    }

    public Consumer<Message<CreateDocumentDto>> createDocument() {
        return message -> {
            try {
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
            } catch (final BpmnError bpmnError) {
                this.errorApi.handleBpmnError(message.getHeaders(), bpmnError);
            } catch (final IncidentError incidentError) {
                this.errorApi.handleIncident(message.getHeaders(), incidentError);
            } catch (final ValidationException validationException) {
                this.errorApi.handleIncident(message.getHeaders(), new IncidentError(validationException.getMessage()));
            }
        };
    }

    public void correlateMessage(final String processInstanceId, final String messageName, final Map<String, Object> message) {
        this.processApi.correlateMessage(processInstanceId, messageName, message);
    }
}
