package io.muenchendigital.digiwf.task.importer;

import io.holunda.polyflow.taskpool.collector.task.TaskServiceCollectorService;
import io.muenchendigital.digiwf.task.TaskManagementProperties;
import io.muenchendigital.digiwf.task.listener.AssignmentCreateTaskListener;
import io.muenchendigital.digiwf.task.listener.CancelableTaskStatusCreateTaskListener;
import io.muenchendigital.digiwf.task.listener.TaskDescriptionCreateTaskListener;
import io.muenchendigital.digiwf.task.listener.TaskSchemaTypeCreateTaskListener;
import lombok.val;
import org.assertj.core.api.Assertions;
import org.camunda.bpm.engine.ProcessEngineConfiguration;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.impl.telemetry.TelemetryRegistry;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.junit5.ProcessEngineExtension;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.assertThat;
import static org.mockito.Mockito.mock;

@Disabled("This test is disabled from CI, since it uses the same in-mem H2 DB as CancelableTaskStatusCreateTaskListenerTest and interfers with it")
// FIXME - make sure we can run multiple tests with the engine -> maybe a dedicated ITEst-module for this is required.
class TaskImporterServiceDeletionTest {

  @RegisterExtension
  public static ProcessEngineExtension extension = ProcessEngineExtension
      .builder()
      .useProcessEngine(
          ProcessEngineConfiguration
              .createStandaloneInMemProcessEngineConfiguration()
              .setHistory("none")
              .setJdbcUrl("jdbc:h2:mem:camunda-importer;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE;")
              .setSkipHistoryOptimisticLockingExceptions(true)
              .setTelemetryRegistry(mock(TelemetryRegistry.class))
              .buildProcessEngine()
      )
      .build();

  private final RuntimeService runtimeService = extension.getRuntimeService();
  private final TaskService taskService = extension.getTaskService();

  private final AssignmentCreateTaskListener realListener = new AssignmentCreateTaskListener(
      new TaskManagementProperties.AssignmentProperties(true, true, true)
  );


  private final TaskImporterService service = new TaskImporterService(
      mock(TaskServiceCollectorService.class),
      taskService,
      extension.getProcessEngineConfiguration(),
      mock(TaskEnrichBatchJobHandler.class)
  );


  // @Test
  // @Deployment(resources = "process_importer_service.bpmn")
  public void enrich_tasks_and_deletes_assignees() {

    val instance = runtimeService.startProcessInstanceByKey("assignment_test_process");
    assertThat(instance).isStarted();

    Assertions.assertThat(taskService.createTaskQuery().processDefinitionKey("assignment_test_process").count()).isEqualTo(5);

    service.enrichExistingTasks();

    taskService.createTaskQuery().list().forEach(task -> Assertions.assertThat(task.getAssignee()).isNull());

   }

}
