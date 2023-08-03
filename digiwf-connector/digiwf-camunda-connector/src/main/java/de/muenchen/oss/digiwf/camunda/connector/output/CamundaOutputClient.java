package de.muenchen.oss.digiwf.camunda.connector.output;

import de.muenchen.oss.digiwf.camunda.connector.data.EngineDataSerializer;
import de.muenchen.oss.digiwf.connector.api.output.OutputService;
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
public class CamundaOutputClient implements ExternalTaskHandler {

    private final OutputService outputService;
    private final CamundaOutputConfiguration outputConfiguration;
    private final EngineDataSerializer serializer;

    @Override
    public void execute(final ExternalTask externalTask, final ExternalTaskService externalTaskService) {
        final Map<String, Object> data = this.getData(externalTask);
        final String topic = (String) data.get(CamundaOutputConfiguration.TOPIC_NAME);
        final String type = (String) data.get(CamundaOutputConfiguration.TYPE_NAME);
        log.info("External task received (topic {}, type {})", topic, type);
        final Optional<String> message = Optional.ofNullable(data.get(CamundaOutputConfiguration.MESSAGE_NAME)).map(Object::toString);
        final Map<String, Object> filteredData = this.filterVariables(data);

        if (message.isPresent()) {
            this.outputService.emitEvent(message.get(), topic, type, externalTask.getProcessInstanceId(), filteredData);
        } else {
            this.outputService.emitEvent(topic, type, externalTask.getProcessInstanceId(), filteredData);
        }

        externalTaskService.complete(externalTask);
    }


    private Map<String, Object> filterVariables(final Map<String, Object> variables) {
        return variables.entrySet().stream()
                .filter(entry -> !this.outputConfiguration.getFilteredVariables().contains(entry.getKey()))
                .filter(entry -> entry.getValue() != null)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private Map<String, Object> getData(final ExternalTask externalTask) {
        return this.serializer.fromEngineData(externalTask.getAllVariablesTyped());
    }


}
