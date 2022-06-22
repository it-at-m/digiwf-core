package io.muenchendigital.digiwf.engine.streaming.process;

import io.muenchendigital.digiwf.engine.streaming.api.StreamingService;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * This template is used by the platform Call Activity for Request / Reply Pattern.
 * Therefore variables of instance (Call Acivty) level are referenced.
 *
 * @author externer.dl.horn
 */
@Slf4j
@Component
public class StreamingTemplateIntegrationDelegate extends AbstractStreamingIntegrationDelegate implements JavaDelegate {

    public StreamingTemplateIntegrationDelegate(final StreamingService streamingService) {
        super(streamingService);
    }

    @Override
    public void execute(final DelegateExecution delegateExecution) throws Exception {
        final Map<String, Object> variables = delegateExecution.getVariables();
        this.emitMessage(delegateExecution, variables);
    }
}
