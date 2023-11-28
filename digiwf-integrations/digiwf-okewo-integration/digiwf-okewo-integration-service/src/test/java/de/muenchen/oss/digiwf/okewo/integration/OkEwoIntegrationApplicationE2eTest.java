package de.muenchen.oss.digiwf.okewo.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import de.muenchen.oss.digiwf.okewo.integration.client.model.Person;
import de.muenchen.oss.digiwf.okewo.integration.domain.model.request.OkEwoEventRequest;
import de.muenchen.oss.digiwf.okewo.integration.domain.model.request.OrdnungsmerkmalDto;
import de.muenchen.oss.digiwf.okewo.integration.utility.DigiWFIntegrationE2eTest;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("itest")
@DirtiesContext
@EmbeddedKafka(partitions = 1,
    brokerProperties = {"listeners=PLAINTEXT://localhost:29092"},
    topics = {
        "${spring.cloud.stream.bindings.functionRouter-in-0.destination}",
        "${spring.cloud.stream.bindings.sendMessage-out-0.destination}",
        "${spring.cloud.stream.bindings.integrationTestConsumer-in-0.destination}"
    })
@WireMockTest(httpPort = 8089)
class OkEwoIntegrationApplicationE2eTest extends DigiWFIntegrationE2eTest {


  @Autowired
  private ObjectMapper objectMapper;

  private String processInstanceId;

  @BeforeEach
  void setup() {
    this.processInstanceId = UUID.randomUUID().toString();
  }

  @Test
  public void shouldStart() {
    // test fails if application context can not start
  }

  @Test
  void shouldProcessGetPersonEvent() throws InterruptedException, JsonProcessingException {
    val ordnungsmerkmal = new OrdnungsmerkmalDto();
    ordnungsmerkmal.setOrdnungsmerkmal("om");
    val request = new OkEwoEventRequest<OrdnungsmerkmalDto>();
    request.setRequest(ordnungsmerkmal);

    val person = new Person().ordnungsmerkmal("om");
//    http://localhost:8089/personen/2.0/rest
//    http://localhost:8089/personen/2.0/rest/person/om?benutzerId=benutzerId
    this.setupWiremock("/personen/2.0/rest/person/om?benutzerId=benutzerId", objectMapper.writeValueAsString(person));

    // send and receive messages
    final Map<String, Object> payload = super.runIntegration(request, processInstanceId, "getPerson");
    assertNotNull(payload);

  }

  private void setupWiremock(final String url, final String expectedResponse) {
    WireMock.stubFor(WireMock
        .get(url)
        .willReturn(WireMock
            .aResponse()
            .withBody(expectedResponse)
            .withHeader("Content-Type", "application/json")
            .withStatus(200)));
  }
}
