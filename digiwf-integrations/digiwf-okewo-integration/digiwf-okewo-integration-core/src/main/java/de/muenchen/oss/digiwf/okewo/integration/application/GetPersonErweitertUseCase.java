package de.muenchen.oss.digiwf.okewo.integration.application;

import de.muenchen.oss.digiwf.okewo.integration.application.in.GetPersonErweitertInPort;
import de.muenchen.oss.digiwf.okewo.integration.application.out.OkEwoClientOutPort;
import de.muenchen.oss.digiwf.okewo.integration.client.model.PersonErweitert;
import de.muenchen.oss.digiwf.okewo.integration.domain.exception.OkEwoIntegrationClientErrorException;
import de.muenchen.oss.digiwf.okewo.integration.properties.OkEwoIntegrationProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetPersonErweitertUseCase implements GetPersonErweitertInPort {
  private final OkEwoIntegrationProperties properties;
  private final OkEwoClientOutPort client;
  @Override
  public PersonErweitert getPerson(String om) throws OkEwoIntegrationClientErrorException {
    return client.getExtendedPerson(om, properties.getBenutzerId());
  }
}
