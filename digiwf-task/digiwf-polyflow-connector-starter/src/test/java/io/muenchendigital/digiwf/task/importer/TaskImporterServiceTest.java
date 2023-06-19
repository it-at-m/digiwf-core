package io.muenchendigital.digiwf.task.importer;

import io.holunda.polyflow.taskpool.collector.task.TaskServiceCollectorService;
import io.muenchendigital.digiwf.task.listener.AssignmentCreateTaskListener;
import io.muenchendigital.digiwf.task.listener.CancelableTaskStatusCreateTaskListener;
import io.muenchendigital.digiwf.task.listener.TaskDescriptionCreateTaskListener;
import io.muenchendigital.digiwf.task.listener.TaskSchemaTypeCreateTaskListener;
import lombok.val;
import org.assertj.core.api.Assertions;
import org.camunda.bpm.engine.ProcessEngineConfiguration;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.junit5.ProcessEngineExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.mockito.ArgumentCaptor;

import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;

class TaskImporterServiceTest {

  @RegisterExtension
  public static ProcessEngineExtension extension = ProcessEngineExtension
      .builder()
      .useProcessEngine(
          ProcessEngineConfiguration
              .createStandaloneInMemProcessEngineConfiguration()
              .setJdbcUrl("jdbc:h2:mem:camunda;DB_CLOSE_DELAY=1000")
              .buildProcessEngine()
      )
      .build();

  private final RuntimeService runtimeService = extension.getRuntimeService();
  private final TaskService taskService = extension.getTaskService();

  private final AssignmentCreateTaskListener listenerMock = mock(AssignmentCreateTaskListener.class);


  private final TaskImporterService service = new TaskImporterService(
      mock(TaskServiceCollectorService.class),
      taskService,
      listenerMock,
      mock(CancelableTaskStatusCreateTaskListener.class),
      mock(TaskSchemaTypeCreateTaskListener.class),
      mock(TaskDescriptionCreateTaskListener.class)
  );

  private final ArgumentCaptor<DelegateTask> taskParamCaptor = ArgumentCaptor.forClass(DelegateTask.class);

  @BeforeEach
  void init_bridge() {
    doNothing().when(listenerMock).taskCreated(taskParamCaptor.capture());
  }

  @Test
  @Deployment(resources = "process_importer_service.bpmn")
  public void queries_tasks() {

    val instance = runtimeService.startProcessInstanceByKey("assignment_test_process");
    assertThat(instance).isStarted();

    Assertions.assertThat(taskService.createTaskQuery().count()).isEqualTo(5);

    service.enrichExistingTasks();

    val invokedOnTasks = taskParamCaptor.getAllValues();
    Assertions.assertThat(invokedOnTasks.stream().map(DelegateTask::getTaskDefinitionKey)).containsExactlyInAnyOrder("user_assigned", "user_candidate_users", "user_candidate_groups", "user_assigned_and_candidate_group");
    Assertions.assertThat(invokedOnTasks.stream().map(DelegateTask::getTaskDefinitionKey)).doesNotContain("user_empty");
  }

}