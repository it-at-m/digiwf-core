package de.muenchen.oss.digiwf.task.service.adapter.out.link;

import de.muenchen.oss.digiwf.task.TaskExternalReference;
import de.muenchen.oss.digiwf.task.TaskVariables;
import de.muenchen.oss.digiwf.task.service.application.usecase.TestFixtures;
import de.muenchen.oss.digiwf.task.service.domain.TaskLink;
import io.holunda.camunda.bpm.data.CamundaBpmData;
import io.holunda.polyflow.view.Task;
import lombok.val;
import org.assertj.core.util.Lists;
import org.camunda.bpm.engine.variable.VariableMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OnTheFlyTaskLinkResolverAdapterTest {

    private static final String ZAMMAD_URL = "https://mpdz-ticketing-prelive.muenchen.de/#ticket/zoom/number/";
    private static final String EAKTE_URL = "https://eakte.muenchen.de/fsc/mx/";

    private final OnTheFlyTaskLinkResolverAdapter adapter = new OnTheFlyTaskLinkResolverAdapter(
        new TaskLinkConfigurationProperties(
            Lists.list(
                new TaskLinkTypeConfig(
                    "zammad", ZAMMAD_URL + "{0}", "Zammad Ticket {0}", "^(?>LHM)?(?<id>[\\d]+)$" // [Foo](LHM11004832) oder LHM11004832
                ),
                new TaskLinkTypeConfig(
                    "mucsdms", EAKTE_URL + "{0}", "MUCS DMS {0}", null // [Vorgang 4712](COO.2150.307.2.41134) oder COO.2150.307.2.41134
                )
            )
        )
    );

    @BeforeEach
    public void init() {
        adapter.initConfiguration();
    }

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


        val links = adapter.apply(task);
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


        val links = adapter.apply(task);
        assertThat(links).hasSize(2);
        assertThat(links).containsExactlyInAnyOrder(
            new TaskLink("url", "https://localhost/with-label", "Labeled", null, null),
            new TaskLink("url", "https://localhost/without-label", null, null, null)
        );
    }

    @Test
    public void should_deliver_typed_links() {
        VariableMap variables = CamundaBpmData.builder()
            .set(TaskVariables.TASK_EXTERNAL_LINKS,
                Lists.newArrayList(
                    new TaskExternalReference("zammad", "[Labeled Zammad Ticket](LHM11004832)"),
                    new TaskExternalReference("zammad", "LHM11004832"),
                    new TaskExternalReference("mucsdms", "[Vorgang 41134](COO.2150.307.2.41134)"),
                    new TaskExternalReference("mucsdms", "COO.2150.307.2.41134")
                )
            )
            .build();
        Task task = TestFixtures.generateTask("task-id", Set.of("candidate"),
            Set.of("group1"), "candidate", Instant.now(), true, variables);


        val links = adapter.apply(task);

        assertThat(links).hasSize(4);
        assertThat(links).containsExactlyInAnyOrder(
            new TaskLink("zammad", ZAMMAD_URL + "11004832", "Labeled Zammad Ticket", null, null),
            new TaskLink("zammad", ZAMMAD_URL + "11004832", "Zammad Ticket 11004832", null, null),
            new TaskLink("mucsdms", EAKTE_URL + "COO.2150.307.2.41134", "Vorgang 41134", null, null),
            new TaskLink("mucsdms", EAKTE_URL + "COO.2150.307.2.41134", "MUCS DMS COO.2150.307.2.41134", null, null)
        );
    }

    @Test
    public void should_sanitize_config() {
        assertThatThrownBy(() -> {
            new OnTheFlyTaskLinkResolverAdapter(
                new TaskLinkConfigurationProperties(
                    Lists.list(
                        new TaskLinkTypeConfig(
                            "error1", "some", "", "^(?>non-cap)?(?<wrongname>[\\d]+)$"
                        )
                    )
                )
            ).initConfiguration();
        }).isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Wrong configuration of TaskLinkResolver. The link type config for error1 contains the pattern ^(?>non-cap)?(?<wrongname>[\\d]+)$ which must contained named capturing group 'id', but it was not.");

        assertThatThrownBy(() -> {
            new OnTheFlyTaskLinkResolverAdapter(
                new TaskLinkConfigurationProperties(
                    Lists.list(
                        new TaskLinkTypeConfig(
                            "error2", "some", "", "^(?>non-cap)?([\\d]+)$" // no named group
                        )
                    )
                )
            ).initConfiguration();
        }).isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Wrong configuration of TaskLinkResolver. The link type config for error2 contains the pattern ^(?>non-cap)?([\\d]+)$ which must contained named capturing group 'id', but it was not.");
    }

}
