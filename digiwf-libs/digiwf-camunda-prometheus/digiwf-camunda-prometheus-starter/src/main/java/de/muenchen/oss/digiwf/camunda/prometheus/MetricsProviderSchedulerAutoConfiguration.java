package de.muenchen.oss.digiwf.camunda.prometheus;

import io.prometheus.client.CollectorRegistry;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Map;

@EnableScheduling
@Configuration
@AutoConfigureAfter(MetricsAutoConfiguration.class)
@RequiredArgsConstructor
@Slf4j
public class MetricsProviderSchedulerAutoConfiguration {

    private final Map<String, MetricsProvider> metricsProviders;
    private final CollectorRegistry collectorRegistry;

    @PostConstruct
    public void initializeProviderMetrics() {
        // providers
        log.info("[DIGIWF METRICS]: Registered {} metrics providers: {}", metricsProviders.keySet().size(), String.join(", ", metricsProviders.keySet()));
        metricsProviders.forEach((key, value) -> value.registerMetrics(collectorRegistry));
    }

    @Scheduled(fixedDelayString = "${digiwf.prometheus.process-engine.update-interval}")
    public void updateProviderMetrics() {
        metricsProviders.forEach((key, value) -> value.updateMetrics());
    }

}
