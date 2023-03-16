package io.muenchendigital.digiwf.task.service.application.usecase;

import io.holunda.polyflow.view.Task;
import io.muenchendigital.digiwf.task.service.application.port.in.WorkOnUserTask;
import io.muenchendigital.digiwf.task.service.application.port.out.engine.TaskCommandPort;
import io.muenchendigital.digiwf.task.service.application.port.out.polyflow.TaskNotFoundException;
import io.muenchendigital.digiwf.task.service.application.port.out.polyflow.TaskQueryPort;
import io.muenchendigital.digiwf.task.service.application.port.out.schema.TaskSchemaRefResolverPort;
import io.muenchendigital.digiwf.task.service.application.port.out.auth.CurrentUserPort;
import io.muenchendigital.digiwf.task.service.domain.TaskWithSchemaRef;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class WorkOnUserTaskUseCase implements WorkOnUserTask {

  private final TaskQueryPort taskQueryPort;
  private final CurrentUserPort currentUserPort;
  private final TaskSchemaRefResolverPort taskSchemaRefResolverPort;
  private final TaskCommandPort taskCommandPort;

  @Override
  public TaskWithSchemaRef loadUserTask(String taskId) throws TaskNotFoundException {
    final Task task = getTaskForUser(taskId);
    return new TaskWithSchemaRef(task, taskSchemaRefResolverPort.apply(task));
  }

  @Override
  public void completeUserTask(String taskId, Map<String, Object> payload) throws TaskNotFoundException {
    var task = getTaskForUser(taskId);
    final String schemaRef = taskSchemaRefResolverPort.apply(task);
    // TODO: validate payload
    taskCommandPort.completeTask(taskId, payload);
  }

  @Override
  public void saveUserTask(String taskId, Map<String, Object> payload) throws TaskNotFoundException {
    var task = getTaskForUser(taskId);
    final String schemaRef = taskSchemaRefResolverPort.apply(task);
    // TODO: validate payload
    taskCommandPort.saveUserTask(taskId, payload);
  }

  @Override
  public void assignUserTask(String taskId, String assignee) throws TaskNotFoundException {
    var task = getTaskForUser(taskId);
    if (!assignee.equals(task.getAssignee())) {
      taskCommandPort.assignUserTask(taskId, assignee);
    }
  }

  @Override
  public void unassignUserTask(String taskId) throws TaskNotFoundException {
    var task = getTaskForUser(taskId);
    if (task.getAssignee() != null) {
      taskCommandPort.unassignUserTask(taskId);
    }
  }

  @Override
  public void deferUserTask(String taskId, OffsetDateTime followUpDate) throws TaskNotFoundException {
    var task = getTaskForUser(taskId);
    taskCommandPort.deferUserTask(taskId, followUpDate.toInstant());
  }

  @Override
  public void undeferUserTask(String taskId) throws TaskNotFoundException {
    var task = getTaskForUser(taskId);
    if (task.getFollowUpDate() != null) {
      taskCommandPort.undeferUserTask(taskId);
    }
  }

  private Task getTaskForUser(String taskId) {
    var currentUser = currentUserPort.getCurrentUser();
    return taskQueryPort.getTaskByIdForCurrentUser(currentUser, taskId);
  }
}
