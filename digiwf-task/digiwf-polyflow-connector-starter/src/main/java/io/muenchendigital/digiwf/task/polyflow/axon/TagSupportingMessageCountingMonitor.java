package io.muenchendigital.digiwf.task.polyflow.axon;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Tags;
import org.axonframework.messaging.Message;
import org.axonframework.monitoring.MessageMonitor;
import org.jetbrains.annotations.NotNull;
import static io.muenchendigital.digiwf.task.metrics.MetricsHelper.TagNames.MESSAGE_STATUS;

/**
 * Counts the number of ingested, successful, failed and processed messages
 * Copied from {@link org.axonframework.micrometer.MessageCountingMonitor} and augmented with the ability to specify tags.
 */
public final class TagSupportingMessageCountingMonitor implements MessageMonitor<Message<?>> {

    private final Counter ingestedCounter;
    private final Counter successCounter;
    private final Counter failureCounter;
    private final Counter processedCounter;
    private final Counter ignoredCounter;

    private TagSupportingMessageCountingMonitor(
            Counter ingestedCounter, Counter successCounter, Counter failureCounter,
            Counter processedCounter, Counter ignoredCounter) {
        this.ingestedCounter = ingestedCounter;
        this.successCounter = successCounter;
        this.failureCounter = failureCounter;
        this.processedCounter = processedCounter;
        this.ignoredCounter = ignoredCounter;
    }

    /**
     * Creates a message counting monitor
     *
     * @param meterName     The meter name that will be created in the given meterRegistry
     * @param tags          The tags to add to the meters
     * @param meterRegistry The meter registry used to create and register the meters
     * @return the message counting monitor
     */
    public static TagSupportingMessageCountingMonitor buildMonitor(String meterName, Iterable<Tag> tags, MeterRegistry meterRegistry) {

        Counter ingestedCounter = meterRegistry.counter(meterName, Tags.concat(tags, MESSAGE_STATUS, "ingested"));
        Counter successCounter = meterRegistry.counter(meterName, Tags.concat(tags, MESSAGE_STATUS, "success"));
        Counter failureCounter = meterRegistry.counter(meterName, Tags.concat(tags, MESSAGE_STATUS, "failure"));
        Counter processedCounter = meterRegistry.counter(meterName, Tags.concat(tags, MESSAGE_STATUS, "processed"));
        Counter ignoredCounter = meterRegistry.counter(meterName, Tags.concat(tags, MESSAGE_STATUS, "ignored"));

        return new TagSupportingMessageCountingMonitor(
                ingestedCounter,
                successCounter,
                failureCounter,
                processedCounter,
                ignoredCounter);
    }

    @Override
    public MonitorCallback onMessageIngested(@NotNull Message<?> message) {
        ingestedCounter.increment();
        //noinspection AnonymousInnerClass,AnonymousInnerClassWithTooManyMethods,ReturnOfInnerClass
        return new MonitorCallback() {
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