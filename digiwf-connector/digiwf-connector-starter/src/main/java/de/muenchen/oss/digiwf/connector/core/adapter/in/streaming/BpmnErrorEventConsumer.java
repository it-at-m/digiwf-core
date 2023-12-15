package de.muenchen.oss.digiwf.connector.core.adapter.in.streaming;

import de.muenchen.oss.digiwf.connector.core.application.port.in.CreateBpmnErrorInPort;
import de.muenchen.oss.digiwf.connector.core.domain.BpmnError;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

/**
 * Generic Listener to correlate bpmn errors to processes.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class BpmnErrorEventConsumer {

    private final CreateBpmnErrorInPort inPort;

    @Bean
    public Consumer<Message<BpmnErrorDto>> createBpmnError() {
        return correlation -> {
            log.info("Received bpmn error correlation {}", correlation.getPayload());
            this.inPort.createBpmnError(map(correlation.getPayload()));
        };
    }

    private BpmnError map(BpmnErrorDto event) {
        return BpmnError.builder()
                .processInstanceId(event.getProcessInstanceId())
                .messageName(event.getMessageName())
                .errorCode(event.getErrorCode())
                .errorMessage(event.getErrorMessage())
                .build();
    }
}
