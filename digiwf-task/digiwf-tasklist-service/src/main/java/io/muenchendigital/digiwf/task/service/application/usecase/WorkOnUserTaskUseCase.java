package io.muenchendigital.digiwf.task.service.application.usecase;

import io.muenchendigital.digiwf.task.service.application.port.in.WorkOnUserTask;
import io.muenchendigital.digiwf.task.service.application.port.out.polyflow.TaskQueryPort;
import io.muenchendigital.digiwf.task.service.application.port.out.schema.TaskSchemaRefResolverPort;
import io.muenchendigital.digiwf.task.service.application.port.out.auth.CurrentUserPort;
import io.muenchendigital.digiwf.task.service.domain.TaskWithSchema;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WorkOnUserTaskUseCase implements WorkOnUserTask {

  private final TaskQueryPort taskQueryPort;
  private final CurrentUserPort currentUserPort;
  private final TaskSchemaRefResolverPort taskSchemaRefResolverPort;

  @Override
  public TaskWithSchema loadUserTask(String taskId) {
    var currentUser = currentUserPort.getCurrentUser();
    var task = taskQueryPort.getTaskByIdForCurrentUser(currentUser, taskId);
    return new TaskWithSchema(task, taskSchemaRefResolverPort.apply(task));
  }
}
