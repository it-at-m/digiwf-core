package io.muenchendigital.digiwf.task.service.application.usecase;

import io.holunda.polyflow.view.Task;
import io.muenchendigital.digiwf.task.service.application.port.in.WorkOnUserTask;
import io.muenchendigital.digiwf.task.service.application.port.out.cancellation.CancellationFlagOutPort;
import io.muenchendigital.digiwf.task.service.application.port.out.engine.TaskCommandPort;
import io.muenchendigital.digiwf.task.service.application.port.out.polyflow.TaskNotFoundException;
import io.muenchendigital.digiwf.task.service.application.port.out.polyflow.TaskQueryPort;
import io.muenchendigital.digiwf.task.service.application.port.out.schema.JsonSchemaNotFoundException;
import io.muenchendigital.digiwf.task.service.application.port.out.schema.JsonSchemaPort;
import io.muenchendigital.digiwf.task.service.application.port.out.schema.JsonSchemaValidationPort;
import io.muenchendigital.digiwf.task.service.application.port.out.schema.TaskSchemaRefResolverPort;
import io.muenchendigital.digiwf.task.service.application.port.out.auth.CurrentUserPort;
import io.muenchendigital.digiwf.task.service.domain.JsonSchema;
import io.muenchendigital.digiwf.task.service.domain.TaskWithSchema;
import io.muenchendigital.digiwf.task.service.domain.TaskWithSchemaRef;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class WorkOnUserTaskUseCase implements WorkOnUserTask {
  private final TaskQueryPort taskQueryPort;
  private final CurrentUserPort currentUserPort;
  private final TaskSchemaRefResolverPort taskSchemaRefResolverPort;
  private final JsonSchemaPort jsonSchemaPort;
  private final TaskCommandPort taskCommandPort;
  private final JsonSchemaValidationPort jsonSchemaValidationPort;
  private final CancellationFlagOutPort cancellationFlagOutPort;

  @Override
  public JsonSchema loadSchema(String schemaId) throws JsonSchemaNotFoundException {
    return jsonSchemaPort.getSchemaById(schemaId);
  }

  @Override
  public TaskWithSchemaRef loadUserTask(String taskId) throws TaskNotFoundException {
    val task = getTaskForUser(taskId);
    val schemaRef = taskSchemaRefResolverPort.apply(task);
    val cancelable = cancellationFlagOutPort.apply(task);
    return new TaskWithSchemaRef(task, schemaRef, cancelable);
  }

  @Override
  public TaskWithSchema loadUserTaskWithSchema(String taskId) throws TaskNotFoundException, JsonSchemaNotFoundException {
    val task = getTaskForUser(taskId);
    val schemaRef = taskSchemaRefResolverPort.apply(task);
    val schema = jsonSchemaPort.getSchemaById(schemaRef);
    val cancelable = cancellationFlagOutPort.apply(task);
    return new TaskWithSchema(task, schema, cancelable);
  }


  @Override
  public void completeUserTask(String taskId, Map<String, Object> payload) throws TaskNotFoundException {
    var task = getTaskForUser(taskId);
    val schemaRef = taskSchemaRefResolverPort.apply(task);
    val schema = jsonSchemaPort.getSchemaById(schemaRef);
    val variables = jsonSchemaValidationPort.validateAndSerialize(schema, task, payload);
    taskCommandPort.completeUserTask(taskId, variables);
  }

  @Override
  public void saveUserTask(String taskId, Map<String, Object> payload) throws TaskNotFoundException {
    var task = getTaskForUser(taskId);
    val schemaRef = taskSchemaRefResolverPort.apply(task);
    val schema = jsonSchemaPort.getSchemaById(schemaRef);
    val variables = jsonSchemaValidationPort.validateAndSerialize(schema, task, payload);
    taskCommandPort.saveUserTask(taskId, variables);
  }

  @Override
  public void assignUserTask(String taskId, String assignee) throws TaskNotFoundException {
    val task = getTaskForUser(taskId);
    if (!assignee.equals(task.getAssignee())) {
      taskCommandPort.assignUserTask(taskId, assignee);
    }
  }

  @Override
  public void unassignUserTask(String taskId) throws TaskNotFoundException {
    val task = getTaskForUser(taskId);
    if (task.getAssignee() != null) {
      taskCommandPort.unassignUserTask(taskId);
    }
  }

  @Override
  public void deferUserTask(String taskId, OffsetDateTime followUpDate) throws TaskNotFoundException {
    val task = getTaskForUser(taskId);
    taskCommandPort.deferUserTask(taskId, followUpDate.toInstant());
  }

  @Override
  public void undeferUserTask(String taskId) throws TaskNotFoundException {
    val task = getTaskForUser(taskId);
    if (task.getFollowUpDate() != null) {
      taskCommandPort.undeferUserTask(taskId);
    }
  }

  @Override
  public void cancelUserTask(String taskId) throws TaskNotFoundException {
    val task = getTaskForUser(taskId);
    if (cancellationFlagOutPort.apply(task)) {
      taskCommandPort.cancelUserTask(taskId);
    } else {
      throw new IllegalArgumentException("Task " + taskId + " can not be cancelled.");
    }
  }

  private Task getTaskForUser(String taskId) {
    val currentUser = currentUserPort.getCurrentUser();
    return taskQueryPort.getTaskByIdForCurrentUser(currentUser, taskId);
  }
}
