package io.muenchendigital.digiwf.task.service.adapter.in.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.holunda.polyflow.view.jpa.JpaPolyflowViewTaskService;
import io.holunda.polyflow.view.query.task.AllTasksQuery;
import io.muenchendigital.digiwf.task.service.TaskListApplication;
import io.muenchendigital.digiwf.task.service.infra.security.TestUser;
import io.muenchendigital.digiwf.task.service.infra.security.WithKeycloakUser;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static io.muenchendigital.digiwf.task.service.adapter.in.rest.RestConstants.BASE_PATH;
import static io.muenchendigital.digiwf.task.service.adapter.in.rest.RestConstants.SERVLET_PATH;
import static io.muenchendigital.digiwf.task.service.application.usecase.TestFixtures.createEvent;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.awaitility.Awaitility.await;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration test sending JSON request to the API and checking the correct invocation and
 * mapping to correct HTTP status.
 */
@SpringBootTest(classes = TaskListApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles({"itest", "embedded-kafka"})
@AutoConfigureMockMvc(addFilters = false)
@WithKeycloakUser
@EmbeddedKafka(
    partitions = 1,
    count = 1,
    topics = {"plf_data_entries", "plf_tasks"},
    ports = {9092}
)
public class RetrieveTasksIT {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private JpaPolyflowViewTaskService service;


  @BeforeEach
  public void produce_task_events() throws JsonProcessingException {
//    template.send(new ProducerRecord<>("plf_task", objectMapper.writeValueAsBytes(createEvent("task_0", TestUser.USER_ID))));
//    template.send(new ProducerRecord<>("plf_task", objectMapper.writeValueAsBytes(createEvent("task_1", TestUser.USER_ID))));
//    template.send(new ProducerRecord<>("plf_task", objectMapper.writeValueAsBytes(createEvent("task_2", TestUser.USER_ID))));
//    await().untilAsserted(
//        () -> {
//          assertThat(service.query(new AllTasksQuery()).getTotalElementCount()).isNotZero();
//        }
//    );

  }


  @Test
  public void retrieve_tasks_assigned_to_user() throws Exception {
    mockMvc
        .perform(
            get(BASE_PATH + "/tasks/user")
                .servletPath(SERVLET_PATH)
                .contentType(MediaType.APPLICATION_JSON)
        )
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().json("{\"content\":[],\"pageable\":{\"pageNumber\":0,\"pageSize\":50,\"paged\":true,\"unpaged\":false,\"sort\":{\"empty\":true,\"unsorted\":true,\"sorted\":false}},\"totalPages\":0,\"totalElements\":0,\"numberOfElements\":0,\"size\":50,\"page\":0,\"last\":false,\"first\":true,\"empty\":true}"))
    ;
  }

}
