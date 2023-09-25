package de.muenchen.oss.digiwf.camunda.prometheus;

import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Gauge;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.ManagementService;

@RequiredArgsConstructor
public class FniAndEdeMetricsProvider implements MetricsProvider {

    private final ManagementService managementService;

    private Gauge fniCount;
    private Gauge edeCount;

    @Override
    public void updateMetrics() {
        // Get total number of FNIs
        this.fniCount.labels("total").set(this.managementService.createMetricsQuery().name("activity-instance-start").sum());

        // Get total number of EDEs
        this.edeCount.set(this.managementService.createMetricsQuery().name("executed-decision-elements").sum());
    }

    @Override
    public void registerMetrics(final CollectorRegistry collectorRegistry) {
        this.fniCount = Gauge.build()
                .name("camunda_activity_instances")
                .help("Number of activity instances (BPMN FNI) in total and by deployed process definition.")
                .labelNames("processDefinitionId")
                .register(collectorRegistry);

        this.edeCount = Gauge.build()
                .name("camunda_executed_decision_instances")
                .help("Total number of executed decision instances (DMN EDE).")
                .register(collectorRegistry);
    }

}
