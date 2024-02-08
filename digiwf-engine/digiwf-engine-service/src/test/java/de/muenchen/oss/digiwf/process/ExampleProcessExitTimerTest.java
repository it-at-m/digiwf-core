package de.muenchen.oss.digiwf.process;

import org.assertj.core.api.Assertions;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.junit5.ProcessEngineExtension;
import org.camunda.bpm.engine.test.mock.Mocks;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.*;
import static org.camunda.bpm.engine.variable.Variables.createVariables;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test to demonstrate correct exit timer usage.
 */
public class ExampleProcessExitTimerTest {
  @RegisterExtension
  public ProcessEngineExtension processEngineExtension = ProcessEngineExtension.builder()
      .configurationResource("camunda.cfg.xml")
      .build();

  private final ProcessMock processMock = new ProcessMock();

  @BeforeEach
  public void setupMocks(){
    Mocks.register("user",  new UserMock("firstname", "lastname"));
    Mocks.register("process", processMock);
  }


  @Test
  @Deployment(resources = "prozesse/example/all-input-fields/all-input-fields-process.bpmn")
  public void terminates_all_fields_process_after_timer_job_is_triggered() {

    ProcessInstance instance = processEngineExtension.getRuntimeService().startProcessInstanceByKey("example-all-input-fields",
        createVariables()
            .putValue("FormField_User", "1234567")
    );
    assertThat(instance).isStarted();
    assertThat(instance).isWaitingAt("Task_UserTask");
    execute(job());
    assertThat(instance).isEnded();
    assertThat(instance).hasNotPassed("End_completed");
    assertThat(instance).hasPassed("End_terminated"); // process terminated
    assertEquals("AllInputsField gestartet von: firstname lastname", processMock.getDescription());
  }

  @Test
  @Deployment(resources = {"prozesse/example/email-integration/email-integration.bpmn", "dummy/StreamingTemplateV02.bpmn"})
  public void terminates_email_integration_process_after_timer_job_is_triggered() {

    ProcessInstance instance = processEngineExtension.getRuntimeService().startProcessInstanceByKey("email-integration-example",
        createVariables()
            .putValue("FormField_Email", "mail@test.de")
            .putValue("FormField_Message", "My message")
            .putValue("FormField_Subject", "Subject")
            .putValue("FormField_EmailTopic", "dwf-email-local-01")
    );
    assertThat(instance).isStarted();
    assertThat(instance).isWaitingAt("send-mail");
    Assertions.assertThat(processEngineExtension.getRuntimeService().createProcessInstanceQuery().processDefinitionKey("StreamingTemplateV02").count()).isEqualTo(1);
    execute(job());
    assertThat(instance).isEnded();
    assertThat(instance).hasNotPassed("End_completed");
    assertThat(instance).hasPassed("End_terminated"); // process terminated
    Assertions.assertThat(processEngineExtension.getRuntimeService().createProcessInstanceQuery().processDefinitionKey("StreamingTemplateV02").count()).isEqualTo(0); // streaming process terminated
  }

  @Test
  @Deployment(resources = {"prozesse/example/s3-integration/S3TestProzess.bpmn"})
  public void terminates_s3_integration_process_after_timer_job_is_triggered() {

    ProcessInstance instance = processEngineExtension.getRuntimeService().startProcessInstanceByKey("feature-s3-integration",
        createVariables()
            .putValue("FormField_EmailTopic", "dwf-email-local-01")
            .putValue("FormField_CosysTopic", "dwf-cosys-local-01")
            .putValue("starterOfInstance", "123456789")
    );
    assertThat(instance).isStarted();
    assertThat(instance).isWaitingAt("user_task_test");
    execute(job());
    assertThat(instance).isEnded();
    assertThat(instance).hasNotPassed("End_completed");
    assertThat(instance).hasPassed("End_terminated"); // process terminated
  }

  @Test
  @Deployment(resources = {"prozesse/example/user-tasks/usertask-process.bpmn"})
  public void terminates_user_task_process_after_timer_job_is_triggered() {

    ProcessInstance instance = processEngineExtension.getRuntimeService().startProcessInstanceByKey("Usertask-Example",
        createVariables()
            .putValue("FORMFIELD_NumberOfTasks", 1)
            .putValue("FORMFIELD_User", "12345678")
    );

    assertThat(instance).isStarted();
    assertThat(instance).isWaitingAt("Task_UserTask");
    execute(job());
    assertThat(instance).isEnded();
    assertThat(instance).hasNotPassed("End_completed");
    assertThat(instance).hasPassed("End_terminated"); // process terminated
    assertEquals("Usertask gestartet von: firstname lastname", processMock.getDescription());

  }

  @Test
  @Deployment(resources = {"prozesse/example/group-tasks/grouptask-process.bpmn"})
  public void terminates_group_task_process_after_timer_job_is_triggered() {
    ProcessInstance instance = processEngineExtension.getRuntimeService().startProcessInstanceByKey("Grouptask-Example",
        createVariables()
            .putValue("FORMFIELD_NumberOfTasks", 1)
            .putValue("FORMFIELD_group", "group1")
    );
    assertThat(instance).isStarted();
    assertThat(instance).isWaitingAt("Task_GroupUserTask");
    execute(job());
    assertThat(instance).isEnded();
    assertThat(instance).hasNotPassed("End_completed");
    assertThat(instance).hasPassed("End_terminated"); // process terminated
    assertEquals("Gruppentask gestartet von: firstname lastname", processMock.getDescription());

  }

}
