package io.muenchendigital.digiwf.camunda.prometheus;

import io.prometheus.client.CollectorRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.PostConstruct;
import java.util.List;

@EnableScheduling
@Import(MetricsConfiguration.class)
@RequiredArgsConstructor
public class CamundaPrometheusAutoConfiguration {

    private final List<MetricsProvider> metricsProviders;
    private final CollectorRegistry collectorRegistry;

    @Scheduled(fixedDelayString = "${io.muenchendigital.camunda.prometheus.update-interval}")
    public void updateMetrics() {
        metricsProviders.forEach(MetricsProvider::updateMetrics);
    }

    @PostConstruct
    public void initalizeMetrics() {
        metricsProviders.forEach(provider -> provider.registerMetrics(collectorRegistry));
    }

}