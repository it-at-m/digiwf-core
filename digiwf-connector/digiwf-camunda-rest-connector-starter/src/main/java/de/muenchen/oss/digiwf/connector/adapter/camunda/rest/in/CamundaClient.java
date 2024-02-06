package de.muenchen.oss.digiwf.connector.adapter.camunda.rest.in;

import de.muenchen.oss.digiwf.connector.adapter.camunda.rest.mapper.EngineDataSerializer;
import de.muenchen.oss.digiwf.connector.core.application.port.in.ExecuteTaskInPort;
import de.muenchen.oss.digiwf.connector.core.application.port.in.ExecuteTaskInPort.ExecuteTaskCommand;
import de.muenchen.oss.digiwf.connector.core.domain.IntegrationNameConfigException;
import de.muenchen.oss.digiwf.connector.core.domain.ProcessDefinitionLoadingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskHandler;
import org.camunda.bpm.client.task.ExternalTaskService;

import java.util.Map;
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
        String integrationName = (String) data.get(CamundaClientConfiguration.INTEGRATION_NAME);
        final String customTopic = (String) data.get(CamundaClientConfiguration.TOPIC_NAME);
        final String type = (String) data.get(CamundaClientConfiguration.TYPE_NAME);
        log.info("External task received (integration {}, type {})", integrationName, type);
        final Map<String, Object> filteredData = this.filterVariables(data);

        // TODO: Remove this fallback after all processes are migrated to the new version. It's a legacy feature to avoid breaking changes.
        if (integrationName == null) {
            log.warn("Integration name is null. Falling back to deprecated legacy feature. Please update your process definition.");
            integrationName = "deprecatedLegacyFeature";
        }

        try {
            executeTaskInPort.executeTask(ExecuteTaskCommand.builder()
                    .customDestination(customTopic)
                    .integrationName(integrationName)
                    .type(type)
                    .instanceId(externalTask.getProcessInstanceId())
                    .data(filteredData)
                    .build());

            externalTaskService.complete(externalTask);
        } catch (final IntegrationNameConfigException e) {
            externalTaskService.handleFailure(externalTask, e.getMessage(), e.getDetailedMessage(), 0, 0);
        } catch (final ProcessDefinitionLoadingException e) {
            externalTaskService.handleFailure(externalTask, e.getMessage(), e.getDetailedMessage(), 0, 0);
        }
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
