package de.muenchen.oss.digiwf.email.integration.adapter.in;

import de.muenchen.oss.digiwf.email.integration.application.port.in.SendMail;
import de.muenchen.oss.digiwf.email.integration.infrastructure.MonitoringService;
import de.muenchen.oss.digiwf.email.integration.model.Mail;
import de.muenchen.oss.digiwf.message.process.api.ErrorApi;
import de.muenchen.oss.digiwf.message.process.api.error.BpmnError;
import de.muenchen.oss.digiwf.message.process.api.error.IncidentError;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;

import jakarta.validation.ValidationException;
import java.util.function.Consumer;

import static de.muenchen.oss.digiwf.message.common.MessageConstants.DIGIWF_MESSAGE_NAME;
import static de.muenchen.oss.digiwf.message.common.MessageConstants.DIGIWF_PROCESS_INSTANCE_ID;

@RequiredArgsConstructor
public class MessageProcessor {

    private final ErrorApi errorApi;
    private final SendMail mailUseCase;
    private final MonitoringService monitoringService;

    @ConditionalOnMissingBean
    @Bean
    public Consumer<Message<Mail>> emailIntegration() {
        return message -> {
            try {
                this.mailUseCase.sendMail(
                        message.getHeaders().get(DIGIWF_PROCESS_INSTANCE_ID, String.class),
                        message.getHeaders().get(DIGIWF_MESSAGE_NAME, String.class),
                        message.getPayload());
                this.monitoringService.sendMailSucceeded();
            } catch (final BpmnError bpmnError) {
                this.monitoringService.sendMailFailed();
                this.errorApi.handleBpmnError(message.getHeaders(), bpmnError);
            } catch (final ValidationException validationException) {
                this.monitoringService.sendMailFailed();
                this.errorApi.handleBpmnError(message.getHeaders(), new BpmnError("VALIDATION_ERROR", validationException.getMessage()));
            } catch (final IncidentError incidentError) {
                this.monitoringService.sendMailFailed();
                this.errorApi.handleIncident(message.getHeaders(), incidentError);
            }
        };
    }

}
