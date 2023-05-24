package io.muenchendigital.digiwf.task.listener;

import io.holunda.polyflow.taskpool.collector.task.TaskEventCollectorService;
import io.muenchendigital.digiwf.task.TaskManagementProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import static io.holunda.camunda.bpm.data.CamundaBpmData.writer;
import static io.muenchendigital.digiwf.task.TaskVariables.TASK_ASSIGNEE;

@Component
@Slf4j
@RequiredArgsConstructor
public class AssignmentAssignTaskListener {
    private final TaskManagementProperties.AssignmentProperties properties;

    @Order(TaskEventCollectorService.ORDER - 1000) // be before polyflow
    @EventListener(condition = "#task.eventName.equals('assignment')")
    public void taskAssigned(final DelegateTask task) {

        if (properties.isShadow()) {
            val assignee = task.getAssignee();
            val writer = writer(task);
            if (properties.isLocal()) {
                log.debug("Shadowing assignment information for task {} in local variable: {}", task.getId(), assignee);
                writer.setLocal(TASK_ASSIGNEE, assignee);
            } else {
                log.debug("Shadowing assignment information for task {} in global variable: {}", task.getId(), assignee);
                writer.set(TASK_ASSIGNEE, assignee);
            }
            if (properties.isDelete()) {
                log.debug("Deleting assignment information from task attributes {}", task.getId());
                task.setAssignee(null);
            }
        }
    }
}
