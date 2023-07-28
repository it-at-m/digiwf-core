package de.muenchen.oss.digiwf.camunda.prometheus;

import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Gauge;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.RuntimeService;

@RequiredArgsConstructor
public class IncidentMetricsProvider implements MetricsProvider {

    private final RuntimeService runtimeService;

    private Gauge openIncidents;

    @Override
    public void updateMetrics() {
        this.openIncidents.set(this.runtimeService.createIncidentQuery().count());
    }

    @Override
    public void registerMetrics(final CollectorRegistry collectorRegistry) {
        this.openIncidents = Gauge.build()
                .name("camunda_incidents_open")
                .help("Number of open incidents.")
                .register(collectorRegistry);
    }

}
