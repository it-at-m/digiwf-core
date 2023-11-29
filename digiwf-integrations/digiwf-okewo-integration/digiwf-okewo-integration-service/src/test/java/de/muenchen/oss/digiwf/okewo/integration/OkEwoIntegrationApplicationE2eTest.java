package de.muenchen.oss.digiwf.okewo.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import de.muenchen.oss.digiwf.okewo.integration.client.model.*;
import de.muenchen.oss.digiwf.okewo.integration.domain.model.request.OkEwoOmBasedRequest;
import de.muenchen.oss.digiwf.okewo.integration.domain.model.request.OkEwoSearchPersonExtendedRequest;
import de.muenchen.oss.digiwf.okewo.integration.domain.model.request.OkEwoSearchPersonRequest;
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

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.github.tomakehurst.wiremock.client.WireMock.equalToJson;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
    val request = new OkEwoOmBasedRequest();
    request.setRequest(ordnungsmerkmal);

    val person = new Person().ordnungsmerkmal("om");
    this.setupGetPathOfWiremock("/personen/2.0/rest/person/om?benutzerId=benutzerId", "username", "password", objectMapper.writeValueAsString(person));

    // send and receive messages
    final Map<String, Object> payload = super.runIntegration(request, processInstanceId, "getPerson");
    assertNotNull(payload);
    final Map<String, Object> response = (Map<String, Object>) payload.get("response");
    assertNotNull(response);
    assertEquals("om", response.get("ordnungsmerkmal"));

  }

  @Test
  void shouldProcessSearchPersonEvent() throws InterruptedException, JsonProcessingException {
    val ordnungsmerkmal = new OrdnungsmerkmalDto();
    ordnungsmerkmal.setOrdnungsmerkmal("om");
    val request = new OkEwoSearchPersonRequest();
    val searchRequest = new SuchePersonAnfrage();
    searchRequest.setBenutzer(new BenutzerType().benutzerId("benutzerId"));
    request.setRequest(searchRequest);
    val person = new Person().ordnungsmerkmal("om");
    val apiResponse = new SuchePersonAntwort().personen(List.of(person));

    this.setupPostPathOfWiremock("/personen/2.0/rest/person/search", objectMapper.writeValueAsString(searchRequest), "username", "password", objectMapper.writeValueAsString(apiResponse));

    // send and receive messages
    final Map<String, Object> payload = super.runIntegration(request, processInstanceId, "searchPerson");
    assertNotNull(payload);
    final Map<String, Object> response = (Map<String, Object>) payload.get("response");
    assertNotNull(response);
    final List<Person> personenResponse = (List<Person>) response.get("personen");
    assertEquals(1, personenResponse.size());
  }

  @Test
  void shouldProcessGetPersonErweitertEvent() throws InterruptedException, JsonProcessingException {
    val ordnungsmerkmal = new OrdnungsmerkmalDto();
    ordnungsmerkmal.setOrdnungsmerkmal("om");
    val request = new OkEwoOmBasedRequest();
    request.setRequest(ordnungsmerkmal);

    val person = new PersonErweitert().ordnungsmerkmal("om");
    this.setupGetPathOfWiremock("/personen/2.0/rest/personErweitert/om?benutzerId=benutzerId", "username", "password", objectMapper.writeValueAsString(person));

    // send and receive messages
    final Map<String, Object> payload = super.runIntegration(request, processInstanceId, "getPersonErweitert");
    assertNotNull(payload);
    final Map<String, Object> response = (Map<String, Object>) payload.get("response");
    assertNotNull(response);
    assertEquals("om", response.get("ordnungsmerkmal"));

  }

  @Test
  void shouldProcessSearchPersonErweitertEvent() throws InterruptedException, JsonProcessingException {
    val ordnungsmerkmal = new OrdnungsmerkmalDto();
    ordnungsmerkmal.setOrdnungsmerkmal("om");
    val request = new OkEwoSearchPersonExtendedRequest();
    val searchRequest = new SuchePersonerweitertAnfrage();
    searchRequest.setBenutzer(new BenutzerType().benutzerId("benutzerId"));
    request.setRequest(searchRequest);
    val person = new PersonErweitert().ordnungsmerkmal("om");
    val apiResponse = new SuchePersonerweitertAntwort().personen(List.of(person));

    this.setupPostPathOfWiremock("/personen/2.0/rest/personErweitert/search", objectMapper.writeValueAsString(searchRequest), "username", "password", objectMapper.writeValueAsString(apiResponse));

    // send and receive messages
    final Map<String, Object> payload = super.runIntegration(request, processInstanceId, "searchPersonErweitert");
    assertNotNull(payload);
    final Map<String, Object> response = (Map<String, Object>) payload.get("response");
    assertNotNull(response);
    final List<Person> personenResponse = (List<Person>) response.get("personen");
    assertEquals(1, personenResponse.size());
  }

  private void setupGetPathOfWiremock(final String url, final String username, final String password, final String expectedResponse) {
    WireMock.stubFor(WireMock
        .get(url)
        .withBasicAuth(username, password)
        .willReturn(WireMock
            .aResponse()
            .withBody(expectedResponse)
            .withHeader("Content-Type", "application/json")
            .withStatus(200)));
  }

  private void setupPostPathOfWiremock(final String url, final String requestBody, final String username, final String password, final String expectedResponse) {
    WireMock.stubFor(WireMock
        .post(url)
        .withRequestBody(equalToJson(requestBody))
        .withBasicAuth(username, password)
        .willReturn(WireMock
            .aResponse()
            .withBody(expectedResponse)
            .withHeader("Content-Type", "application/json")
            .withStatus(200)));
  }
}
