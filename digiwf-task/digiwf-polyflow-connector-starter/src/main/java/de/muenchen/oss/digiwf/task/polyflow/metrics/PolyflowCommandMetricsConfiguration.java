package de.muenchen.oss.digiwf.task.polyflow.metrics;

import io.holunda.polyflow.taskpool.sender.gateway.CommandListGateway;
import io.holunda.polyflow.taskpool.sender.gateway.CommandSuccessHandler;
import io.holunda.polyflow.taskpool.sender.gateway.LoggingTaskCommandErrorHandler;
import io.holunda.polyflow.taskpool.sender.gateway.LoggingTaskCommandSuccessHandler;
import io.micrometer.core.instrument.MeterRegistry;
import de.muenchen.oss.digiwf.task.metrics.MetricsHelper;
import de.muenchen.oss.digiwf.task.polyflow.axon.PossiblyRethrowingTaskCommandErrorHandler;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import static de.muenchen.oss.digiwf.task.metrics.MetricsHelper.TagNames.EXCEPTION_TYPE;
import static de.muenchen.oss.digiwf.task.metrics.MetricsHelper.TagNames.MESSAGE_STATUS;

@Configuration
public class PolyflowCommandMetricsConfiguration {
    @Bean
    @Primary
    public CommandSuccessHandler taskCommandSuccessHandler(MeterRegistry meterRegistry) {
        @SuppressWarnings("LoggerInitializedWithForeignClass")
        var loggingHandler = new LoggingTaskCommandSuccessHandler(LoggerFactory.getLogger(CommandListGateway.class));
        return (commandMessage, commandResultMessage) -> {
            loggingHandler.apply(commandMessage, commandResultMessage);
            meterRegistry.counter(MetricsHelper.POLYFLOW_AXON_KAFKA_EVENTS_SENT, MESSAGE_STATUS, "success", EXCEPTION_TYPE, "none")
                    .increment();
            return null;
        };
    }

    @Bean
    @Primary
    public PossiblyRethrowingTaskCommandErrorHandler taskCommandErrorHandler(MeterRegistry meterRegistry) {
        @SuppressWarnings("LoggerInitializedWithForeignClass")
        var loggingHandler = new LoggingTaskCommandErrorHandler(LoggerFactory.getLogger(CommandListGateway.class));
        return new PossiblyRethrowingTaskCommandErrorHandler((commandMessage, commandResultMessage) -> {
            loggingHandler.apply(commandMessage, commandResultMessage);
            String exceptionType = commandResultMessage.exceptionResult() != null ? commandResultMessage.exceptionResult().getClass().getName() : "unknown";
            meterRegistry.counter(MetricsHelper.POLYFLOW_AXON_KAFKA_EVENTS_SENT, MESSAGE_STATUS, "error", EXCEPTION_TYPE, exceptionType).increment();
            return null;
        });
    }
}
