package de.muenchen.oss.digiwf.okewo.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import de.muenchen.oss.digiwf.integration.e2e.test.DigiwfE2eTest;
import de.muenchen.oss.digiwf.integration.e2e.test.DigiwfIntegrationE2eTestUtility;
import de.muenchen.oss.digiwf.integration.e2e.test.DigiwfWiremockUtility;
import de.muenchen.oss.digiwf.okewo.integration.client.model.*;
import de.muenchen.oss.digiwf.okewo.integration.domain.model.request.OkEwoOmBasedRequest;
import de.muenchen.oss.digiwf.okewo.integration.domain.model.request.OkEwoSearchPersonExtendedRequest;
import de.muenchen.oss.digiwf.okewo.integration.domain.model.request.OkEwoSearchPersonRequest;
import de.muenchen.oss.digiwf.okewo.integration.domain.model.request.OrdnungsmerkmalDto;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DigiwfE2eTest
@WireMockTest(httpPort = 8189)
class OkEwoIntegrationApplicationE2eTest {


  @Autowired
  private ObjectMapper objectMapper;
  @Autowired
  private DigiwfIntegrationE2eTestUtility digiWFIntegrationE2eTestUtility;

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
  void shouldProcessGetPersonEvent() throws JsonProcessingException {
    val ordnungsmerkmal = new OrdnungsmerkmalDto();
    ordnungsmerkmal.setOrdnungsmerkmal("om");
    val request = new OkEwoOmBasedRequest();
    request.setRequest(ordnungsmerkmal);

    val person = new Person().ordnungsmerkmal("om");
    DigiwfWiremockUtility.setupGETWithBasicAuth("/personen/2.0/rest/person/om?benutzerId=benutzerId", "username", "password", objectMapper.writeValueAsString(person));

    // send and receive messages
    final Map<String, Object> payload = digiWFIntegrationE2eTestUtility.runIntegration(request, processInstanceId, "getPerson");
    assertNotNull(payload);
    final Map<String, Object> response = (Map<String, Object>) payload.get("response");
    assertNotNull(response);
    assertEquals("om", response.get("ordnungsmerkmal"));

  }

  @Test
  void shouldProcessSearchPersonEvent() throws JsonProcessingException {
    val ordnungsmerkmal = new OrdnungsmerkmalDto();
    ordnungsmerkmal.setOrdnungsmerkmal("om");
    val request = new OkEwoSearchPersonRequest();
    val searchRequest = new SuchePersonAnfrage();
    searchRequest.setBenutzer(new BenutzerType().benutzerId("benutzerId"));
    request.setRequest(searchRequest);
    val person = new Person().ordnungsmerkmal("om");
    val apiResponse = new SuchePersonAntwort().personen(List.of(person));

    DigiwfWiremockUtility.setupPOSTWithBasicAuth("/personen/2.0/rest/person/search", objectMapper.writeValueAsString(searchRequest), "username", "password", objectMapper.writeValueAsString(apiResponse));

    // send and receive messages
    final Map<String, Object> payload = digiWFIntegrationE2eTestUtility.runIntegration(request, processInstanceId, "searchPerson");
    assertNotNull(payload);
    final Map<String, Object> response = (Map<String, Object>) payload.get("response");
    assertNotNull(response);
    final List<Person> personenResponse = (List<Person>) response.get("personen");
    assertEquals(1, personenResponse.size());
  }

  @Test
  void shouldProcessGetPersonErweitertEvent() throws JsonProcessingException {
    val ordnungsmerkmal = new OrdnungsmerkmalDto();
    ordnungsmerkmal.setOrdnungsmerkmal("om");
    val request = new OkEwoOmBasedRequest();
    request.setRequest(ordnungsmerkmal);

    val person = new PersonErweitert().ordnungsmerkmal("om");
    DigiwfWiremockUtility.setupGETWithBasicAuth("/personen/2.0/rest/personErweitert/om?benutzerId=benutzerId", "username", "password", objectMapper.writeValueAsString(person));

    // send and receive messages
    final Map<String, Object> payload = digiWFIntegrationE2eTestUtility.runIntegration(request, processInstanceId, "getPersonErweitert");
    assertNotNull(payload);
    final Map<String, Object> response = (Map<String, Object>) payload.get("response");
    assertNotNull(response);
    assertEquals("om", response.get("ordnungsmerkmal"));

  }

  @Test
  void shouldProcessSearchPersonErweitertEvent() throws JsonProcessingException {
    val ordnungsmerkmal = new OrdnungsmerkmalDto();
    ordnungsmerkmal.setOrdnungsmerkmal("om");
    val request = new OkEwoSearchPersonExtendedRequest();
    val searchRequest = new SuchePersonerweitertAnfrage();
    searchRequest.setBenutzer(new BenutzerType().benutzerId("benutzerId"));
    request.setRequest(searchRequest);
    val person = new PersonErweitert().ordnungsmerkmal("om");
    val apiResponse = new SuchePersonerweitertAntwort().personen(List.of(person));

    DigiwfWiremockUtility.setupPOSTWithBasicAuth("/personen/2.0/rest/personErweitert/search", objectMapper.writeValueAsString(searchRequest), "username", "password", objectMapper.writeValueAsString(apiResponse));

    // send and receive messages
    final Map<String, Object> payload = digiWFIntegrationE2eTestUtility.runIntegration(request, processInstanceId, "searchPersonErweitert");
    assertNotNull(payload);
    final Map<String, Object> response = (Map<String, Object>) payload.get("response");
    assertNotNull(response);
    final List<Person> personenResponse = (List<Person>) response.get("personen");
    assertEquals(1, personenResponse.size());
  }

}
