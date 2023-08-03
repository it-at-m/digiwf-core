package de.muenchen.oss.digiwf.task.listener;

import de.muenchen.oss.digiwf.task.TaskSchemaType;
import de.muenchen.oss.digiwf.task.TaskVariables;
import io.holunda.polyflow.taskpool.collector.task.TaskEventCollectorService;
import lombok.val;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import static io.holunda.camunda.bpm.data.CamundaBpmData.reader;
import static io.holunda.camunda.bpm.data.CamundaBpmData.writer;


@Component
public class TaskSchemaTypeCreateTaskListener {

    @Order(TaskEventCollectorService.ORDER - 1000) // be before polyflow
    @EventListener(condition = "#task.eventName.equals('create')")
    public void taskCreated(final DelegateTask task) {
        if (reader(task).getLocalOptional(TaskVariables.TASK_SCHEMA_TYPE).isPresent()) {
            return;
        }

        val formKey = task.getProcessEngineServices().getFormService().getTaskFormKey(task.getProcessDefinitionId(), task.getTaskDefinitionKey());
        val writer = writer(task);
        if (StringUtils.hasText(formKey)) {
            writer
                .setLocal(TaskVariables.TASK_SCHEMA_TYPE, TaskSchemaType.VUETIFY_FORM_BASE)
                .setLocal(TaskVariables.TASK_SCHEMA_KEY, formKey);
        } else {
            writer.setLocal(TaskVariables.TASK_SCHEMA_TYPE, TaskSchemaType.SCHEMA_BASED);
        }
    }
}
