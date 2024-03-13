package de.muenchen.oss.digiwf.camunda.prometheus;

import io.prometheus.client.CollectorRegistry;

/**
 * Metrics provider delivers metrics on external trigger.
 */
public interface MetricsProvider {

    /**
     * Triggers metrics update.
     */
    void updateMetrics();

    /**
     * Registers collector registry.
     * @param collectorRegistry registry.
     */
    void registerMetrics(CollectorRegistry collectorRegistry);

}
