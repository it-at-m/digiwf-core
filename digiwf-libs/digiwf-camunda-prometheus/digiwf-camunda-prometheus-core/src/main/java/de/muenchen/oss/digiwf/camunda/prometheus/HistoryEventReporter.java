package de.muenchen.oss.digiwf.camunda.prometheus;

import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Summary;
import org.camunda.bpm.engine.impl.history.event.HistoricActivityInstanceEventEntity;
import org.springframework.context.event.EventListener;

public class HistoryEventReporter implements MetricsReporter {

    private Summary camundaActivityTime;

    @Override
    public void registerMetrics(CollectorRegistry collectorRegistry) {
        camundaActivityTime = Summary.build()
            .quantile(0.5, 0.05)
            .quantile(0.9, 0.01)
            .quantile(0.99, 0.001)
            .name("camunda_activity_execution_time_milliseconds")
            .labelNames("processDefinitionKey", "activityType", "activityName")
            .help("Duration of activities in milliseconds.")
            .register(collectorRegistry);
    }

    /**
     * Observes the duration of any ended camunda activity
     *
     * @param historyEvent the caught event
     */
    @EventListener(condition = "#historyEvent != null && #historyEvent.eventType.equals('end')")
    public void handleEvent(HistoricActivityInstanceEventEntity historyEvent) {

        String activityName = historyEvent.getActivityName();
        if (activityName == null || activityName.isEmpty()) {
            activityName = historyEvent.getActivityId();
        }

        if (historyEvent.getDurationInMillis() != null) {
            camundaActivityTime
                .labels(historyEvent.getProcessDefinitionKey(), historyEvent.getActivityType(), activityName)
                .observe(historyEvent.getDurationInMillis());
        }
    }

}
