package io.muenchendigital.digiwf.task.listener;

import io.muenchendigital.digiwf.task.TaskManagementProperties;
import org.assertj.core.util.Lists;
import org.camunda.community.mockito.delegate.DelegateTaskFake;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static io.muenchendigital.digiwf.task.TaskVariables.*;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.when;

class AssignmentCreateTaskListenerTest {

  private final TaskManagementProperties.AssignmentProperties properties = Mockito.mock(TaskManagementProperties.AssignmentProperties.class);
  private final AssignmentCreateTaskListener assignmentCreateTaskListener = new AssignmentCreateTaskListener(properties);

  private DelegateTaskFake delegateTask;

  @BeforeEach
  void setup_task() {
    delegateTask = new DelegateTaskFake().withAssignee("assignee");
    delegateTask.addCandidateGroup("candidateGroup1");
    delegateTask.addCandidateGroup("candidateGroup2");
    delegateTask.addCandidateUser("candidateUser1");
    delegateTask.addCandidateUser("candidateUser2");
  }


  @Test
  public void is_disabled_by_properties() {
    when(properties.isShadow()).thenReturn(false);

    assignmentCreateTaskListener.taskCreated(delegateTask);
    assertThat(delegateTask.getVariables()).isEmpty();
    assertThat(delegateTask.getAssignee()).isEqualTo("assignee");
    assertThat(DelegateTaskFake.candidateUserIds(delegateTask)).containsExactlyInAnyOrder("candidateUser1", "candidateUser2");
    assertThat(DelegateTaskFake.candidateGroupIds(delegateTask)).containsExactlyInAnyOrder("candidateGroup1", "candidateGroup2");
  }

  @Test
  public void shadows_assignee_local() {
    when(properties.isShadow()).thenReturn(true);
    when(properties.isLocal()).thenReturn(true);

    assignmentCreateTaskListener.taskCreated(delegateTask);

    assertThat(delegateTask.getVariablesLocal()).containsEntry(TASK_ASSIGNEE.getName(), "assignee");
    assertThat(delegateTask.getVariablesLocal()).containsEntry(TASK_CANDIDATE_USERS.getName(), Lists.newArrayList("candidateUser1", "candidateUser2"));
    assertThat(delegateTask.getVariablesLocal()).containsEntry(TASK_CANDIDATE_GROUPS.getName(), Lists.newArrayList("candidateGroup1", "candidateGroup2"));

    assertThat(delegateTask.getAssignee()).isEqualTo("assignee");
    assertThat(DelegateTaskFake.candidateUserIds(delegateTask)).containsExactlyInAnyOrder("candidateUser1", "candidateUser2");
    assertThat(DelegateTaskFake.candidateGroupIds(delegateTask)).containsExactlyInAnyOrder("candidateGroup1", "candidateGroup2");

  }

  @Test
  public void shadows_assignee_global() {
    when(properties.isShadow()).thenReturn(true);
    when(properties.isLocal()).thenReturn(false);

    assignmentCreateTaskListener.taskCreated(delegateTask);
    assertThat(delegateTask.getVariables()).containsEntry(TASK_ASSIGNEE.getName(), "assignee");
    assertThat(delegateTask.getVariables()).containsEntry(TASK_CANDIDATE_USERS.getName(), Lists.newArrayList("candidateUser1", "candidateUser2"));
    assertThat(delegateTask.getVariables()).containsEntry(TASK_CANDIDATE_GROUPS.getName(), Lists.newArrayList("candidateGroup1", "candidateGroup2"));

    assertThat(delegateTask.getAssignee()).isEqualTo("assignee");
    assertThat(DelegateTaskFake.candidateUserIds(delegateTask)).containsExactlyInAnyOrder("candidateUser1", "candidateUser2");
    assertThat(DelegateTaskFake.candidateGroupIds(delegateTask)).containsExactlyInAnyOrder("candidateGroup1", "candidateGroup2");
  }

  @Test
  public void shadows_assignee_local_and_deletes_from_task() {
    when(properties.isShadow()).thenReturn(true);
    when(properties.isLocal()).thenReturn(true);
    when(properties.isDelete()).thenReturn(true);

    assignmentCreateTaskListener.taskCreated(delegateTask);
    assertThat(delegateTask.getVariables()).containsEntry(TASK_CANDIDATE_USERS.getName(), Lists.newArrayList("candidateUser1", "candidateUser2"));
    assertThat(delegateTask.getVariables()).containsEntry(TASK_CANDIDATE_GROUPS.getName(), Lists.newArrayList("candidateGroup1", "candidateGroup2"));

    assertThat(delegateTask.getVariablesLocal()).containsEntry(TASK_ASSIGNEE.getName(), "assignee");
    assertThat(delegateTask.getAssignee()).isNull();
    assertThat(DelegateTaskFake.candidateUserIds(delegateTask)).isEmpty();
    assertThat(DelegateTaskFake.candidateGroupIds(delegateTask)).isEmpty();
  }

}