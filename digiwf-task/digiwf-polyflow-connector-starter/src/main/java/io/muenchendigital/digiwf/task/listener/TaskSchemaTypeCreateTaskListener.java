package io.muenchendigital.digiwf.task.listener;

import io.holunda.polyflow.taskpool.collector.task.TaskEventCollectorService;
import io.muenchendigital.digiwf.task.TaskSchemaType;
import lombok.val;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import static io.holunda.camunda.bpm.data.CamundaBpmData.reader;
import static io.holunda.camunda.bpm.data.CamundaBpmData.writer;
import static io.muenchendigital.digiwf.task.TaskVariables.TASK_SCHEMA_TYPE;


@Component
public class TaskSchemaTypeCreateTaskListener {

  @Order(TaskEventCollectorService.ORDER - 1000) // be before polyflow
  @EventListener(condition = "#task.eventName.equals('create')")
  public void taskCreated(final DelegateTask task) {
    if (reader(task).getLocalOptional(TASK_SCHEMA_TYPE).isPresent()) {
      return;
    }

    val formKey = task.getProcessEngineServices().getFormService().getTaskFormKey(task.getProcessDefinitionId(), task.getTaskDefinitionKey());

    final TaskSchemaType taskType;
    if (StringUtils.hasText(formKey)) {
      taskType = TaskSchemaType.VUETIFY_FORM_BASE;
    } else {
      taskType = TaskSchemaType.SCHEMA_BASED;
    }

    writer(task).setLocal(TASK_SCHEMA_TYPE, taskType);
  }
}
