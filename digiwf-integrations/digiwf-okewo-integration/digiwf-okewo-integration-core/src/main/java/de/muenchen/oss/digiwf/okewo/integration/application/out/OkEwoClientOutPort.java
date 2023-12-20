package de.muenchen.oss.digiwf.okewo.integration.application.out;


import de.muenchen.oss.digiwf.okewo.integration.client.model.*;
import de.muenchen.oss.digiwf.okewo.integration.domain.exception.OkEwoIntegrationClientErrorException;

/**
 * Port to okEwo System
 */
public interface OkEwoClientOutPort {

  Person getPerson(final String om, final String userId) throws OkEwoIntegrationClientErrorException;

  PersonErweitert getExtendedPerson(final String om, final String userId) throws OkEwoIntegrationClientErrorException;

  SuchePersonAntwort searchPerson(final SuchePersonAnfrage request) throws OkEwoIntegrationClientErrorException;

  SuchePersonerweitertAntwort searchExtendedPerson(SuchePersonerweitertAnfrage request) throws OkEwoIntegrationClientErrorException;
}
