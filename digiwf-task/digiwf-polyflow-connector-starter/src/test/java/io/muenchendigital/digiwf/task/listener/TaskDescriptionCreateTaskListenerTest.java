package io.muenchendigital.digiwf.task.listener;

import io.holunda.camunda.bpm.data.CamundaBpmData;
import lombok.val;
import org.camunda.community.mockito.delegate.DelegateExecutionFake;
import org.camunda.community.mockito.delegate.DelegateTaskFake;
import org.junit.jupiter.api.Test;

import static io.muenchendigital.digiwf.task.TaskVariables.TASK_DESCRIPTION;
import static io.muenchendigital.digiwf.task.TaskVariables.TASK_DESCRIPTION_LEGACY;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class TaskDescriptionCreateTaskListenerTest {

  private static final String MY_DESCRIPTION = "My description";

  private final TaskDescriptionCreateTaskListener listener = new TaskDescriptionCreateTaskListener();

  @Test
  public void should_set_description_from_task_variable() {
    val delegateExecution = new DelegateExecutionFake();
    val delegateTask = new DelegateTaskFake().withExecution(delegateExecution)
        .withVariables(
            CamundaBpmData
                .builder()
                .set(TASK_DESCRIPTION_LEGACY, MY_DESCRIPTION)
                .build()

        );
    listener.taskCreated(delegateTask);
    assertThat(delegateTask.getDescription()).isEqualTo(MY_DESCRIPTION);
  }

  @Test
  public void should_set_description_from_task_legacy_variable() {
    val delegateExecution = new DelegateExecutionFake();
    val delegateTask = new DelegateTaskFake().withExecution(delegateExecution).withVariables(
        CamundaBpmData
            .builder()
            .set(TASK_DESCRIPTION_LEGACY, MY_DESCRIPTION)
            .build()
    );
    listener.taskCreated(delegateTask);
    assertThat(delegateTask.getDescription()).isEqualTo(MY_DESCRIPTION);
  }

  @Test
  public void should_set_description_from_execution_variable() {
    val delegateExecution = new DelegateExecutionFake().withVariables(
        CamundaBpmData
            .builder()
            .set(TASK_DESCRIPTION, MY_DESCRIPTION)
            .build()
    );
    val delegateTask = new DelegateTaskFake().withExecution(delegateExecution);
    listener.taskCreated(delegateTask);
    assertThat(delegateTask.getDescription()).isEqualTo(MY_DESCRIPTION);
  }

  @Test
  public void should_set_description_from_execution_legacy_variable() {
    val delegateExecution = new DelegateExecutionFake().withVariables(
        CamundaBpmData
            .builder()
            .set(TASK_DESCRIPTION_LEGACY, MY_DESCRIPTION)
            .build()
    );
    val delegateTask = new DelegateTaskFake().withExecution(delegateExecution);
    listener.taskCreated(delegateTask);
    assertThat(delegateTask.getDescription()).isEqualTo(MY_DESCRIPTION);
  }

  @Test
  public void should_not_change_description_to_null() {
    val delegateExecution = new DelegateExecutionFake();
    val delegateTask = new DelegateTaskFake().withExecution(delegateExecution).withDescription(MY_DESCRIPTION);
    listener.taskCreated(delegateTask);
    assertThat(delegateTask.getDescription()).isEqualTo(MY_DESCRIPTION);
  }

}