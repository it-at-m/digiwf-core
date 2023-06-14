package io.muenchendigital.digiwf.task.service.adapter.out.polyflow;

import com.google.common.collect.Sets;
import io.holunda.polyflow.view.TaskQueryClient;
import io.holunda.polyflow.view.auth.User;
import io.holunda.polyflow.view.query.task.TaskQueryResult;
import io.holunda.polyflow.view.query.task.TasksForCandidateUserAndGroupQuery;
import io.holunda.polyflow.view.query.task.TasksForUserQuery;
import io.muenchendigital.digiwf.task.service.application.port.out.polyflow.TaskQueryPort;
import io.muenchendigital.digiwf.task.service.domain.PagingAndSorting;
import lombok.val;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

import static io.muenchendigital.digiwf.task.service.application.usecase.TestFixtures.generateTasks;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PolyflowTaskQueryAdapterTest {

  private final TaskQueryClient client = mock(TaskQueryClient.class);
  private final TaskQueryPort port = new PolyflowTaskQueryAdapter(client);

  private final User user = new User("0123456789", Sets.newHashSet("group1", "group2"));
  private final String query = "some";

  @Test
  void retrieves_tasks_for_user() {
    when(client.query(any(TasksForUserQuery.class))).thenReturn(CompletableFuture.completedFuture(
        new TaskQueryResult(
            generateTasks(17, Sets.newHashSet(), Sets.newHashSet(), "0123456789"),
            17
        )
    ));
    val result = port.getTasksForCurrentUser(
        user,
        query,
        null,
        new PagingAndSorting(
            0,
            100,
            null
        )
    );
    assertThat(result.getTasks()).hasSize(17);
    assertThat(result.getTotalElementsCount()).isEqualTo(17);
    verify(client).query(new TasksForUserQuery(
        new User(user.getUsername(), Sets.newHashSet()),
        true,
        0,
        100,
        "-createTime",
        Lists.newArrayList("task.textSearch%" + query)
    ));
  }

  @Test
  void retrieves_tasks_for_group() {
    when(client.query(any(TasksForCandidateUserAndGroupQuery.class))).thenReturn(CompletableFuture.completedFuture(
        new TaskQueryResult(
            generateTasks(17, Sets.newHashSet(), Sets.newHashSet(), "0123456789"),
            17
        )
    ));
    val result = port.getTasksForCurrentUserGroup(
        user,
        query,
        true,
        new PagingAndSorting(
            0,
            100,
            null
        )
    );
    assertThat(result.getTasks()).hasSize(17);
    assertThat(result.getTotalElementsCount()).isEqualTo(17);
    verify(client).query(new TasksForCandidateUserAndGroupQuery(
        user,
        true,
        0,
        100,
        "-createTime",
        Lists.newArrayList("task.textSearch%" + query)
    ));
  }

}
