package de.muenchen.oss.digiwf.okewo.integration.application.in;

import de.muenchen.oss.digiwf.okewo.integration.client.model.Person;
import de.muenchen.oss.digiwf.okewo.integration.domain.exception.OkEwoIntegrationClientErrorException;

public interface GetPersonInPort {

  Person getPerson(final String om) throws OkEwoIntegrationClientErrorException;
}
