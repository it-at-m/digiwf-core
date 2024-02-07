package de.muenchen.oss.digiwf.task.service.application.usecase;

import com.google.common.collect.Sets;
import de.muenchen.oss.digiwf.task.TaskSchemaType;
import de.muenchen.oss.digiwf.task.TaskVariables;
import de.muenchen.oss.digiwf.task.service.adapter.out.link.OnTheFlyTaskLinkResolverAdapter;
import de.muenchen.oss.digiwf.task.service.adapter.out.link.TaskLinkConfigurationProperties;
import de.muenchen.oss.digiwf.task.service.adapter.out.schema.VariableTaskSchemaResolverAdapter;
import de.muenchen.oss.digiwf.task.service.adapter.out.schema.VariableTaskSchemaTypeResolverAdapter;
import de.muenchen.oss.digiwf.task.service.adapter.out.tag.TaskTagResolverAdapter;
import de.muenchen.oss.digiwf.task.service.application.port.in.RetrieveTasksForUser;
import de.muenchen.oss.digiwf.task.service.application.port.out.auth.CurrentUserPort;
import de.muenchen.oss.digiwf.task.service.application.port.out.cancellation.CancellationFlagOutPort;
import de.muenchen.oss.digiwf.task.service.application.port.out.links.TaskLinkResolverPort;
import de.muenchen.oss.digiwf.task.service.application.port.out.polyflow.TaskQueryPort;
import de.muenchen.oss.digiwf.task.service.application.port.out.schema.TaskSchemaRefResolverPort;
import de.muenchen.oss.digiwf.task.service.application.port.out.schema.TaskSchemaTypeResolverPort;
import de.muenchen.oss.digiwf.task.service.application.port.out.tag.TaskTagResolverPort;
import de.muenchen.oss.digiwf.task.service.domain.PageOfTasks;
import de.muenchen.oss.digiwf.task.service.domain.PagingAndSorting;
import io.holunda.camunda.bpm.data.CamundaBpmData;
import io.holunda.polyflow.view.auth.User;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class RetrieveTasksForUserUseCaseTest {

    private final TaskQueryPort taskQueryPort = mock(TaskQueryPort.class);
    private final CurrentUserPort currentUserPort = mock(CurrentUserPort.class);

    private final CancellationFlagOutPort cancellationFlagOutPort = mock(CancellationFlagOutPort.class);
    private final TaskSchemaRefResolverPort taskSchemaRefResolverPort = new VariableTaskSchemaResolverAdapter();
    private final TaskSchemaTypeResolverPort taskSchemaTypeResolverPort = new VariableTaskSchemaTypeResolverAdapter();
    private final TaskTagResolverPort taskTagResolverPort = new TaskTagResolverAdapter();
    private final TaskLinkResolverPort taskLinkResolverPort = new OnTheFlyTaskLinkResolverAdapter(
        new TaskLinkConfigurationProperties(Collections.emptyList())
    );

    private final RetrieveTasksForUser useCase = new RetrieveTasksForUserUseCase(
            taskQueryPort,
            currentUserPort,
            taskSchemaRefResolverPort,
            taskSchemaTypeResolverPort,
            cancellationFlagOutPort,
            taskTagResolverPort,
            taskLinkResolverPort
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
    void getsUnassignedTasksForCurrentUserGroupAndCandidate() {

        val content = TestFixtures.generateTasks(11, Sets.newHashSet(), Sets.newHashSet("group1"), null);
        content.addAll(TestFixtures.generateTasks(6, Sets.newHashSet("0123456789", "987654321"), Sets.newHashSet(), null));

        val pageOfTasks = new PageOfTasks(content, 17, pagingAndSorting);

        when(taskQueryPort.getTasksForCurrentUserGroup(any(), anyString(), any(), any(), anyBoolean(), any())).thenReturn(pageOfTasks);

        val tasks = useCase.getUnassignedTasksForCurrentUserGroup(query, null, pagingAndSorting);
        assertThat(tasks.getTotalElementsCount()).isEqualTo(17);
        verify(taskQueryPort).getTasksForCurrentUserGroup(user, query, null, null, false, pagingAndSorting);
        verifyNoMoreInteractions(taskQueryPort);
    }


    @Test
    void getsUnassignedTasksForCurrentUserGroup() {

        val pageOfTasks = new PageOfTasks(
                TestFixtures.generateTasks(17, Sets.newHashSet(), Sets.newHashSet("group1"), null),
                17,
                pagingAndSorting
        );

        when(taskQueryPort.getTasksForCurrentUserGroup(any(), anyString(), any(), any(), anyBoolean(), any())).thenReturn(pageOfTasks);

        val tasks = useCase.getUnassignedTasksForCurrentUserGroup(query, null, pagingAndSorting);
        assertThat(tasks.getTotalElementsCount()).isEqualTo(17);
        verify(taskQueryPort).getTasksForCurrentUserGroup(user, query, null, null,false, pagingAndSorting);
        verifyNoMoreInteractions(taskQueryPort);
    }

    @Test
    void getsAssignedTasksForCurrentUserGroup() {

        val pageOfTasks = new PageOfTasks(
                TestFixtures.generateTasks(17, Sets.newHashSet(), Sets.newHashSet("group1"), null),
                17,
                pagingAndSorting
        );

        when(taskQueryPort.getTasksForCurrentUserGroup(any(), anyString(), any(), any(), anyBoolean(), any())).thenReturn(pageOfTasks);

        val tasks = useCase.getAssignedTasksForCurrentUserGroup(query, null, null, pagingAndSorting);
        assertThat(tasks.getTotalElementsCount()).isEqualTo(17);
        verify(taskQueryPort).getTasksForCurrentUserGroup(user, query, null, null, true, pagingAndSorting);
        verifyNoMoreInteractions(taskQueryPort);
    }

    @Test
    void getsAssignedTasksForCurrentUserGroupAndAssignee() {


        val pageOfTasks = new PageOfTasks(
            TestFixtures.generateTasks(5, Sets.newHashSet(), Sets.newHashSet("group1"), "987654321"),
            5,
            pagingAndSorting
        );

        when(taskQueryPort.getTasksForCurrentUserGroup(any(), anyString(), any(), any(), anyBoolean(), any())).thenReturn(pageOfTasks);

        val tasks = useCase.getAssignedTasksForCurrentUserGroup(query, null, "987654321", pagingAndSorting);
        assertThat(tasks.getTotalElementsCount()).isEqualTo(5);
        verify(taskQueryPort).getTasksForCurrentUserGroup(user, query, null, "987654321", true, pagingAndSorting);
        verifyNoMoreInteractions(taskQueryPort);
    }

    @Test
    void getsTasksForCurrentUser() {

        val pageOfTasks = new PageOfTasks(
                TestFixtures.generateTasks(17, Sets.newHashSet(), Sets.newHashSet(), user.getUsername()),
                17,
                pagingAndSorting
        );

        when(taskQueryPort.getTasksForCurrentUser(any(), anyString(), any(), any(), any())).thenReturn(pageOfTasks);

        val tasks = useCase.getTasksForCurrentUser(query, null, null, pagingAndSorting);
        assertThat(tasks.getTotalElementsCount()).isEqualTo(17);
        verify(taskQueryPort).getTasksForCurrentUser(user, query, null, null, pagingAndSorting);
        verifyNoMoreInteractions(taskQueryPort);
    }

    @Test
    void getsLegacyAndSchemaBasedTasksForCurrentUser() {

        val allTasks = TestFixtures.generateTasks(15, Sets.newHashSet(), Sets.newHashSet(), user.getUsername());
        allTasks.add(TestFixtures.generateTask("task_15", Sets.newHashSet(), Sets.newHashSet(), user.getUsername(), null, true,
                CamundaBpmData.builder().set(TaskVariables.TASK_SCHEMA_TYPE, TaskSchemaType.VUETIFY_FORM_BASE).build())
        );
        allTasks.add(TestFixtures.generateTask("task_16", Sets.newHashSet(), Sets.newHashSet(), user.getUsername(), null, true,
                CamundaBpmData.builder().set(TaskVariables.TASK_SCHEMA_TYPE, TaskSchemaType.VUETIFY_FORM_BASE).build())
        );

        val pageOfTasks = new PageOfTasks(
                allTasks,
                17,
                pagingAndSorting
        );

        when(taskQueryPort.getTasksForCurrentUser(any(), anyString(), any(), any(), any())).thenReturn(pageOfTasks);

        val tasks = useCase.getTasksForCurrentUser(query, null, null, pagingAndSorting);
        assertThat(tasks.getTotalElementsCount()).isEqualTo(17);
        verify(taskQueryPort).getTasksForCurrentUser(user, query, null, null, pagingAndSorting);
        verifyNoMoreInteractions(taskQueryPort);
    }

}
