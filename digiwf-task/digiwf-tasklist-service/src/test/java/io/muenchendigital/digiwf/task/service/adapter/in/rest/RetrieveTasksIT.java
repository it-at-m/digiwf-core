package io.muenchendigital.digiwf.task.service.adapter.in.rest;

import com.google.common.collect.Sets;
import io.holunda.polyflow.view.Task;
import io.holunda.polyflow.view.jpa.JpaPolyflowViewTaskService;
import io.holunda.polyflow.view.query.task.AllTasksQuery;
import io.muenchendigital.digiwf.task.service.TaskListApplication;
import io.muenchendigital.digiwf.task.service.adapter.out.user.MockUserGroupResolverAdapter;
import io.muenchendigital.digiwf.task.service.infra.security.TestUser;
import io.muenchendigital.digiwf.task.service.infra.security.WithKeycloakUser;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.messaging.MetaData;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static io.muenchendigital.digiwf.task.service.adapter.in.rest.RestConstants.BASE_PATH;
import static io.muenchendigital.digiwf.task.service.adapter.in.rest.RestConstants.SERVLET_PATH;
import static io.muenchendigital.digiwf.task.service.application.usecase.TestFixtures.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration test sending JSON request to the API and checking the correct invocation and
 * mapping to correct HTTP status.
 */
@SpringBootTest(
    classes = TaskListApplication.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ActiveProfiles({"itest", "embedded-kafka", "no-ldap"})
@AutoConfigureMockMvc(addFilters = false)
@EmbeddedKafka(
    partitions = 1,
    topics = {"plf_data_entries", "plf_tasks"}
)
@Slf4j
@DirtiesContext
public class RetrieveTasksIT {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private JpaPolyflowViewTaskService service;

  private final Task[] tasks = {
      // user id
      generateTask("task_0", Sets.newHashSet(), Sets.newHashSet(), TestUser.USER_ID, null, true),
      // candidate group
      generateTask("task_1", Sets.newHashSet(), Sets.newHashSet(MockUserGroupResolverAdapter.GROUP1, "ANOTHER"), "OTHER", null),
      // candidate user -> This is a special case, we don't expect candidate user assignment
      generateTask("task_2", Sets.newHashSet(TestUser.USER_ID), Sets.newHashSet(), "OTHER", null),
      // some white noise
      generateTask("task_3", Sets.newHashSet(), Sets.newHashSet(), "OTHER", null),
      generateTask("task_4", Sets.newHashSet(), Sets.newHashSet(MockUserGroupResolverAdapter.GROUP1), null, null),
  };


  @BeforeEach
  public void produce_task_events() {
    Arrays.stream(tasks).forEach(t -> service.on(createEvent(t), MetaData.emptyInstance()));
    await().untilAsserted(
        () -> {
          var count = service.query(new AllTasksQuery()).getTotalElementCount();
          assertThat(count).isEqualTo(tasks.length);
        }
    );
  }

  @AfterEach
  public void clean_tasks() {
    Arrays.stream(tasks).forEach(t -> service.on(deleteEvent(t), MetaData.emptyInstance()));
  }


  @Test
  @WithKeycloakUser
  public void retrieve_tasks_assigned_to_user() throws Exception {
    mockMvc
        .perform(
            get(BASE_PATH + "/tasks/user?sort=+taskId")
                .servletPath(SERVLET_PATH)
                .contentType(MediaType.APPLICATION_JSON)
        )
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content").isArray())
        .andExpect(jsonPath("$.content", hasSize(2)))
        .andExpect(jsonPath("$.totalPages", equalTo(1)))
        .andExpect(jsonPath("$.totalElements", equalTo(2)))
        .andExpect(jsonPath("$.numberOfElements", equalTo(2)))
        .andExpect(jsonPath("$.last", equalTo(true)))
        .andExpect(jsonPath("$.first", equalTo(true)))
        .andExpect(jsonPath("$.empty", equalTo(false)))
    ;
  }

  @Test
  @WithKeycloakUser
  public void retrieve_tasks_assigned_to_user_paged() throws Exception {
    mockMvc
        .perform(
            get(BASE_PATH + "/tasks/user?page=0&size=1&sort=+taskId")
                .servletPath(SERVLET_PATH)
                .contentType(MediaType.APPLICATION_JSON)
        )
        //.andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content").isArray())
        .andExpect(jsonPath("$.content", hasSize(1)))
        .andExpect(jsonPath("$.content[0].id", equalTo("task_0")))
        .andExpect(jsonPath("$.totalPages", equalTo(2)))
        .andExpect(jsonPath("$.totalElements", equalTo(2)))
        .andExpect(jsonPath("$.numberOfElements", equalTo(1)))
        .andExpect(jsonPath("$.last", equalTo(false)))
        .andExpect(jsonPath("$.first", equalTo(true)))
        .andExpect(jsonPath("$.empty", equalTo(false)))
    ;
    mockMvc
        .perform(
            get(BASE_PATH + "/tasks/user?page=1&size=1&sort=+taskId")
                .servletPath(SERVLET_PATH)
                .contentType(MediaType.APPLICATION_JSON)
        )
        //.andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content").isArray())
        .andExpect(jsonPath("$.content", hasSize(1)))
        .andExpect(jsonPath("$.content[0].id", equalTo("task_2")))
        .andExpect(jsonPath("$.totalPages", equalTo(2)))
        .andExpect(jsonPath("$.totalElements", equalTo(2)))
        .andExpect(jsonPath("$.numberOfElements", equalTo(1)))
        .andExpect(jsonPath("$.last", equalTo(true)))
        .andExpect(jsonPath("$.first", equalTo(false)))
        .andExpect(jsonPath("$.empty", equalTo(false)))
    ;

  }

  @Test
  @WithKeycloakUser
  public void retrieve_assigned_tasks_via_group() throws Exception {
    mockMvc
        .perform(
            get(BASE_PATH + "/tasks/group/assigned?sort=+taskId")
                .servletPath(SERVLET_PATH)
                .contentType(MediaType.APPLICATION_JSON)
        )
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content").isArray())
        .andExpect(jsonPath("$.content", hasSize(1)))
        .andExpect(jsonPath("$.content[0].id", equalTo("task_1")))
        .andExpect(jsonPath("$.totalPages", equalTo(1)))
        .andExpect(jsonPath("$.totalElements", equalTo(1)))
        .andExpect(jsonPath("$.numberOfElements", equalTo(1)))
        .andExpect(jsonPath("$.last", equalTo(true)))
        .andExpect(jsonPath("$.first", equalTo(true)))
        .andExpect(jsonPath("$.empty", equalTo(false)))
    ;
  }

  @Test
  @WithKeycloakUser
  public void retrieve_unassigned_tasks_via_group() throws Exception {
    mockMvc
        .perform(
            get(BASE_PATH + "/tasks/group/unassigned?sort=+taskId")
                .servletPath(SERVLET_PATH)
                .contentType(MediaType.APPLICATION_JSON)
        )
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content").isArray())
        .andExpect(jsonPath("$.content", hasSize(1)))
        .andExpect(jsonPath("$.content[0].id", equalTo("task_4")))
        .andExpect(jsonPath("$.totalPages", equalTo(1)))
        .andExpect(jsonPath("$.totalElements", equalTo(1)))
        .andExpect(jsonPath("$.numberOfElements", equalTo(1)))
        .andExpect(jsonPath("$.last", equalTo(true)))
        .andExpect(jsonPath("$.first", equalTo(true)))
        .andExpect(jsonPath("$.empty", equalTo(false)))
    ;
  }

}
