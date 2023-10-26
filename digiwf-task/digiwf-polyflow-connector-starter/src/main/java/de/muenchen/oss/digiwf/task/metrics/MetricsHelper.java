package de.muenchen.oss.digiwf.task.metrics;

/**
 * Helper for metrics.
 */
public class MetricsHelper {

    public static final String POLYFLOW_AXON_KAFKA_EVENTS_SENT = "polyflow_axon_kafka_events_sent";
    public static final String POLYFLOW_COMMANDS_SENT = "polyflow_commands_sent";

    /**
     * Tag names for metrics.
     */
    public enum TagNames {
        ;
        public static final String MESSAGE_STATUS = "status";
        public static final String PROCESS_ENGINE = "processEngine";
        public static final String EXCEPTION_TYPE = "exceptionType";
    }
}
