package de.muenchen.oss.digiwf.dms.integration.adapter.in;

import de.muenchen.oss.digiwf.dms.integration.application.port.in.CreateVorgangUseCase;
import de.muenchen.oss.digiwf.dms.integration.domain.Vorgang;
import de.muenchen.oss.digiwf.message.process.api.ErrorApi;
import de.muenchen.oss.digiwf.message.process.api.ProcessApi;
import de.muenchen.oss.digiwf.message.process.api.error.BpmnError;
import de.muenchen.oss.digiwf.message.process.api.error.IncidentError;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;

import java.util.Map;
import java.util.function.Consumer;

import static de.muenchen.oss.digiwf.message.common.MessageConstants.DIGIWF_MESSAGE_NAME;
import static de.muenchen.oss.digiwf.message.common.MessageConstants.DIGIWF_PROCESS_INSTANCE_ID;

@RequiredArgsConstructor
public class MessageProcessor {

    private final ProcessApi processApi;
    private final ErrorApi errorApi;
    private final CreateVorgangUseCase createVorgangUseCase;

    @Bean
    public Consumer<Message<CreateVorgangDto>> exampleIntegration() {
        return message -> {
            try {
                final CreateVorgangDto createVorgangDto = message.getPayload();
                final Vorgang vorgang = this.createVorgangUseCase.createVorgang(
                        createVorgangDto.getTitle(),
                        createVorgangDto.getSachakteCoo(),
                        createVorgangDto.getUser());

                this.correlateMessage(message.getHeaders().get(DIGIWF_PROCESS_INSTANCE_ID).toString(),
                        message.getHeaders().get(DIGIWF_MESSAGE_NAME).toString(), Map.of("vorgangCoo", vorgang.getCoo()));
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
