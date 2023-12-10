package de.muenchen.oss.digiwf.connector.adapter.in.streaming;

import de.muenchen.oss.digiwf.connector.application.port.in.CreateBpmnErrorInPort;
import de.muenchen.oss.digiwf.connector.domain.BpmnError;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

/**
 * Generic Listener to correlate bpmn errors to processes.
 *
 * @author martind260
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
        BpmnError bpmnError = new BpmnError();
        bpmnError.setProcessInstanceId(event.getProcessInstanceId());
        bpmnError.setMessageName(event.getMessageName());
        bpmnError.setErrorCode(event.getErrorCode());
        bpmnError.setErrorMessage(event.getErrorMessage());
        return bpmnError;
    }
}
