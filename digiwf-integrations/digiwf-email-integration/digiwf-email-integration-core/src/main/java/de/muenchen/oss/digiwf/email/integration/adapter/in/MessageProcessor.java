package de.muenchen.oss.digiwf.email.integration.adapter.in;

import de.muenchen.oss.digiwf.email.integration.application.port.in.SendMailInPort;
import de.muenchen.oss.digiwf.email.integration.infrastructure.MonitoringService;
import de.muenchen.oss.digiwf.email.integration.model.TextMail;
import de.muenchen.oss.digiwf.email.integration.model.TemplateMail;
import de.muenchen.oss.digiwf.message.process.api.ErrorApi;
import de.muenchen.oss.digiwf.message.process.api.error.BpmnError;
import de.muenchen.oss.digiwf.message.process.api.error.IncidentError;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;

import java.util.Map;
import java.util.function.Consumer;

import static de.muenchen.oss.digiwf.message.common.MessageConstants.*;

@RequiredArgsConstructor
public class MessageProcessor {

    private final ErrorApi errorApi;
    private final SendMailInPort mailInPort;
    private final MonitoringService monitoringService;

    public Consumer<Message<TextMail>> emailIntegration() {

        return message -> {
            withErrorHandling(message, () -> {
                this.mailInPort.sendMailWithText(
                        message.getHeaders().get(DIGIWF_PROCESS_INSTANCE_ID, String.class),
                        message.getHeaders().get(TYPE, String.class),
                        message.getHeaders().get(DIGIWF_INTEGRATION_NAME, String.class),
                        message.getPayload());
                this.monitoringService.sendMailSucceeded();
            });
        };
    }

    public Consumer<Message<MailWithLogoAndLinkDto>> sendMailWithLogoAndLink() {

        return message -> {
            withErrorHandling(message, () -> {
                MailWithLogoAndLinkDto mail = message.getPayload();
                this.mailInPort.sendMailWithTemplate(
                        message.getHeaders().get(DIGIWF_PROCESS_INSTANCE_ID, String.class),
                        message.getHeaders().get(TYPE, String.class),
                        message.getHeaders().get(DIGIWF_INTEGRATION_NAME, String.class),
                        convertToTemplateMail(mail, mail.getTemplate(), Map.of("mail", mail)));
                this.monitoringService.sendMailSucceeded();
            });
        };
    }

    private void withErrorHandling(final Message<?> message, final Runnable runnable) {
        try {
            runnable.run();
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
    }

    private TemplateMail convertToTemplateMail(BasicMailDto basicMail, String template, Map<String, Object> content) {
        return new TemplateMail(
                basicMail.getReceivers(),
                basicMail.getReceiversCc(),
                basicMail.getReceiversBcc(),
                basicMail.getSubject(),
                basicMail.getReplyTo(),
                basicMail.getAttachments(),
                template,
                content);
    }

}
