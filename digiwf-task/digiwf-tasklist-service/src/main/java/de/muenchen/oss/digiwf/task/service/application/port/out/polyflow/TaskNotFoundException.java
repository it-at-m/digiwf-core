package de.muenchen.oss.digiwf.task.service.application.port.out.polyflow;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Signals that the task with given id is not available.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class TaskNotFoundException extends RuntimeException {
  /**
   * Creates new exception with corresponding error message.
   *
   * @param taskId task id to report the error for.
   */
  public TaskNotFoundException(String taskId) {
    super("Task with id '" + taskId + "' could not be found.");
  }
}
