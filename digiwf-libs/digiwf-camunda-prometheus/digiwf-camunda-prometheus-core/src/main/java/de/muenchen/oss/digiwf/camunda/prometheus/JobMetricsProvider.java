package de.muenchen.oss.digiwf.camunda.prometheus;

import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Gauge;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.ManagementService;

import java.util.Date;

@RequiredArgsConstructor
public class JobMetricsProvider implements MetricsProvider {

    private final ManagementService managementService;

    private Gauge jobsExecutable;
    private Gauge jobsFuture;
    private Gauge jobsNoRetries;
    private Gauge jobsSuspended;

    @Override
    public void updateMetrics() {
        this.jobsExecutable.set(this.managementService.createJobQuery().executable().count());
        this.jobsFuture.set(this.managementService.createJobQuery().duedateHigherThan(new Date()).count());
        this.jobsNoRetries.set(this.managementService.createJobQuery().noRetriesLeft().count());
        this.jobsSuspended.set(this.managementService.createJobQuery().suspended().count());
    }

    @Override
    public void registerMetrics(final CollectorRegistry collectorRegistry) {
        this.jobsExecutable = Gauge.build()
                .name("camunda_jobs_executable")
                .help("Number of jobs which are executable, ie. retries > 0 and due date is null or due date is in the past")
                .register(collectorRegistry);

        this.jobsFuture = Gauge.build()
                .name("camunda_jobs_future")
                .help("Number of jobs where the due date is in the future")
                .register(collectorRegistry);

        this.jobsNoRetries = Gauge.build()
                .name("camunda_jobs_out_of_retries")
                .help("Number of jobs with no retries left")
                .register(collectorRegistry);

        this.jobsSuspended = Gauge.build()
                .name("camunda_jobs_suspended")
                .help("Number of suspended jobs")
                .register(collectorRegistry);
    }

}
