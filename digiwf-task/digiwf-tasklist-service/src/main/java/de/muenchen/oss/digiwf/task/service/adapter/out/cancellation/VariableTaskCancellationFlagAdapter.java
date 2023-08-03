package de.muenchen.oss.digiwf.task.service.adapter.out.cancellation;

import de.muenchen.oss.digiwf.task.TaskVariables;
import de.muenchen.oss.digiwf.task.service.application.port.out.cancellation.CancellationFlagOutPort;
import io.holunda.polyflow.view.Task;
import org.springframework.stereotype.Component;

import static io.holunda.camunda.bpm.data.CamundaBpmData.reader;

/**
 * Checks if the task is cancellable based on task variable.
 */
@Component
public class VariableTaskCancellationFlagAdapter implements CancellationFlagOutPort {

  @Override
  public Boolean apply(Task task) {
    return reader(task.getPayload()).getOrDefault(TaskVariables.TASK_CANCELABLE, false);
  }
}
