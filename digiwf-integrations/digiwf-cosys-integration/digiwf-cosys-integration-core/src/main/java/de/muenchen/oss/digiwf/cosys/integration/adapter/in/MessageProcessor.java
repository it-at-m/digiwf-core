package de.muenchen.oss.digiwf.cosys.integration.adapter.in;


import de.muenchen.oss.digiwf.cosys.integration.application.port.in.CreateDocument;
import de.muenchen.oss.digiwf.cosys.integration.model.GenerateDocument;
import de.muenchen.oss.digiwf.message.process.api.ErrorApi;
import de.muenchen.oss.digiwf.message.process.api.error.BpmnError;
import de.muenchen.oss.digiwf.message.process.api.error.IncidentError;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import javax.validation.ValidationException;
import java.util.function.Consumer;

import static de.muenchen.oss.digiwf.message.common.MessageConstants.DIGIWF_MESSAGE_NAME;
import static de.muenchen.oss.digiwf.message.common.MessageConstants.DIGIWF_PROCESS_INSTANCE_ID;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessageProcessor {

    private final CreateDocument documentUseCase;

    private final ErrorApi errorApi;

    /**
     * All messages from the route "generateDocument" go here.
     *
     * @return the consumer
     */
    @ConditionalOnMissingBean
    @Bean
    public Consumer<Message<GenerateDocument>> cosysIntegration() {
        return message -> {
            try {
            log.info("Processing generate document request from eventbus");
            final GenerateDocument document = message.getPayload();
            log.debug("Generate document request: {}", document);
                this.documentUseCase.createDocument(
                        message.getHeaders().get(DIGIWF_PROCESS_INSTANCE_ID, String.class),
                        message.getHeaders().get(DIGIWF_MESSAGE_NAME, String.class),
                        document);
            } catch (final BpmnError bpmnError) {
                this.errorApi.handleBpmnError(message.getHeaders(), bpmnError);
            } catch (final ValidationException validationException) {
                this.errorApi.handleBpmnError(message.getHeaders(), new BpmnError("VALIDATION_ERROR", validationException.getMessage()));
            } catch (final IncidentError incidentError) {
                this.errorApi.handleIncident(message.getHeaders(), incidentError);
            }
        };
    }

}
