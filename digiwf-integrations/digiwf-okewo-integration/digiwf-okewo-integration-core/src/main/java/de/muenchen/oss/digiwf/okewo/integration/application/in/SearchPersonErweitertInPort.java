package de.muenchen.oss.digiwf.okewo.integration.application.in;

import de.muenchen.oss.digiwf.okewo.integration.client.model.SuchePersonerweitertAnfrage;
import de.muenchen.oss.digiwf.okewo.integration.client.model.SuchePersonerweitertAntwort;
import de.muenchen.oss.digiwf.okewo.integration.domain.exception.OkEwoIntegrationClientErrorException;

public interface SearchPersonErweitertInPort {

  SuchePersonerweitertAntwort searchPerson(final SuchePersonerweitertAnfrage request) throws OkEwoIntegrationClientErrorException;
}
