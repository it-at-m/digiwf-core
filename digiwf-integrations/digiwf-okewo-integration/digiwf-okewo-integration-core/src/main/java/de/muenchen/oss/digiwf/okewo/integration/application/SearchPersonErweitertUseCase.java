package de.muenchen.oss.digiwf.okewo.integration.application;

import de.muenchen.oss.digiwf.okewo.integration.application.in.SearchPersonErweitertInPort;
import de.muenchen.oss.digiwf.okewo.integration.application.out.OkEwoClientOutPort;
import de.muenchen.oss.digiwf.okewo.integration.client.model.SuchePersonerweitertAnfrage;
import de.muenchen.oss.digiwf.okewo.integration.client.model.SuchePersonerweitertAntwort;
import de.muenchen.oss.digiwf.okewo.integration.domain.exception.OkEwoIntegrationClientErrorException;
import de.muenchen.oss.digiwf.okewo.integration.properties.OkEwoIntegrationProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SearchPersonErweitertUseCase implements SearchPersonErweitertInPort {
  private final OkEwoClientOutPort client;
  @Override
  public SuchePersonerweitertAntwort searchPerson(SuchePersonerweitertAnfrage request) throws OkEwoIntegrationClientErrorException {
    return client.searchExtendedPerson(request);
  }
}
