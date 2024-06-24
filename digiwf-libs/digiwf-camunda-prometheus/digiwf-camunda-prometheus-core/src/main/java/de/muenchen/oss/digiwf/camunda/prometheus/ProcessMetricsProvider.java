package de.muenchen.oss.digiwf.camunda.prometheus;

import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Gauge;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.repository.ProcessDefinition;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

@RequiredArgsConstructor
public class ProcessMetricsProvider implements MetricsProvider {

    private final RuntimeService runtimeService;
    private final RepositoryService repositoryService;

    private Gauge processDefinitionCount;
    private Gauge processInstanceCount;

    @Override
    public void updateMetrics() {
        repositoryService.createProcessDefinitionQuery().list().stream()
                .collect(groupingBy(ProcessDefinition::getKey, counting())).forEach((key, count) -> {
                    processDefinitionCount.labels(key).set(count);
                    processInstanceCount.labels(key).set(runtimeService.createProcessInstanceQuery().processDefinitionKeyIn(key).count());
                });
    }

    @Override
    public void registerMetrics(final CollectorRegistry collectorRegistry) {
        this.processDefinitionCount = Gauge.build()
                .name("camunda_deployed_process_definitions")
                .help("Number of deployed process definitions.")
                .labelNames("processDefinitionKey")
                .register(collectorRegistry);

        this.processInstanceCount = Gauge.build()
                .name("camunda_running_process_instances")
                .help("Running process instances by process definition key.")
                .labelNames("processDefinitionKey")
                .register(collectorRegistry);
    }
}
