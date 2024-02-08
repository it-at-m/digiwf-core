package de.muenchen.oss.digiwf.camunda.prometheus;

import io.prometheus.client.CollectorRegistry;

/**
 * Metric reporter is an active component, pushing metrics without explicit trigger.
 */
public interface MetricsReporter {
    /**
     * Registers collector registry.
     * @param collectorRegistry registry.
     */
    void registerMetrics(CollectorRegistry collectorRegistry);
}
