package de.muenchen.oss.digiwf.camunda.prometheus;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "digiwf.prometheus.process-engine")
public class CamundaPrometheusProperties {
    /**
     * Update interval for running metrics providers, which actively execute queries.
     */
    private final int updateInterval = 30000;
    /**
     * Flags controlling metrics.
     */
    private final Providers providers = new Providers();

    public static class Providers {
        /**
         * Flag to activate incident metric.
         */
        private boolean incident;
        /**
         * Flag to activate job metric.
         */
        private boolean job;
        /**
         * Flag to activate process metric.
         */
        private boolean process;
        /**
         * Flag to activate task metric.
         */
        private boolean task;
    }
}
