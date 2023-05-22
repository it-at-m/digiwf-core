package io.muenchendigital.digiwf.task.service.adapter.out.file;


import com.google.common.collect.Sets;
import io.holunda.camunda.bpm.data.CamundaBpmData;
import io.holunda.polyflow.view.auth.User;
import io.muenchendigital.digiwf.task.service.application.port.out.auth.CurrentUserPort;
import io.muenchendigital.digiwf.task.service.application.port.out.file.TaskFileConfigResolverPort;
import io.muenchendigital.digiwf.task.service.domain.TaskFileConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import io.holunda.polyflow.view.Task;

import java.util.Collections;

import static io.muenchendigital.digiwf.task.TaskVariables.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import static io.muenchendigital.digiwf.task.service.application.usecase.TestFixtures.*;


class TaskFileConfigResolverAdapterTest {

    private final User user = new User("0123456789", Sets.newHashSet("group1", "group2"));
    private final TaskFileConfigResolverPort configResolverPort = new TaskFileConfigResolverAdapter();
    private final CurrentUserPort currentUserPort = mock(CurrentUserPort.class);

    @BeforeEach
    void setupMocks() {
        when(currentUserPort.getCurrentUser()).thenReturn(user);
    }

    @Test
    void getTaskFileConfigAllVariables() {
        Task task = generateTask("task_0", Collections.emptySet(), Collections.emptySet(), user.getUsername(), null, false,
                CamundaBpmData
                        .builder()
                        .set(PROCESS_FILE_CONTEXT, "File context")
                        .set(PROCESS_ASYNC_CONFIG, "Async config")
                        .set(PROCESS_SYNC_CONFIG, "Sync config")
                        .set(FILE_PATHS, "File;paths")
                        .set(FILE_PATHS_READONLY, "File;paths;read;only")
                        .build()
        );

        TaskFileConfig config = configResolverPort.apply(task);

        assertEquals("File context", config.processFileContext);
        assertEquals("Async config", config.processAsyncConfig);
        assertEquals("Sync config", config.processSyncConfig);
        assertEquals(2, config.filePaths.size());
        assertEquals(4, config.filePathsReadonly.size());
        assertTrue(config.filePaths.contains("File"));
        assertTrue(config.filePathsReadonly.contains("read"));
    }

    @Test
    void getTaskFileConfigNotAllVariables() {
        Task task = generateTask("task_0", Collections.emptySet(), Collections.emptySet(), user.getUsername(), null, false,
                CamundaBpmData
                        .builder()
                        .set(PROCESS_FILE_CONTEXT, "File context")
                        .build()
        );

        TaskFileConfig config = configResolverPort.apply(task);

        assertEquals("File context", config.processFileContext);
        assertNull(config.processAsyncConfig);
        assertNull(config.processSyncConfig);
        assertTrue(config.filePaths.isEmpty());
        assertTrue(config.filePathsReadonly.isEmpty());
    }

    @Test
    void getNoFileContextException() {
        Task task = generateTask("task_0", Collections.emptySet(), Collections.emptySet(), user.getUsername(), null, false,
                CamundaBpmData
                        .builder()
                        .set(PROCESS_ASYNC_CONFIG, "Async config")
                        .set(PROCESS_SYNC_CONFIG, "Sync config")
                        .set(FILE_PATHS, "File paths")
                        .set(FILE_PATHS_READONLY, "File paths read only")
                        .build()
        );

        Exception exception = assertThrows(NoFileContextException.class, () -> { configResolverPort.apply(task);});

        String expectedMessage = "No file context found for task";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);

    }
}