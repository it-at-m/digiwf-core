package de.muenchen.oss.digiwf.task.service.adapter.in.rest;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.google.common.collect.Sets;
import de.muenchen.oss.digiwf.task.TaskExternalReference;
import de.muenchen.oss.digiwf.task.TaskVariables;
import de.muenchen.oss.digiwf.task.service.TaskListApplication;
import de.muenchen.oss.digiwf.task.service.adapter.out.user.MockUserGroupResolverAdapter;
import de.muenchen.oss.digiwf.task.service.application.port.out.engine.TaskCommandPort;
import de.muenchen.oss.digiwf.task.service.infra.security.TestUser;
import de.muenchen.oss.digiwf.task.service.infra.security.WithKeycloakUser;
import io.holunda.camunda.bpm.data.CamundaBpmData;
import io.holunda.polyflow.view.Task;
import io.holunda.polyflow.view.jpa.JpaPolyflowViewTaskService;
import io.holunda.polyflow.view.query.task.AllTasksQuery;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.axonframework.messaging.MetaData;
import org.camunda.bpm.engine.variable.VariableMap;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

import static de.muenchen.oss.digiwf.task.service.application.usecase.TestFixtures.*;
import static java.util.stream.DoubleStream.builder;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration test sending JSON request to the API and checking the correct invocation and
 * mapping to correct HTTP status.
 */
@SpringBootTest(classes = TaskListApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles({"itest", "embedded-kafka"})
@AutoConfigureMockMvc(addFilters = false)
@EmbeddedKafka(
    partitions = 1,
    topics = {"${polyflow.axon.kafka.topic-tasks}", "${polyflow.axon.kafka.topic-data-entries}"}
)
@Slf4j
@WireMockTest(httpPort = 7080)
@DirtiesContext
public class TaskExternalLinksIT {

  private final Instant followUpDate = Instant.now().plus(2, ChronoUnit.DAYS);

  private final VariableMap variables = CamundaBpmData.builder()
      .set(TaskVariables.TASK_EXTERNAL_LINKS, Lists.newArrayList(
          new TaskExternalReference("url", "[München](https://www.muenchen.de/)"),
          new TaskExternalReference("zammad", "LHM11004832"),
          new TaskExternalReference("mucsdms", "[Vorgang 41134](COO.2150.307.2.41134)")
      ))
      .build();

  private final Task[] tasks = {
          // user id
          generateTask("task_0", Sets.newHashSet(), Sets.newHashSet(), TestUser.USER_ID, this.followUpDate, true, variables)
  };

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private JpaPolyflowViewTaskService service;

  @MockBean
  private TaskCommandPort taskCommandPort;

  @BeforeEach
  public void produce_task_events() {
    Arrays.stream(this.tasks).forEach(t -> this.service.on(createEvent(t), MetaData.emptyInstance()));
    await().untilAsserted(
        () -> {
          final var count = this.service.query(new AllTasksQuery()).getTotalElementCount();
          assertThat(count).isEqualTo(this.tasks.length);
        }
    );
  }

  @AfterEach
  public void clean_tasks() {
    Arrays.stream(this.tasks).forEach(t -> this.service.on(deleteEvent(t), MetaData.emptyInstance()));
  }


    @Test
    @WithKeycloakUser
    public void retrieve_task_with_details() throws Exception {
        WireMock.givenThat(
                WireMock.get(WireMock.urlEqualTo("/rest/jsonschema/schema-1"))
                        .willReturn(
                                WireMock.aResponse()
                                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                        .withBody(getJsonFromFile("json-schema-1.json"))
                        )
        );

        mockMvc
                .perform(
                        get(RestConstants.BASE_PATH + "/tasks/id/task_0")
                                .servletPath(RestConstants.SERVLET_PATH)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo("task_0")))
                .andExpect(jsonPath("$.schemaRef", equalTo("schema-1")))
        ;
    }
}
