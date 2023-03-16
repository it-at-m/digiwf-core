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

import static org.springframework.http.ResponseEntity.noContent;
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
    // FIXME: check with Dominik and Rene
    return TaskApiDelegate.super.getSchema(schemaId);
  }

  @Override
  public ResponseEntity<TaskCombinedSchemaTO> getTaskSchema(String taskId) {
    // FIXME: check with Dominik and Rene
    return TaskApiDelegate.super.getTaskSchema(taskId);
  }

  @Override
  public ResponseEntity<TaskWithDetailsTO> getTaskByTaskId(String taskId) {
    var taskWithSchema = workOnUserTask.loadUserTask(taskId);
    return ok(taskMapper.toWithDetails(taskWithSchema.getTask(), taskWithSchema.getSchemaRef()));
  }

  @Override
  public ResponseEntity<Void> completeTask(String taskId, Map<String, Object> requestBody) {
    workOnUserTask.completeUserTask(taskId, requestBody);
    return noContent().build();
  }

  @Override
  public ResponseEntity<Void> saveTaskVariables(String taskId, Map<String, Object> requestBody) {
    workOnUserTask.saveUserTask(taskId, requestBody);
    return noContent().build();
  }

  @Override
  public ResponseEntity<Void> assignTask(String taskId, TaskAssignmentTO taskAssignmentTO) {
    workOnUserTask.assignUserTask(taskId, taskAssignmentTO.getAssignee());
    return noContent().build();
  }

  @Override
  public ResponseEntity<Void> unassignTask(String taskId) {
    workOnUserTask.unassignUserTask(taskId);
    return noContent().build();
  }

  @Override
  public ResponseEntity<Void> deferTask(String taskId, TaskDeferralTO taskDeferralTO) {
    workOnUserTask.deferUserTask(taskId, taskDeferralTO.getFollowUpDate());
    return noContent().build();
  }

  @Override
  public ResponseEntity<Void> undeferTask(String taskId) {
    workOnUserTask.undeferUserTask(taskId);
    return noContent().build();
  }
}
