package io.muenchendigital.digiwf.engine.streaming.process;

import io.muenchendigital.digiwf.engine.streaming.api.StreamingService;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * This delegate can be used for Service Tasks in every process step.
 * It is possible to define a message name.
 *
 * @author externer.dl.horn
 */
@Slf4j
@Component
public class StreamingIntegrationDelegate extends AbstractStreamingIntegrationDelegate implements JavaDelegate {

    public StreamingIntegrationDelegate(final StreamingService streamingService) {
        super(streamingService);
    }

    @Override
    public void execute(final DelegateExecution delegateExecution) throws Exception {
        final Map<String, Object> localVariables = delegateExecution.getVariablesLocal();
        this.emitMessage(delegateExecution, localVariables);
    }

}
