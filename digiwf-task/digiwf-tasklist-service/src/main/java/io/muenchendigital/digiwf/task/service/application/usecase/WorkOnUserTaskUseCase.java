package io.muenchendigital.digiwf.task.service.application.usecase;

import io.muenchendigital.digiwf.task.service.adapter.out.engine.TaskCommandClient;
import io.muenchendigital.digiwf.task.service.application.port.in.WorkOnUserTask;
import io.muenchendigital.digiwf.task.service.application.port.out.engine.TaskCommandPort;
import io.muenchendigital.digiwf.task.service.application.port.out.polyflow.TaskNotFoundException;
import io.muenchendigital.digiwf.task.service.application.port.out.polyflow.TaskQueryPort;
import io.muenchendigital.digiwf.task.service.application.port.out.schema.TaskSchemaRefResolverPort;
import io.muenchendigital.digiwf.task.service.application.port.out.auth.CurrentUserPort;
import io.muenchendigital.digiwf.task.service.domain.TaskWithSchema;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class WorkOnUserTaskUseCase implements WorkOnUserTask {

  private final TaskQueryPort taskQueryPort;
  private final CurrentUserPort currentUserPort;
  private final TaskSchemaRefResolverPort taskSchemaRefResolverPort;
  private final TaskCommandPort taskCommandPort;

  @Override
  public TaskWithSchema loadUserTask(String taskId) throws TaskNotFoundException {
    var currentUser = currentUserPort.getCurrentUser();
    var task = taskQueryPort.getTaskByIdForCurrentUser(currentUser, taskId);
    return new TaskWithSchema(task, taskSchemaRefResolverPort.apply(task));
  }

  @Override
  public void completeUserTask(String taskId, Map<String, Object> payload) throws TaskNotFoundException {
    taskCommandPort.completeTask(taskId, payload);
  }
}
