package io.muenchendigital.digiwf.email.integration.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "io.muenchendigital.digiwf.mail.metrics")
public class MetricsProperties {

    /**
     * Total mail counter.
     */
    private String totalMailCounterName;

    /**
     * Failure counter.
     */
    private String failureCounterName;
}
