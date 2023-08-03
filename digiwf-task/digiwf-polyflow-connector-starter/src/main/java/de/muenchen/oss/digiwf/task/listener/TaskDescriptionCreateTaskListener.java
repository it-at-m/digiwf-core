package de.muenchen.oss.digiwf.task.listener;

import de.muenchen.oss.digiwf.task.TaskVariables;
import io.holunda.polyflow.taskpool.collector.task.TaskEventCollectorService;
import lombok.val;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import static io.holunda.camunda.bpm.data.CamundaBpmData.reader;
import static io.holunda.camunda.bpm.data.CamundaBpmData.writer;


@Component
public class TaskDescriptionCreateTaskListener {

    @Order(TaskEventCollectorService.ORDER - 1000) // be before polyflow
    @EventListener(condition = "#task.eventName.equals('create')")
    public void taskCreated(final DelegateTask task) {

        val executionReader = reader(task.getExecution());
        val taskReader = reader(task);

        val description = executionReader.getLocalOptional(TaskVariables.TASK_DESCRIPTION)
            .orElseGet(() -> executionReader.getLocalOptional(TaskVariables.TASK_DESCRIPTION_LEGACY)
                .orElseGet(() -> taskReader.getLocalOptional(TaskVariables.TASK_DESCRIPTION)
                    .orElseGet(() -> taskReader.getLocalOrDefault(TaskVariables.TASK_DESCRIPTION_LEGACY, null))));
        if (description != null) {
            task.setDescription(description);
        }
    }
}
