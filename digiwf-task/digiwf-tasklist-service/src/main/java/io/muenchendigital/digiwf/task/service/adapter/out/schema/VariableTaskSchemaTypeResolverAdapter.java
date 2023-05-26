package io.muenchendigital.digiwf.task.service.adapter.out.schema;

import io.holunda.polyflow.view.Task;
import io.muenchendigital.digiwf.task.TaskSchemaType;
import io.muenchendigital.digiwf.task.TaskVariables;
import io.muenchendigital.digiwf.task.service.application.port.out.schema.TaskSchemaTypeResolverPort;
import org.springframework.stereotype.Component;

@Component
public class VariableTaskSchemaTypeResolverAdapter implements TaskSchemaTypeResolverPort {
  @Override
  public TaskSchemaType apply(Task task) {
    Object value = task.getPayload().getOrDefault(TaskVariables.TASK_SCHEMA_TYPE.getName(), null);
    if (value instanceof String) {
      return TaskSchemaType.valueOf((String) value);
    } else if (value instanceof TaskSchemaType){
      return (TaskSchemaType) value;
    } else {
      throw new IllegalStateException("Unknown value for task schema type " + value);
    }
    // FIXME camunda BPM Data
    //return reader(task.getPayload()).getOrDefault(TaskVariables.TASK_SCHEMA_TYPE, null);
  }
}
