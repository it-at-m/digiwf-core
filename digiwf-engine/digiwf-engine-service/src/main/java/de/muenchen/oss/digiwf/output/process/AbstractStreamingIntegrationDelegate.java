package de.muenchen.oss.digiwf.output.process;

import de.muenchen.oss.digiwf.engine.mapper.EngineDataMapper;
import io.holunda.camunda.bpm.data.factory.VariableFactory;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static de.muenchen.oss.digiwf.shared.streaming.StreamingHeaders.*;
import static io.holunda.camunda.bpm.data.CamundaBpmData.stringVariable;

/**
 * This Abstract streaming integration delegate provides basic functionality to write messages to the event stream.
 *
 * @author externer.dl.horn
 */
public class AbstractStreamingIntegrationDelegate {
    private final EngineDataMapper engineDataMapper;

    private final static VariableFactory<String> MESSAGE_NAME = stringVariable("app_message_name");
    private final static VariableFactory<String> TYPE_NAME = stringVariable("app_type_name");
    private final static VariableFactory<String> TOPIC_NAME = stringVariable("app_topic_name");
    private final static VariableFactory<String> RESPONSELESS = stringVariable("app_responseless");
    private final static List<String> filterVariables = List.of(
            MESSAGE_NAME.getName(),
            TYPE_NAME.getName(),
            TOPIC_NAME.getName(),
            RESPONSELESS.getName()
    );

    public AbstractStreamingIntegrationDelegate(final EngineDataMapper engineDataMapper) {
        this.engineDataMapper = engineDataMapper;
    }

    protected Message<Map<String, Object>> mapMessage(final DelegateExecution delegateExecution, final Map<String, Object> variables) {
        final Map<String, Object> data = this.filterVariables(variables);

        final MessageBuilder<Map<String, Object>> builder = MessageBuilder
                .withPayload(this.engineDataMapper.mapToData(data))
                .setHeader(STREAM_SEND_TO_DESTINATION, TOPIC_NAME.from(delegateExecution).get())
                .setHeader(TYPE, TYPE_NAME.from(delegateExecution).get())
                .setHeader(DIGIWF_PROCESS_INSTANCE_ID, delegateExecution.getProcessInstanceId());
        MESSAGE_NAME.from(delegateExecution).getOptional().ifPresent(name -> builder.setHeader(DIGIWF_MESSAGE_NAME, name));
        return builder.build();
    }

    private Map<String, Object> filterVariables(final Map<String, Object> variables) {
        return variables.entrySet().stream()
                .filter(entry -> !filterVariables.contains(entry.getKey()))
                .filter(entry -> entry.getValue() != null)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
