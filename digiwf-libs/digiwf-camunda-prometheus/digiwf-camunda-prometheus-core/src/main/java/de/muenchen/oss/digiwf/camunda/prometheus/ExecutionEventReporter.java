package de.muenchen.oss.digiwf.camunda.prometheus;

import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Counter;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.springframework.context.event.EventListener;

@RequiredArgsConstructor
public class ExecutionEventReporter implements MetricsReporter {

    private final RepositoryService repositoryService;
    private Counter startExecutionEvents;
    private Counter endExecutionEvents;

    @Override
    public void registerMetrics(CollectorRegistry collectorRegistry) {
        this.startExecutionEvents = Counter.build()
            .name("camunda_start_process_events_count")
            .help("Start process events by process definition.")
            .labelNames("processDefinitionKey")
            .register(collectorRegistry);
        this.endExecutionEvents = Counter.build()
            .name("camunda_end_process_events_count")
            .help("End process events by process definition.")
            .labelNames("processDefinitionKey")
            .register(collectorRegistry);
    }

    @EventListener(condition = "#execution.eventName.equals('start')")
    public void countStartEvent(DelegateExecution execution) {
        if (isModelElementOfType("startEvent", execution)) {
            startExecutionEvents.labels(getProcessDefinitionKey(execution)).inc();
        }
    }

    @EventListener(condition = "#execution.eventName.equals('end')")
    public void countEndEvent(DelegateExecution execution) {
        if (isModelElementOfType("endEvent", execution)) {
            endExecutionEvents.labels(getProcessDefinitionKey(execution)).inc();
        }
    }

    private static boolean isModelElementOfType(String typeName, DelegateExecution execution) {
        return execution.getBpmnModelElementInstance() != null
            && typeName.equals(execution.getBpmnModelElementInstance().getElementType().getTypeName());
    }

    private String getProcessDefinitionKey(DelegateExecution execution) {
        return repositoryService.getProcessDefinition(execution.getProcessDefinitionId()).getKey();
    }

}
