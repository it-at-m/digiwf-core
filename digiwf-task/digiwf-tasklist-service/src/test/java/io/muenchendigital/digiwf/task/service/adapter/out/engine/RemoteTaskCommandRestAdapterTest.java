package io.muenchendigital.digiwf.task.service.adapter.out.engine;

import io.holunda.camunda.bpm.data.CamundaBpmData;
import io.holunda.camunda.bpm.data.factory.VariableFactory;
import io.muenchendigital.digiwf.task.BpmnErrors;
import io.muenchendigital.digiwf.task.service.application.port.out.engine.TaskCommandPort;
import lombok.val;
import org.camunda.bpm.engine.TaskService;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static io.holunda.camunda.bpm.data.CamundaBpmData.stringVariable;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class RemoteTaskCommandRestAdapterTest {
  private final TaskService taskService = mock(TaskService.class);
  private final TaskCommandPort taskCommandPort = new RemoteTaskCommandRestAdapter(taskService);
  private final String taskId = UUID.randomUUID().toString();

  private final VariableFactory<String> STRING_VAR = stringVariable("STRING_VAR");

  @Test
  public void cancels_task() {
    taskCommandPort.cancelUserTask(taskId);
    verify(taskService).handleBpmnError(taskId, BpmnErrors.DEFAULT_TASK_CANCELLATION_ERROR);
  }

  @Test
  public void assign() {
    taskCommandPort.assignUserTask(taskId, "user");
    verify(taskService).setAssignee(taskId, "user");
  }

  @Test
  public void unassign() {
    taskCommandPort.unassignUserTask(taskId);
    verify(taskService).setAssignee(taskId, null);
  }

  @Test
  public void complete() {
    val vars = CamundaBpmData
        .builder()
        .set(STRING_VAR, "init")
        .build();
    taskCommandPort.completeUserTask(taskId, vars);
    verify(taskService).complete(taskId, vars);
  }

  @Test
  public void save() {
    val vars = CamundaBpmData
        .builder()
        .set(STRING_VAR, "init")
        .build();
    taskCommandPort.saveUserTask(taskId, vars);
    verify(taskService).setVariables(taskId, vars);
  }
}