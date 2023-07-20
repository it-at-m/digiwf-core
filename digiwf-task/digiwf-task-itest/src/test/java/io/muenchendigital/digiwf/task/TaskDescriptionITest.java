package io.muenchendigital.digiwf.task;

import io.holunda.camunda.taskpool.api.task.CreateTaskCommand;
import io.holunda.camunda.taskpool.api.task.EngineTaskCommand;
import io.holunda.polyflow.taskpool.sender.gateway.CommandListGateway;
import io.muenchendigital.digiwf.task.importer.TaskImporterService;
import lombok.val;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static io.holunda.camunda.bpm.data.CamundaBpmData.builder;
import static io.holunda.camunda.bpm.data.CamundaBpmData.writer;
import static io.muenchendigital.digiwf.task.TaskVariables.TASK_DESCRIPTION;
import static io.muenchendigital.digiwf.task.TaskVariables.TASK_DESCRIPTION_LEGACY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ActiveProfiles("itest")
public class TaskDescriptionITest {

  @Autowired
  private RuntimeService runtimeService;

  @Autowired
  private TaskService taskService;

  @MockBean
  private CommandListGateway gateway;

  @Autowired
  private TaskImporterService importerService;
  @Captor
  private ArgumentCaptor<List<Object>> commandCaptor;
  private TestHelper helper;

  @BeforeEach
  public void init_helper() {
    helper = new TestHelper(runtimeService, taskService, "itest_user_task", commandCaptor);
  }

  @Test
  public void should_create_user_task_with_description_from_legacy_variable() {

    val description = "legacy description of the task";
    helper.start(builder()
        .set(TASK_DESCRIPTION_LEGACY, description)
        .build()
    );

    verify(gateway).sendToGateway(commandCaptor.capture());

    val task = helper.userTask();
    assertThat(task.getDescription()).isEqualTo(description);

    assertThat(helper.command()).extracting(EngineTaskCommand::getEventName).isEqualTo("create");
    assertThat(helper.command()).extracting(cmd -> ((CreateTaskCommand) cmd).getDescription()).isEqualTo(description);
  }

  @Test
  public void should_create_user_task_with_description_from_variable() {

    val description = "description of the task";
    helper.start(builder()
        .set(TASK_DESCRIPTION, description)
        .build()
    );

    verify(gateway).sendToGateway(commandCaptor.capture());

    val task = helper.userTask();
    assertThat(task.getDescription()).isEqualTo(description);

    assertThat(helper.command()).extracting(EngineTaskCommand::getEventName).isEqualTo("create");
    assertThat(helper.command()).extracting(cmd -> ((CreateTaskCommand) cmd).getDescription()).isEqualTo(description);
  }

  @Test
  public void should_create_user_task_without_description_and_enrich_later_legacy() {

    val description = "legacy description of the task";
    helper.start(builder()
        .build()
    );

    verify(gateway).sendToGateway(commandCaptor.capture());

    val task = helper.userTask();
    assertThat(task.getDescription()).isEqualTo(null);

    assertThat(helper.command()).extracting(EngineTaskCommand::getEventName).isEqualTo("create");
    assertThat(helper.command()).extracting(cmd -> ((CreateTaskCommand) cmd).getDescription()).isEqualTo(null);

    writer(taskService, task.getId()).setLocal(TASK_DESCRIPTION_LEGACY, description);

    importerService.enrichExistingTasks();
    importerService.importExistingTasks();

    verify(gateway, atLeastOnce()).sendToGateway(commandCaptor.capture());
    assertThat(helper.commands()).hasSize(1);
    assertThat(helper.command()).extracting(cmd -> ((CreateTaskCommand) cmd).getDescription()).isEqualTo(description);
  }

  @Test
  public void should_create_user_task_without_description_and_enrich_later() {

    val description = "description of the task";
    helper.start(builder()
        .build()
    );

    verify(gateway).sendToGateway(commandCaptor.capture());

    val task = helper.userTask();
    assertThat(task.getDescription()).isEqualTo(null);

    assertThat(helper.command()).extracting(EngineTaskCommand::getEventName).isEqualTo("create");
    assertThat(helper.command()).extracting(cmd -> ((CreateTaskCommand) cmd).getDescription()).isEqualTo(null);

    writer(taskService, task.getId()).setLocal(TASK_DESCRIPTION, description);

    importerService.enrichExistingTasks();
    importerService.importExistingTasks();

    verify(gateway, atLeastOnce()).sendToGateway(commandCaptor.capture());
    assertThat(helper.commands()).hasSize(1);
    assertThat(helper.command()).extracting(cmd -> ((CreateTaskCommand) cmd).getDescription()).isEqualTo(description);
  }


  @AfterEach
  public void clean_up() {
    helper.cleanup();
  }
}
