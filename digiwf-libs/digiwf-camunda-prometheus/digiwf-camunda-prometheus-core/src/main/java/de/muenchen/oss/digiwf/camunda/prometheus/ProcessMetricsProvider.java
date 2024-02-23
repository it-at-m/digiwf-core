package de.muenchen.oss.digiwf.camunda.prometheus;

import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Gauge;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.repository.ProcessDefinition;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.groupingBy;

@RequiredArgsConstructor
public class ProcessMetricsProvider implements MetricsProvider {

    private final RuntimeService runtimeService;
    private final RepositoryService repositoryService;

    private Gauge processDefinitionCount;
    private Gauge processDefinitionCountUnique;
    private Gauge processInstanceCount;

    @Override
    public void updateMetrics() {

        Map<String, List<ProcessDefinition>> maxVersionByProcessDefinitionKey = repositoryService.createProcessDefinitionQuery().list().stream()
            .sorted(comparing(ProcessDefinition::getVersion).reversed())
            .collect(groupingBy(ProcessDefinition::getKey));

        processDefinitionCount.set(maxVersionByProcessDefinitionKey.values().stream().mapToLong(List::size).sum());
        processDefinitionCountUnique.set(maxVersionByProcessDefinitionKey.size());

        maxVersionByProcessDefinitionKey.forEach((processDefinitionKey, processDefinitions) -> {
            long instanceCountByKey = runtimeService.createProcessInstanceQuery()
                .processDefinitionKey(processDefinitionKey)
                .count();
            long instanceCountLatestVersion = runtimeService.createProcessInstanceQuery()
                .processDefinitionId(processDefinitions.get(0).getId())
                .count();

            processInstanceCount.labels(processDefinitionKey, "true")
                .set(instanceCountLatestVersion);
            processInstanceCount.labels(processDefinitionKey, "false")
                .set(instanceCountByKey - instanceCountLatestVersion);
        });
    }

    @Override
    public void registerMetrics(final CollectorRegistry collectorRegistry) {
        this.processDefinitionCount = Gauge.build()
                .name("camunda_deployed_process_definitions")
                .help("Number of deployed process definitions.")
                .register(collectorRegistry);

        this.processDefinitionCountUnique = Gauge.build()
                .name("camunda_deployed_process_definitions_unique")
                .help("Number of deployed process definitions, ignoring different versions of the same definition.")
                .register(collectorRegistry);

        this.processInstanceCount = Gauge.build()
                .name("camunda_running_process_instances")
                .help("Running process instances by process definition key.")
                .labelNames("processDefinitionKey", "latest")
                .register(collectorRegistry);
    }
}
