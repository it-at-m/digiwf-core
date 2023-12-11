package de.muenchen.oss.digiwf.okewo.integration.application.in;

import de.muenchen.oss.digiwf.okewo.integration.client.model.SuchePersonAnfrage;
import de.muenchen.oss.digiwf.okewo.integration.client.model.SuchePersonAntwort;
import de.muenchen.oss.digiwf.okewo.integration.domain.exception.OkEwoIntegrationClientErrorException;

public interface SearchPersonInPort {
  SuchePersonAntwort searchPerson(final SuchePersonAnfrage request) throws OkEwoIntegrationClientErrorException;
}
