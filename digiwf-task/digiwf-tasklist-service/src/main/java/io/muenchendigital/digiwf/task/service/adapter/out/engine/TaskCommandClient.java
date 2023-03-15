package io.muenchendigital.digiwf.task.service.adapter.out.engine;

import io.muenchendigital.digiwf.task.service.application.port.out.engine.TaskCommandPort;
import org.camunda.bpm.engine.TaskService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Encapsulation of the Camunda remote client.
 */
@Component
public class TaskCommandClient implements TaskCommandPort {
  private final TaskService taskService;

  public TaskCommandClient(@Qualifier("remote") TaskService taskService) {
    this.taskService = taskService;
  }

  @Override
  public void completeTask(String taskId, Map<String, Object> variables) {
    taskService.complete(taskId, variables);
  }
}
