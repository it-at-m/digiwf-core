package io.muenchendigital.digiwf.task.listener;

import io.muenchendigital.digiwf.task.TaskSchemaType;
import lombok.val;
import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.ProcessEngineServices;
import org.camunda.community.mockito.delegate.DelegateTaskFake;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static io.holunda.camunda.bpm.data.CamundaBpmData.reader;
import static io.muenchendigital.digiwf.task.TaskVariables.*;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.task;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TaskSchemaTypeCreateTaskListenerTest {

  private final TaskSchemaTypeCreateTaskListener listener = new TaskSchemaTypeCreateTaskListener();
  private final ProcessEngineServices processEngineServices = mock(ProcessEngineServices.class);
  private final FormService formService = mock(FormService.class);

  @BeforeEach
  public void setupMocks() {
    when(processEngineServices.getFormService()).thenReturn(formService);
  }

  @Test
  public void detect_schema_based_form() {
    val task = new DelegateTaskFake(UUID.randomUUID().toString())
        .withProcessEngineServices(processEngineServices)
        .withProcessDefinitionId(UUID.randomUUID().toString())
        .withTaskDefinitionKey("task-key");

    when(formService.getTaskFormKey(any(), any())).thenReturn(null);

    listener.taskCreated(task);
    assertThat(task.getVariablesLocal()).containsEntry(TASK_SCHEMA_TYPE.getName(), TaskSchemaType.SCHEMA_BASED);
    verify(formService).getTaskFormKey(task.getProcessDefinitionId(), task.getTaskDefinitionKey());
  }

  @Test
  public void detect_vuetify_form_base_form() {
    val task = new DelegateTaskFake(UUID.randomUUID().toString())
        .withProcessEngineServices(processEngineServices)
        .withProcessDefinitionId(UUID.randomUUID().toString())
        .withTaskDefinitionKey("task-key");

    when(formService.getTaskFormKey(any(), any())).thenReturn("form-ref");

    listener.taskCreated(task);
    assertThat(task.getVariablesLocal()).containsEntry(TASK_SCHEMA_TYPE.getName(), TaskSchemaType.VUETIFY_FORM_BASE);
    verify(formService).getTaskFormKey(task.getProcessDefinitionId(), task.getTaskDefinitionKey());
  }

}