package de.muenchen.oss.digiwf.camunda.prometheus;

import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Counter;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.springframework.context.event.EventListener;

/**
 * Reports task events.
 * @see TaskListener
 */
@RequiredArgsConstructor
public class TaskEventReporter implements MetricsReporter {

    private static final String PROCESS_DEFINITION_KEY = "processDefinitionKey";
    private static final String TASK_DEFINITION_KEY = "taskDefinitionKey";

    private final RepositoryService repositoryService;
    private Counter createTaskEvents;
    private Counter completeTaskEvents;
    private Counter assignTaskEvents;
    private Counter deleteTaskEvents;
    private Counter updateTaskEvents;
    private Counter timeoutTaskEvents;

    @Override
    public void registerMetrics(CollectorRegistry collectorRegistry) {
        createTaskEvents = Counter.build()
            .name("camunda_create_task_events_count")
            .help("Create task events by deployed process definition.")
            .labelNames(PROCESS_DEFINITION_KEY, TASK_DEFINITION_KEY)
            .register(collectorRegistry);
        completeTaskEvents = Counter.build()
            .name("camunda_complete_task_events_count")
            .help("Complete task events by deployed process definition.")
            .labelNames(PROCESS_DEFINITION_KEY, TASK_DEFINITION_KEY)
            .register(collectorRegistry);
        assignTaskEvents = Counter.build()
            .name("camunda_assign_task_events_count")
            .help("Assign and un-assign task events by deployed process definition.")
            .labelNames(PROCESS_DEFINITION_KEY, TASK_DEFINITION_KEY)
            .register(collectorRegistry);
        deleteTaskEvents = Counter.build()
            .name("camunda_delete_task_events_count")
            .help("Delete task events by deployed process definition.")
            .labelNames(PROCESS_DEFINITION_KEY, TASK_DEFINITION_KEY)
            .register(collectorRegistry);
        updateTaskEvents = Counter.build()
            .name("camunda_update_task_events_count")
            .help("Update task events by deployed process definition.")
            .labelNames(PROCESS_DEFINITION_KEY, TASK_DEFINITION_KEY)
            .register(collectorRegistry);
        timeoutTaskEvents = Counter.build()
            .name("camunda_timeout_task_events_count")
            .help("Timout task events by deployed process definition.")
            .labelNames(PROCESS_DEFINITION_KEY, TASK_DEFINITION_KEY)
            .register(collectorRegistry);
    }

    @EventListener(condition = "#task.eventName.equals('create')")
    public void countCreateEvent(DelegateTask task) {
        createTaskEvents.labels(getProcessDefinitionKey(task), task.getTaskDefinitionKey()).inc();
    }

    @EventListener(condition = "#task.eventName.equals('complete')")
    public void countCompleteEvent(DelegateTask task) {
        completeTaskEvents.labels(getProcessDefinitionKey(task), task.getTaskDefinitionKey()).inc();
    }

    @EventListener(condition = "#task.eventName.equals('assignment')")
    public void countAssignmentEvent(DelegateTask task) {
        assignTaskEvents.labels(getProcessDefinitionKey(task), task.getTaskDefinitionKey()).inc();
    }

    @EventListener(condition = "#task.eventName.equals('delete')")
    public void countDeleteEvent(DelegateTask task) {
        deleteTaskEvents.labels(getProcessDefinitionKey(task), task.getTaskDefinitionKey()).inc();
    }

    @EventListener(condition = "#task.eventName.equals('timeout')")
    public void countTimeoutEvent(DelegateTask task) {
        timeoutTaskEvents.labels(getProcessDefinitionKey(task), task.getTaskDefinitionKey()).inc();
    }
    @EventListener(condition = "#task.eventName.equals('update')")
    public void countUpdateEvent(DelegateTask task) {
        updateTaskEvents.labels(getProcessDefinitionKey(task), task.getTaskDefinitionKey()).inc();
    }

    private String getProcessDefinitionKey(DelegateTask task) {
        return repositoryService.getProcessDefinition(task.getProcessDefinitionId()).getKey();
    }

}
