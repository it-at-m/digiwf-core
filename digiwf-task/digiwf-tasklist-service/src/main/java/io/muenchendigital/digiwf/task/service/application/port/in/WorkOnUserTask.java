package io.muenchendigital.digiwf.task.service.application.port.in;

import io.muenchendigital.digiwf.task.service.application.port.out.polyflow.TaskNotFoundException;
import io.muenchendigital.digiwf.task.service.domain.TaskWithSchema;

import java.util.Map;

/**
 * Use case describing all operations a user can perform on an individfual usertask.
 */
public interface WorkOnUserTask {

  /**
   * Loads a user task by id.
   *
   * @param taskId task id.
   * @return Task with schema.
   * @throws TaskNotFoundException if task is not available or access is not permitted.
   */
  TaskWithSchema loadUserTask(String taskId) throws TaskNotFoundException;

  /**
   * Completes user task.
   *
   * @param taskId  task id.
   * @param payload process variables to pass to the process during completion.
   * @throws TaskNotFoundException if task is not available or access is not permitted.
   */
  void completeUserTask(String taskId, Map<String, Object> payload) throws TaskNotFoundException;

}
