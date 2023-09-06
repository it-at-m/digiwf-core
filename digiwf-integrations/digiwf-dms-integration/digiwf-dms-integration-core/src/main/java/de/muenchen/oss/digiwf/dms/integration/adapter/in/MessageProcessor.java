package de.muenchen.oss.digiwf.dms.integration.adapter.in;

import de.muenchen.oss.digiwf.dms.integration.application.port.in.CreateDokumentUseCase;
import de.muenchen.oss.digiwf.dms.integration.application.port.in.CreateVorgangUseCase;
import de.muenchen.oss.digiwf.dms.integration.domain.Dokument;
import de.muenchen.oss.digiwf.dms.integration.domain.DokumentArt;
import de.muenchen.oss.digiwf.dms.integration.domain.Vorgang;
import de.muenchen.oss.digiwf.dms.integration.domain.VorgangArt;
import de.muenchen.oss.digiwf.message.process.api.ErrorApi;
import de.muenchen.oss.digiwf.message.process.api.ProcessApi;
import de.muenchen.oss.digiwf.message.process.api.error.BpmnError;
import de.muenchen.oss.digiwf.message.process.api.error.IncidentError;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

import javax.validation.ValidationException;
import java.util.Map;
import java.util.function.Consumer;

import static de.muenchen.oss.digiwf.message.common.MessageConstants.DIGIWF_MESSAGE_NAME;
import static de.muenchen.oss.digiwf.message.common.MessageConstants.DIGIWF_PROCESS_INSTANCE_ID;

@Configuration
@RequiredArgsConstructor
public class MessageProcessor {

    private final ProcessApi processApi;
    private final ErrorApi errorApi;
    private final CreateVorgangUseCase createVorgangUseCase;
    private final CreateDokumentUseCase createDokumentUseCase;

    @Bean
    public Consumer<Message<CreateVorgangDto>> createVorgang() {
        return message -> {
            try {
                final CreateVorgangDto createVorgangDto = message.getPayload();
                final Vorgang vorgang = this.createVorgangUseCase.createVorgang(
                        createVorgangDto.getTitle(),
                        createVorgangDto.getSachakteCoo(),
                        VorgangArt.valueOf(createVorgangDto.getArt()),
                        createVorgangDto.getUser()
                );

                this.correlateMessage(message.getHeaders().get(DIGIWF_PROCESS_INSTANCE_ID).toString(),
                        message.getHeaders().get(DIGIWF_MESSAGE_NAME).toString(), Map.of("vorgangCoo", vorgang.getCoo()));
            } catch (final BpmnError bpmnError) {
                this.errorApi.handleBpmnError(message.getHeaders(), bpmnError);
            } catch (final IncidentError incidentError) {
                this.errorApi.handleIncident(message.getHeaders(), incidentError);
            } catch (final ValidationException validationException) {
                this.errorApi.handleIncident(message.getHeaders(), new IncidentError(validationException.getMessage()));
            }
        };
    }

    @Bean
    public Consumer<Message<CreateDokumentDto>> createDokument() {
        return message -> {
            try {
                final CreateDokumentDto createDokumentDto = message.getPayload();
                final Dokument dokument = this.createDokumentUseCase.createDocument(
                        createDokumentDto.getVorgangCoo(),
                        createDokumentDto.getTitle(),
                        createDokumentDto.getUser(),
                        DokumentArt.valueOf(createDokumentDto.getArt()),
                        createDokumentDto.getS3Dateien()
                );

                this.correlateMessage(message.getHeaders().get(DIGIWF_PROCESS_INSTANCE_ID).toString(),
                        message.getHeaders().get(DIGIWF_MESSAGE_NAME).toString(), Map.of("dokumentCoo", dokument.getCoo()));
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
