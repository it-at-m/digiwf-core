package de.muenchen.oss.digiwf.camunda.prometheus;

import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Gauge;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.repository.ProcessDefinition;

import static java.util.stream.Collectors.toMap;

@RequiredArgsConstructor
public class IncidentMetricsProvider implements MetricsProvider {

    private final RuntimeService runtimeService;
    private final RepositoryService repositoryService;
    private static final String NOT_PROCESS_INCIDENT = "__no_process_definition_key";

    private Gauge openIncidents;

    @Override
    public void updateMetrics() {
        var processDefinitions = repositoryService.createProcessDefinitionQuery().list();
        var incidentCounts = processDefinitions.stream().collect(toMap(ProcessDefinition::getKey, ignored -> 0L, Long::sum));
        incidentCounts.put(NOT_PROCESS_INCIDENT, 0L);
        var processDefinitionKeys = processDefinitions.stream().collect(toMap(ProcessDefinition::getId, ProcessDefinition::getKey));

        runtimeService.createIncidentQuery().list().forEach(incident -> {
            var processDefinitionKey = incident.getProcessDefinitionId() == null ? NOT_PROCESS_INCIDENT : processDefinitionKeys.get(incident.getProcessDefinitionId());
            incidentCounts.merge(processDefinitionKey, 1L, Long::sum);
        });
        incidentCounts.forEach(
            (processDefinitionKey, incidents) -> this.openIncidents.labels(processDefinitionKey).set(incidents));
    }

    @Override
    public void registerMetrics(final CollectorRegistry collectorRegistry) {
        this.openIncidents = Gauge.build()
            .name("camunda_incidents_open")
            .labelNames("processDefinitionKey")
            .help("Number of open incidents by process definition key.")
            .register(collectorRegistry);
    }

}
