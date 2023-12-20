package de.muenchen.oss.digiwf.connector.adapter.camunda.rest.in;

import de.muenchen.oss.digiwf.connector.adapter.camunda.rest.mapper.EngineDataSerializer;
import de.muenchen.oss.digiwf.connector.core.application.port.in.ExecuteTaskInPort;
import de.muenchen.oss.digiwf.connector.core.application.port.in.ExecuteTaskInPort.ExecuteTaskCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskHandler;
import org.camunda.bpm.client.task.ExternalTaskService;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Slf4j
public class CamundaClient implements ExternalTaskHandler {

    private final ExecuteTaskInPort executeTaskInPort;
    private final CamundaClientConfiguration outputConfiguration;
    private final EngineDataSerializer serializer;

    @Override
    public void execute(final ExternalTask externalTask, final ExternalTaskService externalTaskService) {
        final Map<String, Object> data = this.getData(externalTask);
        final String topic = (String) data.get(CamundaClientConfiguration.TOPIC_NAME);
        final String type = (String) data.get(CamundaClientConfiguration.TYPE_NAME);
        log.info("External task received (topic {}, type {})", topic, type);
        final Optional<String> message = Optional.ofNullable(data.get(CamundaClientConfiguration.MESSAGE_NAME)).map(Object::toString);
        final Map<String, Object> filteredData = this.filterVariables(data);

        executeTaskInPort.executeTask(ExecuteTaskCommand.builder()
                .messageName(message.orElse(null))
                .destination(topic)
                .type(type)
                .instanceId(externalTask.getProcessInstanceId())
                .data(filteredData)
                .build());

        externalTaskService.complete(externalTask);
    }


    private Map<String, Object> filterVariables(final Map<String, Object> variables) {
        return variables.entrySet().stream()
                .filter(entry -> !this.outputConfiguration.getFilters().contains(entry.getKey()))
                .filter(entry -> entry.getValue() != null)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private Map<String, Object> getData(final ExternalTask externalTask) {
        return this.serializer.fromEngineData(externalTask.getAllVariablesTyped());
    }


}
