package io.muenchendigital.digiwf.task.service.adapter.out.cancellation;

import io.holunda.polyflow.view.Task;
import io.muenchendigital.digiwf.task.service.application.port.out.cancellation.CancellationFlagOutPort;
import org.springframework.stereotype.Component;

import static io.holunda.camunda.bpm.data.CamundaBpmData.reader;
import static io.muenchendigital.digiwf.task.TaskVariables.TASK_CANCELABLE;

/**
 * Checks if the task is cancellable based on task variable.
 */
@Component
public class VariableTaskCancellationFlagAdapter implements CancellationFlagOutPort {

  @Override
  public Boolean apply(Task task) {
    return reader(task.getPayload()).getOrDefault(TASK_CANCELABLE, false);
  }
}
