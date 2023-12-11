package de.muenchen.oss.digiwf.okewo.integration.adapter.out;

import de.muenchen.oss.digiwf.okewo.integration.client.api.PersonApi;
import de.muenchen.oss.digiwf.okewo.integration.client.api.PersonErweitertApi;
import de.muenchen.oss.digiwf.okewo.integration.client.model.*;
import de.muenchen.oss.digiwf.okewo.integration.domain.exception.OkEwoIntegrationClientErrorException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class OkEwoAdapterTest {

  private final PersonErweitertApi personErweitertApi = mock(PersonErweitertApi.class);
  private final PersonApi personApi = mock(PersonApi.class);
  private final OkEwoAdapter adapter = new OkEwoAdapter(
      personErweitertApi,
      personApi
  );

  private final WebClientResponseException notFoundException = new WebClientResponseException(HttpStatus.NOT_FOUND, "not found", null, null, null, null);
  private final WebClientResponseException badRequestException = new WebClientResponseException(HttpStatus.BAD_REQUEST, "bad request", null, null, null, null);

  @Test
  void getPerson_shouldReturnPersonWhenRequestWasSuccessfully() throws OkEwoIntegrationClientErrorException {
    final Person serverResponse = new Person().ordnungsmerkmal("om");
    when(personApi.deMuenchenEaiEwoRouteROUTEPROCESSGETPERSON("om", "benutzerId")).thenReturn(Mono.just(serverResponse));

    final Person result = adapter.getPerson("om", "benutzerId");
    assertEquals("om", result.getOrdnungsmerkmal());
  }

  @Test
  void getPerson_shouldThrowExceptionIfRequestFailed() {
    when(personApi.deMuenchenEaiEwoRouteROUTEPROCESSGETPERSON("om", "benutzerId")).thenThrow(notFoundException);

    final OkEwoIntegrationClientErrorException exception= assertThrows(OkEwoIntegrationClientErrorException.class, () -> {
      adapter.getPerson("om", "benutzerId");
    });

    assertTrue(exception.getMessage().contains("404"));
  }

  @Test
  void getExtendedPerson_shouldReturnPersonErweitertWhenRequestWasSuccessfully() throws OkEwoIntegrationClientErrorException {
    final PersonErweitert serverResponse = new PersonErweitert().ordnungsmerkmal("om");
    when(personErweitertApi.deMuenchenEaiEwoRouteROUTEPROCESSGETPERSONERWEITERT("om", "benutzerId")).thenReturn(Mono.just(serverResponse));

    final PersonErweitert result = adapter.getExtendedPerson("om", "benutzerId");
    assertEquals("om", result.getOrdnungsmerkmal());
  }

  @Test
  void getExtendedPerson_shouldThrowExceptionIfRequestFailed() {
    when(personErweitertApi.deMuenchenEaiEwoRouteROUTEPROCESSGETPERSONERWEITERT("om", "benutzerId")).thenThrow(notFoundException);

    final OkEwoIntegrationClientErrorException exception= assertThrows(OkEwoIntegrationClientErrorException.class, () -> {
      adapter.getExtendedPerson("om", "benutzerId");
    });

    assertTrue(exception.getMessage().contains("404"));
  }

  @Test
  void searchPerson_shouldReturnPersonErweitertWhenRequestWasSuccessfully() throws OkEwoIntegrationClientErrorException {
    final SuchePersonAnfrage request = new SuchePersonAnfrage();
    final Person person = new Person().ordnungsmerkmal("om");
    final SuchePersonAntwort serverResponse = new SuchePersonAntwort().addPersonenItem(person);
    when(personApi.deMuenchenEaiEwoRouteROUTEPROCESSSEARCHPERSON(request)).thenReturn(Mono.just(serverResponse));

    final SuchePersonAntwort result = adapter.searchPerson(request);
    assertEquals("om", result.getPersonen().get(0).getOrdnungsmerkmal());
  }

  @Test
  void searchPerson_shouldThrowExceptionIfRequestFailed() {
    final SuchePersonAnfrage request = new SuchePersonAnfrage();
    when(personApi.deMuenchenEaiEwoRouteROUTEPROCESSSEARCHPERSON(request)).thenThrow(badRequestException);

    final OkEwoIntegrationClientErrorException exception= assertThrows(OkEwoIntegrationClientErrorException.class, () -> {
      adapter.searchPerson(request);
    });

    assertTrue(exception.getMessage().contains("400"));
  }

  @Test
  void searchExtendedPerson() throws OkEwoIntegrationClientErrorException {
    final SuchePersonerweitertAnfrage request = new SuchePersonerweitertAnfrage();
    final PersonErweitert person = new PersonErweitert().ordnungsmerkmal("om");
    final SuchePersonerweitertAntwort response = new SuchePersonerweitertAntwort().addPersonenItem(person);
    when(personErweitertApi.deMuenchenEaiEwoRouteROUTEPROCESSSEARCHPERSONERWEITERT(request)).thenReturn(Mono.just(response));

    final SuchePersonerweitertAntwort result = adapter.searchExtendedPerson(request);
    assertEquals("om", result.getPersonen().get(0).getOrdnungsmerkmal());

  }

  @Test
  void searchExtendedPerson_shouldThrowExceptionIfRequestFailed() {
    SuchePersonerweitertAnfrage request = new SuchePersonerweitertAnfrage();
    when(personErweitertApi.deMuenchenEaiEwoRouteROUTEPROCESSSEARCHPERSONERWEITERT(request)).thenThrow(badRequestException);

    final OkEwoIntegrationClientErrorException exception= assertThrows(OkEwoIntegrationClientErrorException.class, () -> {

      adapter.searchExtendedPerson(request);
    });

    assertTrue(exception.getMessage().contains("400"));
  }
}
