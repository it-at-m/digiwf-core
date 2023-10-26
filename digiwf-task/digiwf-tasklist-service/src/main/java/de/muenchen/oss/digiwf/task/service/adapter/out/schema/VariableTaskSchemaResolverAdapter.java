package de.muenchen.oss.digiwf.task.service.adapter.out.schema;

import de.muenchen.oss.digiwf.task.TaskVariables;
import de.muenchen.oss.digiwf.task.service.application.port.out.schema.TaskSchemaRefResolverPort;
import io.holunda.polyflow.view.Task;
import org.springframework.stereotype.Component;

import static io.holunda.camunda.bpm.data.CamundaBpmData.reader;

/**
 * Resolves task schema reference based on a task variable.
 * Returns null if the variable is not found.
 */
@Component
public class VariableTaskSchemaResolverAdapter implements TaskSchemaRefResolverPort {
  @Override
  public String apply(Task task) {
    return reader(task.getPayload()).getOrDefault(TaskVariables.TASK_SCHEMA_KEY, null);
  }
}
