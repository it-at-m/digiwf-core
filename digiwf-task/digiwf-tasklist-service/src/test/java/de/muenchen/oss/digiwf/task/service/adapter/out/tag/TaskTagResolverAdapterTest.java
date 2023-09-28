package de.muenchen.oss.digiwf.task.service.adapter.out.tag;

import de.muenchen.oss.digiwf.task.TaskVariables;
import de.muenchen.oss.digiwf.task.service.application.port.out.tag.TaskTagResolverPort;
import de.muenchen.oss.digiwf.task.service.application.usecase.TestFixtures;
import io.holunda.camunda.bpm.data.CamundaBpmData;
import io.holunda.polyflow.view.Task;
import org.camunda.bpm.engine.variable.VariableMap;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class TaskTagResolverAdapterTest {

private final TaskTagResolverPort taskTagResolverPort = new TaskTagResolverAdapter();

    @Test
    public void shouldReturnTagIfTagExists() {
        VariableMap variables = CamundaBpmData.builder()
                .set(TaskVariables.TASK_TAG, "task-tag-value")
                .build();
        Task task = TestFixtures.generateTask("task-id", Set.of("candidate"), Set.of("group1"), "candidate", Instant.now(), true, variables);
        String tag = taskTagResolverPort.apply(task).get();
        assertEquals("task-tag-value", tag);
    }
    @Test
    public void shouldReturnNullIfTagNotExists() {
        VariableMap variables = CamundaBpmData.builder()
                .build();
        Task task = TestFixtures.generateTask("task-id", Set.of("candidate"), Set.of("group1"), "candidate", Instant.now(), true, variables);
        Optional<String> tag = taskTagResolverPort.apply(task);
        assertTrue(tag.isEmpty());
    }
}
