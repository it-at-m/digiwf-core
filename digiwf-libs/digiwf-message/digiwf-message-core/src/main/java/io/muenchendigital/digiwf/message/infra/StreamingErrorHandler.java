package io.muenchendigital.digiwf.message.infra;

import io.muenchendigital.digiwf.message.common.MessageConstants;
import io.muenchendigital.digiwf.message.common.error.BpmnError;
import io.muenchendigital.digiwf.message.common.error.IncidentError;
import io.muenchendigital.digiwf.message.process.api.ProcessApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.support.ErrorMessage;

import java.util.Optional;
import java.util.function.Consumer;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class StreamingErrorHandler {

    private final ProcessApi processApi;

    @Bean
    public Consumer<ErrorMessage> digiwfErrorHandler() {
        return error -> {
            Optional<Object> processInstance = Optional.empty();
            Optional<Object> messageName = Optional.empty();

            if (error.getPayload() instanceof MessagingException) {
                final MessagingException messageHandlingException = (MessagingException) error.getPayload();
                final Message<?> failedMessage = messageHandlingException.getFailedMessage();
                processInstance = Optional.ofNullable(failedMessage.getHeaders().get(MessageConstants.DIGIWF_PROCESS_INSTANCE_ID));
                messageName = Optional.ofNullable(failedMessage.getHeaders().get(MessageConstants.DIGIWF_MESSAGE_NAME));
            }

            // bpmn error
            if (processInstance.isPresent() && error.getPayload().getCause() instanceof BpmnError) {
                final BpmnError bpmnError = (BpmnError) error.getPayload().getCause();
                this.processApi.handleBpmnError(processInstance.get().toString(), bpmnError.getErrorCode(), bpmnError.getErrorMessage());
                log.info("Handling technical error for process {} error {}", processInstance.get(), bpmnError.getErrorMessage());
                return;
            }

            if (processInstance.isPresent() && messageName.isPresent() && error.getPayload().getCause() instanceof IncidentError) {
                final IncidentError incidentError = (IncidentError) error.getPayload().getCause();
                this.processApi.handleIncident(processInstance.get().toString(), messageName.get().toString(), incidentError.getErrorMessage());
                log.info("Handling technical error for process {} error {}", processInstance.get(), incidentError.getErrorMessage());
                return;
            }

            log.error("Exception was not handled. Exception was {}", error.getPayload().getCause().getMessage());
        };
    }

}
