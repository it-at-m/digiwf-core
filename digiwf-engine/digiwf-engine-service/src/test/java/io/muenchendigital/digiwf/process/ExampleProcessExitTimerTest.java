package io.muenchendigital.digiwf.process;

import org.assertj.core.api.Assertions;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.spring.boot.starter.test.helper.StandaloneInMemoryTestConfiguration;
import org.junit.Rule;
import org.junit.Test;

import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.*;
import static org.camunda.bpm.engine.variable.Variables.createVariables;

/**
 * Test to demonstrate correct exit timer usage.
 */
public class ExampleProcessExitTimerTest {
  @Rule
  public ProcessEngineRule rule = new StandaloneInMemoryTestConfiguration().rule();

  @Test
  @Deployment(resources = "prozesse/example/all-input-fields/all-input-fields-process.bpmn")
  public void terminates_all_fields_process_after_timer_job_is_triggered() {

    ProcessInstance instance = rule.getRuntimeService().startProcessInstanceByKey("example-all-input-fields",
        createVariables().putValue("FORMFIELD_User", "1234567")
    );
    assertThat(instance).isStarted();
    assertThat(instance).isWaitingAt("Task_UserTask");
    execute(job());
    assertThat(instance).isEnded();
    assertThat(instance).hasNotPassed("End_completed");
    assertThat(instance).hasPassed("End_terminated"); // process terminated
  }

  @Test
  @Deployment(resources = {"prozesse/example/email-integration/email-integration.bpmn", "dummy/StreamingTemplateV02.bpmn"})
  public void terminates_email_integration_process_after_timer_job_is_triggered() {

    ProcessInstance instance = rule.getRuntimeService().startProcessInstanceByKey("email-integration-example",
        createVariables()
            .putValue("FormField_Email", "mail@test.de")
            .putValue("FormField_Message", "My message")
            .putValue("FormField_Subject", "Subject")
    );
    assertThat(instance).isStarted();
    assertThat(instance).isWaitingAt("send-mail");
    Assertions.assertThat(rule.getRuntimeService().createProcessInstanceQuery().processDefinitionKey("StreamingTemplateV02").count()).isEqualTo(1);
    execute(job());
    assertThat(instance).isEnded();
    assertThat(instance).hasNotPassed("End_completed");
    assertThat(instance).hasPassed("End_terminated"); // process terminated
    Assertions.assertThat(rule.getRuntimeService().createProcessInstanceQuery().processDefinitionKey("StreamingTemplateV02").count()).isEqualTo(0); // streaming process terminated
  }

  @Test
  @Deployment(resources = {"prozesse/example/s3-integration/S3TestProzess.bpmn"})
  public void terminates_s3_integration_process_after_timer_job_is_triggered() {

    ProcessInstance instance = rule.getRuntimeService().startProcessInstanceByKey("feature-s3-integration",
        createVariables()
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

    ProcessInstance instance = rule.getRuntimeService().startProcessInstanceByKey("Usertask-Example",
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
  }

  @Test
  @Deployment(resources = {"prozesse/example/group-tasks/grouptask-process.bpmn"})
  public void terminates_group_task_process_after_timer_job_is_triggered() {

    ProcessInstance instance = rule.getRuntimeService().startProcessInstanceByKey("Grouptask-Example",
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
  }

}
