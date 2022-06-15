package io.muenchendigital.digiwf.engine.streaming.process;

import io.muenchendigital.digiwf.engine.data.EngineDataSerializer;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Sinks;

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

    private final Sinks.Many<Message<Map<String, Object>>> dynamicSink;

    public StreamingIntegrationDelegate(final EngineDataSerializer engineDataMapper, final Sinks.Many<Message<Map<String, Object>>> dynamicSink) {
        super(engineDataMapper);
        this.dynamicSink = dynamicSink;
    }

    @Override
    public void execute(final DelegateExecution delegateExecution) throws Exception {
        final Map<String, Object> localVariables = delegateExecution.getVariablesLocal();
        log.debug("Send event to stream {}", localVariables);
        final Message<Map<String, Object>> message = this.mapMessage(delegateExecution, localVariables);
        this.dynamicSink.tryEmitNext(message).orThrow();
    }

}
