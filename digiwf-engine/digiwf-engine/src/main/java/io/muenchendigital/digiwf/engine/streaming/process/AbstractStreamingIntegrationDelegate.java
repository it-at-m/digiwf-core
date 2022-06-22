package io.muenchendigital.digiwf.engine.streaming.process;

import io.holunda.camunda.bpm.data.factory.VariableFactory;
import io.muenchendigital.digiwf.engine.streaming.api.StreamingService;
import org.camunda.bpm.engine.delegate.DelegateExecution;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static io.holunda.camunda.bpm.data.CamundaBpmData.stringVariable;

/**
 * This Abstract streaming integration delegate provides basic functionality to write messages to the event stream.
 *
 * @author externer.dl.horn
 */
public class AbstractStreamingIntegrationDelegate {
    private final StreamingService streamingService;

    private final static VariableFactory<String> MESSAGE_NAME = stringVariable("app_message_name");
    private final static VariableFactory<String> TYPE_NAME = stringVariable("app_type_name");
    private final static VariableFactory<String> TOPIC_NAME = stringVariable("app_topic_name");
    private final static List<String> filterVariables = List.of(MESSAGE_NAME.getName(), TYPE_NAME.getName(), TOPIC_NAME.getName());

    public AbstractStreamingIntegrationDelegate(final StreamingService streamingService) {
        this.streamingService = streamingService;
    }

    protected void emitMessage(final DelegateExecution delegateExecution, final Map<String, Object> variables) {
        final Map<String, Object> data = this.filterVariables(variables);

        final String destination = TOPIC_NAME.from(delegateExecution).get();
        final String type = TYPE_NAME.from(delegateExecution).get();
        final String instanceId = delegateExecution.getProcessInstanceId();
        final Optional<String> messageName = MESSAGE_NAME.from(delegateExecution).getOptional();

        if (messageName.isPresent()) {
            this.streamingService.emitMessage(messageName.get(), destination, type, instanceId, data);
        } else {
            this.streamingService.emitMessage(destination, type, instanceId, data);

        }
    }

    protected Map<String, Object> filterVariables(final Map<String, Object> variables) {
        return variables.entrySet().stream()
                .filter(entry -> !filterVariables.contains(entry.getKey()))
                .filter(entry -> entry.getValue() != null)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
