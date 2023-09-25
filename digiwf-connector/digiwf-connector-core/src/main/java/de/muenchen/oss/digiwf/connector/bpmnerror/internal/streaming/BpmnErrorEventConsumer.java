package de.muenchen.oss.digiwf.connector.bpmnerror.internal.streaming;

import de.muenchen.oss.digiwf.connector.api.bpmnerror.BpmnErrorEvent;
import de.muenchen.oss.digiwf.connector.api.bpmnerror.BpmnErrorService;
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

    private final BpmnErrorService bpmnErrorService;

    @Bean
    public Consumer<Message<BpmnErrorEvent>> createBpmnError() {
        return correlation -> {
            log.info("Received bpmn error correlation {}", correlation.getPayload());
            this.bpmnErrorService.createBpmnError(correlation.getPayload());
        };
    }
}
