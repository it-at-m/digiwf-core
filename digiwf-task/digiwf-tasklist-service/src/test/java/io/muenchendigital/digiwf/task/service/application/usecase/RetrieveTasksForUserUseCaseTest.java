package io.muenchendigital.digiwf.task.service.application.usecase;

import com.google.common.collect.Sets;
import io.holunda.camunda.taskpool.api.task.ProcessReference;
import io.holunda.polyflow.view.Task;
import io.holunda.polyflow.view.auth.User;
import io.muenchendigital.digiwf.task.service.adapter.out.schema.VariableTaskSchemaResolverAdapter;
import io.muenchendigital.digiwf.task.service.application.port.in.RetrieveTasksForUser;
import io.muenchendigital.digiwf.task.service.application.port.out.auth.CurrentUserPort;
import io.muenchendigital.digiwf.task.service.application.port.out.polyflow.TaskQueryPort;
import io.muenchendigital.digiwf.task.service.application.port.out.schema.TaskSchemaRefResolverPort;
import io.muenchendigital.digiwf.task.service.domain.PageOfTasks;
import io.muenchendigital.digiwf.task.service.domain.PagingAndSorting;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static io.muenchendigital.digiwf.task.service.application.usecase.TestFixtures.generateTasks;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.camunda.bpm.engine.variable.Variables.createVariables;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

class RetrieveTasksForUserUseCaseTest {

  private final TaskQueryPort taskQueryPort = mock(TaskQueryPort.class);
  private final CurrentUserPort currentUserPort = mock(CurrentUserPort.class);
  private final TaskSchemaRefResolverPort taskSchemaRefResolverPort = new VariableTaskSchemaResolverAdapter();

  private final RetrieveTasksForUser useCase = new RetrieveTasksForUserUseCase(
      taskQueryPort,
      currentUserPort,
      taskSchemaRefResolverPort
  );

  private final String query = "";
  private final User user = new User("0123456789", Sets.newHashSet("group1", "group2"));
  private final PagingAndSorting pagingAndSorting = new PagingAndSorting(0, 100, "");

  @BeforeEach
  void setupMocks() {
    when(currentUserPort.getCurrentUser()).thenReturn(user);
  }

  @Test
  void getsUnassignedTasksForCurrentUserGroup() {

    val pageOfTasks = new PageOfTasks(
        generateTasks(17, Sets.newHashSet(), Sets.newHashSet("group1"), null),
        17,
        pagingAndSorting
    );

    when(taskQueryPort.getTasksForCurrentUserGroup(any(), anyString(), anyBoolean(), any())).thenReturn(pageOfTasks);

    val tasks = useCase.getUnassignedTasksForCurrentUserGroup(query, pagingAndSorting);
    assertThat(tasks.getTotalElementsCount()).isEqualTo(17);
    verify(taskQueryPort).getTasksForCurrentUserGroup(user, query, false, pagingAndSorting);
    verifyNoMoreInteractions(taskQueryPort);
  }

  @Test
  void getsAssignedTasksForCurrentUserGroup() {

    val pageOfTasks = new PageOfTasks(
        generateTasks(17, Sets.newHashSet(), Sets.newHashSet("group1"), null),
        17,
        pagingAndSorting
    );

    when(taskQueryPort.getTasksForCurrentUserGroup(any(), anyString(), anyBoolean(), any())).thenReturn(pageOfTasks);

    val tasks = useCase.getAssignedTasksForCurrentUserGroup(query, pagingAndSorting);
    assertThat(tasks.getTotalElementsCount()).isEqualTo(17);
    verify(taskQueryPort).getTasksForCurrentUserGroup(user, query, true, pagingAndSorting);
    verifyNoMoreInteractions(taskQueryPort);
  }

  @Test
  void getsTasksForCurrentUserGroup() {

    val pageOfTasks = new PageOfTasks(
        generateTasks(17, Sets.newHashSet(), Sets.newHashSet("group1"), null),
        17,
        pagingAndSorting
    );

    when(taskQueryPort.getTasksForCurrentUser(any(), anyString(), any())).thenReturn(pageOfTasks);

    val tasks = useCase.getTasksForCurrentUser(query, pagingAndSorting);
    assertThat(tasks.getTotalElementsCount()).isEqualTo(17);
    verify(taskQueryPort).getTasksForCurrentUser(user, query, pagingAndSorting);
    verifyNoMoreInteractions(taskQueryPort);
  }
}