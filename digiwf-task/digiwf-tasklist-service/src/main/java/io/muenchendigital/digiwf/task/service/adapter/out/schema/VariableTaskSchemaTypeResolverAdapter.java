package io.muenchendigital.digiwf.task.service.adapter.out.schema;

import io.holunda.polyflow.view.Task;
import io.muenchendigital.digiwf.task.TaskSchemaType;
import io.muenchendigital.digiwf.task.TaskVariables;
import io.muenchendigital.digiwf.task.service.application.port.out.schema.TaskSchemaTypeResolverPort;
import org.springframework.stereotype.Component;

import static io.holunda.camunda.bpm.data.CamundaBpmData.reader;

@Component
public class VariableTaskSchemaTypeResolverAdapter implements TaskSchemaTypeResolverPort {
  @Override
  public TaskSchemaType apply(Task task) {
    return reader(task.getPayload()).getOrDefault(TaskVariables.TASK_SCHEMA_TYPE, null);
  }
}
