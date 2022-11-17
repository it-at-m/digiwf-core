package io.muenchendigital.digiwf.camunda.prometheus;

import io.prometheus.client.CollectorRegistry;

public interface MetricsProvider {

    void updateMetrics();

    void registerMetrics(CollectorRegistry collectorRegistry);

}
