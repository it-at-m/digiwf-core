package de.muenchen.oss.digiwf.task.service.adapter.out.link;

import de.muenchen.oss.digiwf.task.TaskExternalReference;
import de.muenchen.oss.digiwf.task.TaskVariables;
import de.muenchen.oss.digiwf.task.service.application.port.out.links.TaskLinkResolverPort;
import de.muenchen.oss.digiwf.task.service.application.usecase.TestFixtures;
import de.muenchen.oss.digiwf.task.service.domain.TaskLink;
import io.holunda.camunda.bpm.data.CamundaBpmData;
import io.holunda.polyflow.view.Task;
import lombok.val;
import org.assertj.core.util.Lists;
import org.camunda.bpm.engine.variable.VariableMap;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class TaskLinkResolverAdapterTest {

  private final TaskLinkResolverPort port = new TaskLinkResolverAdapter();

  @Test
  public void should_deliver_empty_list_for_no_links_or_unknown_links() {
    VariableMap variables = CamundaBpmData.builder()
        .set(TaskVariables.TASK_EXTERNAL_LINKS,
            Lists.newArrayList(
                new TaskExternalReference("unknown", "what-ever")
            )
        )
        .build();
    Task task = TestFixtures.generateTask("task-id", Set.of("candidate"),
        Set.of("group1"), "candidate", Instant.now(), true, variables);


    val links = port.apply(task);
    assertThat(links).isEmpty();
  }

  @Test
  public void should_deliver_url_links() {
    VariableMap variables = CamundaBpmData.builder()
        .set(TaskVariables.TASK_EXTERNAL_LINKS,
            Lists.newArrayList(
                new TaskExternalReference("url", "[Labeled](https://localhost/with-label)"),
                new TaskExternalReference("url", "https://localhost/without-label")
            )
        )
        .build();
    Task task = TestFixtures.generateTask("task-id", Set.of("candidate"),
        Set.of("group1"), "candidate", Instant.now(), true, variables);


    val links = port.apply(task);
    assertThat(links).hasSize(2);
    assertThat(links).containsExactlyInAnyOrder(
        new TaskLink("url", "https://localhost/with-label", "Labeled", null, null),
        new TaskLink("url", "https://localhost/without-label", null, null, null)
    );
  }

}