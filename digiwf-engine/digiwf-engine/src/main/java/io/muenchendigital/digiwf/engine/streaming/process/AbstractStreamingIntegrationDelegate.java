package io.muenchendigital.digiwf.engine.streaming.process;

import io.holunda.camunda.bpm.data.factory.VariableFactory;
import io.muenchendigital.digiwf.engine.data.EngineDataSerializer;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static io.holunda.camunda.bpm.data.CamundaBpmData.stringVariable;
import static io.muenchendigital.digiwf.engine.streaming.internal.StreamingHeaders.*;

/**
 * This Abstract streaming integration delegate provides basic functionality to write messages to the event stream.
 *
 * @author externer.dl.horn
 */
public class AbstractStreamingIntegrationDelegate {
    private final EngineDataSerializer engineDataMapper;

    private final static VariableFactory<String> MESSAGE_NAME = stringVariable("app_message_name");
    private final static VariableFactory<String> TYPE_NAME = stringVariable("app_type_name");
    private final static VariableFactory<String> TOPIC_NAME = stringVariable("app_topic_name");
    private final static List<String> filterVariables = List.of(MESSAGE_NAME.getName(), TYPE_NAME.getName(), TOPIC_NAME.getName());

    public AbstractStreamingIntegrationDelegate(final EngineDataSerializer engineDataMapper) {
        this.engineDataMapper = engineDataMapper;
    }

    protected Message<Map<String, Object>> mapMessage(final DelegateExecution delegateExecution, final Map<String, Object> variables) {
        final Map<String, Object> data = this.filterVariables(variables);

        final MessageBuilder<Map<String, Object>> builder = MessageBuilder
                .withPayload(this.engineDataMapper.fromEngineData(data))
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
