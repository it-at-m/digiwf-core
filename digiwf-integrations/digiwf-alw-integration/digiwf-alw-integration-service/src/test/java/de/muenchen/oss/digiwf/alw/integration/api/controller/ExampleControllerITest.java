package de.muenchen.oss.digiwf.alw.integration.api.controller;

import de.muenchen.oss.digiwf.alw.integration.AwlServiceApplication;
import de.muenchen.oss.digiwf.alw.integration.adapter.out.alw.AlwResponsibilityRestAdapter;
import de.muenchen.oss.digiwf.spring.security.autoconfiguration.SpringSecurityAutoConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = {
    AwlServiceApplication.class
},
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@EnableAutoConfiguration(exclude = SpringSecurityAutoConfiguration.class)
@ActiveProfiles({"mocked-alw-service", "itest",})
class ExampleControllerITest {

  @Autowired
  private WebApplicationContext webApplicationContext;
  private MockMvc mockMvc;

  @SpyBean
  private AlwResponsibilityRestAdapter adapter;

  @BeforeEach
  public void setup() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();

  }

  @Test
  void find_via_rest() throws Exception {
    mockMvc
        .perform(
            get("/getAlwZustaendigkeit/{azrNumber}", "123456789012")
                .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(content().contentType("text/plain;charset=UTF-8"))
        .andExpect(content().string("OU1"));

    verify(adapter).getResponsibleSachbearbeiter("123456789012");
  }

  @Test
  void dont_find_via_rest() throws Exception {
    mockMvc
        .perform(
            get("/getAlwZustaendigkeit/{azrNumber}", "098765432109")
                .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isInternalServerError());
    verify(adapter).getResponsibleSachbearbeiter("098765432109");
  }

}
