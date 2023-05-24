package io.muenchendigital.digiwf.task.service.application.usecase;

import com.google.common.collect.Sets;
import io.holunda.polyflow.view.auth.User;
import io.muenchendigital.digiwf.task.service.adapter.out.schema.VariableTaskSchemaResolverAdapter;
import io.muenchendigital.digiwf.task.service.application.port.in.RetrieveTasksForUser;
import io.muenchendigital.digiwf.task.service.application.port.out.auth.CurrentUserPort;
import io.muenchendigital.digiwf.task.service.application.port.out.cancellation.CancellationFlagOutPort;
import io.muenchendigital.digiwf.task.service.application.port.out.polyflow.TaskQueryPort;
import io.muenchendigital.digiwf.task.service.application.port.out.schema.TaskSchemaRefResolverPort;
import io.muenchendigital.digiwf.task.service.domain.PageOfTasks;
import io.muenchendigital.digiwf.task.service.domain.PagingAndSorting;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.muenchendigital.digiwf.task.service.application.usecase.TestFixtures.generateTasks;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

class RetrieveTasksForUserUseCaseTest {

  private final TaskQueryPort taskQueryPort = mock(TaskQueryPort.class);
  private final CurrentUserPort currentUserPort = mock(CurrentUserPort.class);

  private final CancellationFlagOutPort cancellationFlagOutPort = mock(CancellationFlagOutPort.class);
  private final TaskSchemaRefResolverPort taskSchemaRefResolverPort = new VariableTaskSchemaResolverAdapter();

  private final RetrieveTasksForUser useCase = new RetrieveTasksForUserUseCase(
      taskQueryPort,
      currentUserPort,
      taskSchemaRefResolverPort,
      cancellationFlagOutPort
  );

  private final String query = "";
  private final User user = new User("0123456789", Sets.newHashSet("group1", "group2"));
  private final PagingAndSorting pagingAndSorting = new PagingAndSorting(0, 100, "");

  @BeforeEach
  void setupMocks() {
    when(currentUserPort.getCurrentUser()).thenReturn(user);
    when(cancellationFlagOutPort.apply(any())).thenReturn(true);
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
  void getsTasksForCurrentUser() {

    val pageOfTasks = new PageOfTasks(
        generateTasks(17, Sets.newHashSet(), Sets.newHashSet(), user.getUsername()),
        17,
        pagingAndSorting
    );

    when(taskQueryPort.getTasksForCurrentUser(any(), anyString(), any(), any())).thenReturn(pageOfTasks);

    val tasks = useCase.getTasksForCurrentUser(query, null, pagingAndSorting);
    assertThat(tasks.getTotalElementsCount()).isEqualTo(17);
    verify(taskQueryPort).getTasksForCurrentUser(user, query, null, pagingAndSorting);
    verifyNoMoreInteractions(taskQueryPort);
  }
}