package de.muenchen.oss.digiwf.camunda.prometheus;

import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Gauge;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.repository.ResourceDefinition;

@RequiredArgsConstructor
public class IncidentMetricsProvider implements MetricsProvider {

    private static final String NOT_PROCESS_INCIDENT = "__no_process_definition_key";
    private final RuntimeService runtimeService;
    private final RepositoryService repositoryService;
    private Gauge openIncidents;

    @Override
    public void updateMetrics() {
        repositoryService.createProcessDefinitionQuery().list().stream()
                .map(ResourceDefinition::getKey).forEach((key) ->
                        openIncidents.labels(key)
                                .set(runtimeService.createIncidentQuery().processDefinitionKeyIn(key).count()
                                )
                );
        openIncidents.labels(NOT_PROCESS_INCIDENT).set(runtimeService.createIncidentQuery().processDefinitionId(null).count());
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
