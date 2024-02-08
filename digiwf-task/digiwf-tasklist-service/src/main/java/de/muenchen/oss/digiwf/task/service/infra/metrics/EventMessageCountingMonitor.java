package de.muenchen.oss.digiwf.task.service.infra.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.axonframework.messaging.Message;
import org.axonframework.monitoring.MessageMonitor;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public class EventMessageCountingMonitor implements MessageMonitor<Message<?>> {

    private final MeterRegistry meterRegistry;
    private final String meterName;

    private Counter ingestedCounter;
    private Counter successCounter;
    private Counter failureCounter;
    private Counter processedCounter;
    private Counter ignoredCounter;

    @PostConstruct
    public void setupMonitor() {
        ingestedCounter = meterRegistry.counter(meterName, "status", "ingested");
        successCounter = meterRegistry.counter(meterName, "status", "success");
        failureCounter = meterRegistry.counter(meterName, "status", "failure");
        processedCounter = meterRegistry.counter(meterName, "status", "processed");
        ignoredCounter = meterRegistry.counter(meterName, "status", "ignored");
    }

    @Override
    public MonitorCallback onMessageIngested(@NotNull Message<?> message) {
        ingestedCounter.increment();
        return new MessageMonitor.MonitorCallback() {
            @Override
            public void reportSuccess() {
                processedCounter.increment();
                successCounter.increment();
            }

            @Override
            public void reportFailure(Throwable cause) {
                processedCounter.increment();
                failureCounter.increment();
            }

            @Override
            public void reportIgnored() {
                ignoredCounter.increment();
            }
        };
    }
}
