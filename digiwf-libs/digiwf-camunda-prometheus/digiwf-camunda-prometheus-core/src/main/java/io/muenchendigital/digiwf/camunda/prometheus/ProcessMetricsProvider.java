package io.muenchendigital.digiwf.camunda.prometheus;

import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Gauge;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class ProcessMetricsProvider implements MetricsProvider {

    private final RuntimeService runtimeService;
    private final RepositoryService repositoryService;

    private Gauge processDefinitionCount;
    private Gauge processDefinitionCountUnique;
    private Gauge processInstanceCount;

    @Override
    public void updateMetrics() {
        final List<String> processDefinitionKeys = new ArrayList<>();
        this.repositoryService.createProcessDefinitionQuery().list().forEach(processDefinition -> processDefinitionKeys.add(processDefinition.getKey()));
        this.processDefinitionCount.set(processDefinitionKeys.size());
        this.processDefinitionCountUnique.set(processDefinitionKeys.stream().distinct().count());

        if (processDefinitionKeys.size() > 0) {
            processDefinitionKeys.forEach(processDefinitionKey -> this.processInstanceCount.labels(processDefinitionKey)
                    .set(this.runtimeService.createProcessInstanceQuery()
                            .processDefinitionKey(processDefinitionKey)
                            .count()
                    )
            );
        } else {
            this.processInstanceCount.labels("NA").set(0);
        }
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
                .labelNames("processDefinitionKey")
                .register(collectorRegistry);
    }
}
