package de.muenchen.oss.digiwf.camunda.prometheus;

import io.prometheus.client.CollectorRegistry;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@EnableScheduling
@RequiredArgsConstructor
@Configuration
public class CamundaPrometheusAutoConfiguration {

    private final List<MetricsProvider> metricsProviders;
    private final List<MetricsReporter> metricsReporters;
    private final CollectorRegistry collectorRegistry;

    @Scheduled(fixedDelayString = "${io.muenchendigital.camunda.prometheus.update-interval}")
    public void updateMetrics() {
        metricsProviders.forEach(MetricsProvider::updateMetrics);
    }

    @PostConstruct
    public void initializeMetrics() {
        // providers
        metricsProviders.forEach(provider -> provider.registerMetrics(collectorRegistry));

        // reporters
        metricsReporters.forEach(provider -> provider.registerMetrics(collectorRegistry));
    }



}
