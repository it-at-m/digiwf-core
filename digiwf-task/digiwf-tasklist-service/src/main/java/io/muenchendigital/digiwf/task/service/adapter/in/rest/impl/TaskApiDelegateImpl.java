package io.muenchendigital.digiwf.task.service.adapter.in.rest.impl;

import io.muenchendigital.digiwf.task.service.adapter.in.rest.mapper.TaskMapper;
import io.muenchendigital.digiwf.task.service.application.port.in.WorkOnUserTask;
import io.muenchendigital.digiwf.task.service.port.in.rest.api.TaskApiDelegate;
import io.muenchendigital.digiwf.task.service.port.in.rest.model.TaskAssignmentTO;
import io.muenchendigital.digiwf.task.service.port.in.rest.model.TaskCombinedSchemaTO;
import io.muenchendigital.digiwf.task.service.port.in.rest.model.TaskDeferralTO;
import io.muenchendigital.digiwf.task.service.port.in.rest.model.TaskWithDetailsTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;

/**
 * Task API delegate for work on one user task.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class TaskApiDelegateImpl implements TaskApiDelegate {
  private final TaskMapper taskMapper;
  private final WorkOnUserTask workOnUserTask;

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
    var taskWithSchema = workOnUserTask.loadUserTask(taskId);
    return ok(taskMapper.toWithDetails(taskWithSchema.getTask(), taskWithSchema.getSchemaRef()));
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
