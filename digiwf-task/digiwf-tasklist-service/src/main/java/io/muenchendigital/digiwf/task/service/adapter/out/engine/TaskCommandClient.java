package io.muenchendigital.digiwf.task.service.adapter.out.engine;

import org.camunda.bpm.engine.TaskService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Encapsulation of the Camunda remote client.
 */
@Component
public class TaskCommandClient {
  private final TaskService taskService;

  public TaskCommandClient(@Qualifier("remote") TaskService taskService) {
    this.taskService = taskService;
  }

  public void completeTask(String taskId, Map<String, Object> variables) {
    taskService.complete(taskId, variables);
  }
}
