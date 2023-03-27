package io.muenchendigital.digiwf.task.service.adapter.in.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static io.muenchendigital.digiwf.task.service.adapter.in.rest.RestConstants.BASE_PATH;
import static io.muenchendigital.digiwf.task.service.adapter.in.rest.RestConstants.SERVLET_PATH;
import static io.muenchendigital.digiwf.task.service.infra.security.ControllerAuthorizationHelper.mockUser;
import static io.muenchendigital.digiwf.task.service.infra.security.TestUser.JOHN_DOE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration test sending JSON request to the API and checking the correct invocation and
 * mapping to correct HTTP status.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("itest")
@AutoConfigureMockMvc(addFilters = false)
public class RetrieveTasksIT {

  private static final String BASE_URL = BASE_PATH + "/tasks";

  @Autowired
  private MockMvc mockMvc;

  @BeforeEach
  void setup() {
    mockUser(JOHN_DOE);
  }

  @Test
  public void retrieve_tasks_assigned_to_user() throws Exception {
    mockMvc.perform(get(BASE_URL).servletPath(SERVLET_PATH).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());

  }
}
