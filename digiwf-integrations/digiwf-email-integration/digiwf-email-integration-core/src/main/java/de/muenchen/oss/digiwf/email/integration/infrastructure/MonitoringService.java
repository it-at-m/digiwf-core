package de.muenchen.oss.digiwf.email.integration.infrastructure;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

public class MonitoringService {

    private final MeterRegistry meterRegistry;

    private Counter totalMailCounter;
    private Counter failureCounter;

    public MonitoringService(final MeterRegistry meterRegistry, final String totalMailCounterName, final String failureCounterName) {
        this.meterRegistry = meterRegistry;
        this.totalMailCounter = this.meterRegistry.counter(totalMailCounterName);
        this.failureCounter = this.meterRegistry.counter(failureCounterName);
    }

    public void sendMailSucceeded() {
        this.totalMailCounter.increment();
    }

    public void sendMailFailed() {
        this.failureCounter.increment();
    }

}
