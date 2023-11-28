package de.muenchen.oss.digiwf.okewo.integration.adapter.out;

import de.muenchen.oss.digiwf.okewo.integration.application.out.OkEwoClientOutPort;
import de.muenchen.oss.digiwf.okewo.integration.client.api.PersonApi;
import de.muenchen.oss.digiwf.okewo.integration.client.api.PersonErweitertApi;
import de.muenchen.oss.digiwf.okewo.integration.client.model.*;
import de.muenchen.oss.digiwf.okewo.integration.domain.exception.OkEwoIntegrationClientErrorException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Component
@AllArgsConstructor
public class OkEwoAdapter implements OkEwoClientOutPort {

  private PersonErweitertApi personErweitertApi;
  private PersonApi personApi;

  @Override
  public Person getPerson(String om, String userId) throws OkEwoIntegrationClientErrorException {
    try {
      return personApi.deMuenchenEaiEwoRouteROUTEPROCESSGETPERSON(om, userId).block();
    } catch (WebClientResponseException exception) {
      final String message = String.format("The request to get a person failed with %s.", exception.getStatusCode());
      throw new OkEwoIntegrationClientErrorException(message, exception);
    }
  }

  @Override
  public PersonErweitert getExtendedPerson(String om, String userId) throws OkEwoIntegrationClientErrorException {
    try {
      return personErweitertApi.deMuenchenEaiEwoRouteROUTEPROCESSGETPERSONERWEITERT(om, userId).block();
    } catch (WebClientResponseException exception) {
      final String message = String.format("The request to get a person failed with %s.", exception.getStatusCode());
      throw new OkEwoIntegrationClientErrorException(message, exception);
    }
  }

  @Override
  public SuchePersonAntwort searchPerson(SuchePersonAnfrage request) throws OkEwoIntegrationClientErrorException {
    try {
      return personApi.deMuenchenEaiEwoRouteROUTEPROCESSSEARCHPERSON(request).block();
    } catch (WebClientResponseException exception) {
      final String message = String.format("The request to get a person failed with %s.", exception.getStatusCode());
      throw new OkEwoIntegrationClientErrorException(message, exception);
    }
  }

  @Override
  public SuchePersonerweitertAntwort searchExtendedPerson(SuchePersonerweitertAnfrage request) throws OkEwoIntegrationClientErrorException {
    try {
      return personErweitertApi.deMuenchenEaiEwoRouteROUTEPROCESSSEARCHPERSONERWEITERT(request).block();
    } catch (WebClientResponseException exception) {
      final String message = String.format("The request to get a person failed with %s.", exception.getStatusCode());
      throw new OkEwoIntegrationClientErrorException(message, exception);
    }
  }
}
