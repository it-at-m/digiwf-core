package io.muenchendigital.digiwf.email.integration.adapter;

import io.muenchendigital.digiwf.email.integration.application.port.in.SendMail;
import io.muenchendigital.digiwf.email.integration.application.port.out.CorrelateMessagePort;
import io.muenchendigital.digiwf.email.integration.model.Mail;
import io.muenchendigital.digiwf.message.process.api.ErrorApi;
import io.muenchendigital.digiwf.message.process.api.ProcessApi;
import io.muenchendigital.digiwf.message.process.api.error.BpmnError;
import io.muenchendigital.digiwf.message.process.api.error.IncidentError;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

import javax.validation.ValidationException;
import java.util.Map;
import java.util.function.Consumer;

import static io.muenchendigital.digiwf.message.common.MessageConstants.DIGIWF_PROCESS_INSTANCE_ID;

@Configuration
@RequiredArgsConstructor
public class MessageProcessor implements CorrelateMessagePort {

    private final ProcessApi processApi;
    private final ErrorApi errorApi;
    private final SendMail mailUseCase;


    @Bean
    public Consumer<Message<Mail>> exampleIntegration() {
        return message -> {
            try {
                this.mailUseCase.sendMail(message.getHeaders().get(DIGIWF_PROCESS_INSTANCE_ID, String.class), message.getPayload());
            } catch (final BpmnError bpmnError) {
                this.errorApi.handleBpmnError(message.getHeaders(), bpmnError);
            } catch (final ValidationException validationException) {
                this.errorApi.handleBpmnError(message.getHeaders(), new BpmnError("VALIDATION_ERROR", validationException.getMessage()));
            } catch (final IncidentError incidentError) {
                this.errorApi.handleIncident(message.getHeaders(), incidentError);
            }
        };
    }

    @Override
    public void correlateMessage(final String processInstanceId, final String messageName, final Map<String, Object> message) {
        this.processApi.correlateMessage(processInstanceId, messageName, message);
    }

}
