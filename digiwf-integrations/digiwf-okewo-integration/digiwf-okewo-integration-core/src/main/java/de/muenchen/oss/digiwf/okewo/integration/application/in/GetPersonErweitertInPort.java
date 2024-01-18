package de.muenchen.oss.digiwf.okewo.integration.application.in;

import de.muenchen.oss.digiwf.okewo.integration.client.model.PersonErweitert;
import de.muenchen.oss.digiwf.okewo.integration.domain.exception.OkEwoIntegrationClientErrorException;

public interface GetPersonErweitertInPort {

  PersonErweitert getPerson(final String om) throws OkEwoIntegrationClientErrorException;
}
