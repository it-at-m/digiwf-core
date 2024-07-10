package de.muenchen.oss.digiwf.email.integration.adapter.in.streaming;

import de.muenchen.oss.digiwf.email.integration.application.port.in.SendMailPathsInPort;
import de.muenchen.oss.digiwf.email.integration.application.port.in.SendMailPresignedInPort;
import de.muenchen.oss.digiwf.email.integration.domain.model.paths.TextMailPaths;
import de.muenchen.oss.digiwf.email.integration.domain.model.presigned.TextMailPresigned;
import de.muenchen.oss.digiwf.email.integration.infrastructure.MonitoringService;
import de.muenchen.oss.digiwf.message.process.api.ErrorApi;
import de.muenchen.oss.digiwf.message.process.api.ProcessApi;
import de.muenchen.oss.digiwf.message.process.api.error.BpmnError;
import de.muenchen.oss.digiwf.message.process.api.error.IncidentError;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import java.util.Map;
import java.util.function.Consumer;

import static de.muenchen.oss.digiwf.message.common.MessageConstants.*;

@RequiredArgsConstructor
public class StreamingAdapter {

    private final ProcessApi processApi;
    private final ErrorApi errorApi;
    private final SendMailPresignedInPort sendMailPresignedInPort;
    private final SendMailPathsInPort sendMailPathsInPort;
    private final MonitoringService monitoringService;

    @Deprecated
    public Consumer<Message<TextMailPresigned>> emailIntegration() {
        return message -> {
            withErrorHandling(message, () -> {
                this.sendMailPresignedInPort.sendMailWithText(message.getPayload());
                this.correlateMessage(
                        message.getHeaders(),
                        Map.of("mailSentStatus", true)
                );
                this.monitoringService.sendMailSucceeded();
            });
        };
    }

    @Deprecated
    public Consumer<Message<MailWithLogoAndLinkPresignedDto>> sendMailWithLogoAndLink() {
        return message -> {
            withErrorHandling(message, () -> {
                MailWithLogoAndLinkPresignedDto mail = message.getPayload();
                this.sendMailPresignedInPort.sendMailWithTemplate(mail.toTemplateMailPresiged());
                this.correlateMessage(
                        message.getHeaders(),
                        Map.of("mailSentStatus", true)
                );
                this.monitoringService.sendMailSucceeded();
            });
        };
    }

    public Consumer<Message<TextMailPaths>> sendTextMailV2() {
        return message -> {
            withErrorHandling(message, () -> {
                this.sendMailPathsInPort.sendMailWithText(message.getPayload());
                this.correlateMessage(
                        message.getHeaders(),
                        Map.of("mailSentStatus", true)
                );
                this.monitoringService.sendMailSucceeded();
            });
        };
    }

    public Consumer<Message<MailWithLogoAndLinkPathsDto>> sendMailWithLogoAndLinkV2() {
        return message -> {
            withErrorHandling(message, () -> {
                MailWithLogoAndLinkPathsDto mail = message.getPayload();
                this.sendMailPathsInPort.sendMailWithTemplate(mail.toTemplateMailPaths());
                this.correlateMessage(
                        message.getHeaders(),
                        Map.of("mailSentStatus", true)
                );
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

    public void correlateMessage(MessageHeaders headers, Map<String, Object> content) {
        this.processApi.correlateMessage(
                headers.get(DIGIWF_PROCESS_INSTANCE_ID, String.class),
                headers.get(TYPE, String.class),
                headers.get(DIGIWF_INTEGRATION_NAME, String.class),
                content
        );
    }
}
