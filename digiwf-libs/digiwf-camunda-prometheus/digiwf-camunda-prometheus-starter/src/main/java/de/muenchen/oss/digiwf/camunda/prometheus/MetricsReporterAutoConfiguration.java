package de.muenchen.oss.digiwf.camunda.prometheus;

import io.prometheus.client.CollectorRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@AutoConfigureAfter(MetricsAutoConfiguration.class)
public class MetricsReporterAutoConfiguration {

    @Autowired
    public void configureReporters(List<MetricsReporter> metricsReporters, CollectorRegistry collectorRegistry) {
        // reporters
        metricsReporters.forEach(provider -> provider.registerMetrics(collectorRegistry));
    }
}
