package de.muenchen.oss.digiwf.task.service.infra.metrics;

import io.holunda.camunda.taskpool.api.business.DataEntryCreatedEvent;
import io.holunda.camunda.taskpool.api.business.DataEntryUpdatedEvent;
import io.holunda.camunda.taskpool.api.task.TaskIdentity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.messaging.Message;
import org.axonframework.monitoring.MessageMonitor;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

@RequiredArgsConstructor
@Slf4j
public class ApplicationNameCompositeMessageMonitorWrapper<T extends MessageMonitor<Message<?>>> implements MessageMonitor<Message<?>> {

    private final Function<String, T> monitorSupplier;

    private final Map<String, T> applicationNameMonitors = new ConcurrentHashMap<>();

    @Override
    public MonitorCallback onMessageIngested(@NotNull Message<?> message) {
        var applicationName = determineApplicationName(message);
        var messageMonitorForApplicationName = applicationNameMonitors.computeIfAbsent(applicationName, monitorSupplier);
        return messageMonitorForApplicationName.onMessageIngested(message);
    }

    private static String determineApplicationName(Message<?> message) {
        if (TaskIdentity.class.isAssignableFrom(message.getPayloadType())) {
            return ((TaskIdentity) message.getPayload()).getSourceReference().getApplicationName();
        }
        if (DataEntryCreatedEvent.class.isAssignableFrom(message.getPayloadType())) {
            return ((DataEntryCreatedEvent) message.getPayload()).getApplicationName();
        }
        if (DataEntryUpdatedEvent.class.isAssignableFrom(message.getPayloadType())) {
            return ((DataEntryUpdatedEvent) message.getPayload()).getApplicationName();
        }
        log.debug("Cannot determine application name for payload type {}", message.getPayloadType());
        return "unknown";
    }

}
