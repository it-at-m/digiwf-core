package de.muenchen.oss.digiwf.task.service.adapter.out.schema;

import de.muenchen.oss.digiwf.task.TaskSchemaType;
import de.muenchen.oss.digiwf.task.TaskVariables;
import de.muenchen.oss.digiwf.task.service.application.port.out.schema.TaskSchemaTypeResolverPort;
import io.holunda.polyflow.view.Task;
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
