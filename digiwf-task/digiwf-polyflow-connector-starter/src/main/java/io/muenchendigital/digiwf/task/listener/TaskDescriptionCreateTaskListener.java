package io.muenchendigital.digiwf.task.listener;

import io.holunda.polyflow.taskpool.collector.task.TaskEventCollectorService;
import lombok.val;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import static io.holunda.camunda.bpm.data.CamundaBpmData.reader;
import static io.holunda.camunda.bpm.data.CamundaBpmData.writer;
import static io.muenchendigital.digiwf.task.TaskSchemaType.SCHEMA_BASED;
import static io.muenchendigital.digiwf.task.TaskSchemaType.VUETIFY_FORM_BASE;
import static io.muenchendigital.digiwf.task.TaskVariables.*;


@Component
public class TaskDescriptionCreateTaskListener {

    @Order(TaskEventCollectorService.ORDER - 1000) // be before polyflow
    @EventListener(condition = "#task.eventName.equals('create')")
    public void taskCreated(final DelegateTask task) {

        val executionReader = reader(task.getExecution());
        val taskReader = reader(task);

        val description = executionReader.getLocalOptional(TASK_DESCRIPTION)
            .orElseGet(() -> executionReader.getLocalOptional(TASK_DESCRIPTION_LEGACY)
                .orElseGet(() -> taskReader.getLocalOptional(TASK_DESCRIPTION)
                    .orElseGet(() -> taskReader.getLocalOrDefault(TASK_DESCRIPTION_LEGACY, null))));
        if (description != null) {
            task.setDescription(description);
        }
    }
}
