package io.muenchendigital.digiwf.task.service.rest.impl;

import io.muenchendigital.digiwf.task.service.query.TaskQueryAdapter;
import io.muenchendigital.digiwf.task.service.query.TaskSchemaRefResolver;
import io.muenchendigital.digiwf.task.service.rest.api.TaskApiDelegate;
import io.muenchendigital.digiwf.task.service.rest.mapper.TaskMapper;
import io.muenchendigital.digiwf.task.service.rest.model.TaskAssignmentTO;
import io.muenchendigital.digiwf.task.service.rest.model.TaskCombinedSchemaTO;
import io.muenchendigital.digiwf.task.service.rest.model.TaskDeferralTO;
import io.muenchendigital.digiwf.task.service.rest.model.TaskWithDetailsTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;

/**
 * Task API delegate.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class TaskApiDelegateImpl implements TaskApiDelegate {
  private final TaskMapper taskMapper;
  private final TaskQueryAdapter taskQueryAdapter;
  private final TaskSchemaRefResolver schemaResolver;

  @Override
  public ResponseEntity<TaskCombinedSchemaTO> getSchema(String schemaId) {
    return TaskApiDelegate.super.getSchema(schemaId);
  }

  @Override
  public ResponseEntity<TaskCombinedSchemaTO> getTaskSchema(String taskId) {
    return TaskApiDelegate.super.getTaskSchema(taskId);
  }

  @Override
  public ResponseEntity<TaskWithDetailsTO> getTaskByTaskId(String taskId) {
    var task = taskQueryAdapter.getTaskByIdForCurrentUser(taskId);
    return ok(taskMapper.to(task, schemaResolver.apply(task)));
  }

  @Override
  public ResponseEntity<Void> completeTask(String taskId, Map<String, Object> requestBody) {
    return TaskApiDelegate.super.completeTask(taskId, requestBody);
  }

  @Override
  public ResponseEntity<Void> saveTaskVariables(String taskId, Map<String, Object> requestBody) {
    return TaskApiDelegate.super.saveTaskVariables(taskId, requestBody);
  }

  @Override
  public ResponseEntity<Void> assignTask(String taskId, TaskAssignmentTO taskAssignmentTO) {
    return TaskApiDelegate.super.assignTask(taskId, taskAssignmentTO);
  }

  @Override
  public ResponseEntity<Void> unassignTask(String taskId) {
    return TaskApiDelegate.super.unassignTask(taskId);
  }

  @Override
  public ResponseEntity<Void> deferTask(String taskId, TaskDeferralTO taskDeferralTO) {
    return TaskApiDelegate.super.deferTask(taskId, taskDeferralTO);
  }

  @Override
  public ResponseEntity<Void> undeferTask(String taskId) {
    return TaskApiDelegate.super.undeferTask(taskId);
  }
}
