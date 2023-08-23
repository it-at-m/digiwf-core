package io.muenchendigital.digiwf.okewo.integration.repository;

import io.muenchendigital.digiwf.okewo.integration.exception.OkEwoIntegrationClientErrorException;
import io.muenchendigital.digiwf.okewo.integration.exception.OkEwoIntegrationException;
import io.muenchendigital.digiwf.okewo.integration.exception.OkEwoIntegrationServerErrorException;
import io.muenchendigital.digiwf.okewo.integration.gen.api.PersonApi;
import io.muenchendigital.digiwf.okewo.integration.gen.model.Person;
import io.muenchendigital.digiwf.okewo.integration.gen.model.SuchePersonAnfrage;
import io.muenchendigital.digiwf.okewo.integration.gen.model.SuchePersonAntwort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;

@Repository
@RequiredArgsConstructor
@Slf4j
public class OkEwoPersonRepository {

    private final PersonApi personApi;

    /**
     * Gets a {@link Person} by Ordnungsmerkmal.
     *
     * @param om         as Ordnungsmerkmal which identifies a {@link Person}.
     * @param benutzerId to identify the requesting application.
     * @return the {@link Person} identified by Ordnungsmerkmal.
     * @throws OkEwoIntegrationClientErrorException if the problem is with the client.
     * @throws OkEwoIntegrationServerErrorException if the problem is with OK.EWO.
     * @throws OkEwoIntegrationException            if the problem cannot be assigned directly to OK.EWO or client.
     */
    public Person getPerson(final String om, final String benutzerId) throws OkEwoIntegrationClientErrorException, OkEwoIntegrationServerErrorException, OkEwoIntegrationException {
        try {
            return this.personApi.deMuenchenEaiEwoRouteROUTEPROCESSGETPERSON(om, benutzerId);
        } catch (final HttpClientErrorException exception) {
            final String message = String.format("The request to get a person failed with %s.", exception.getStatusCode());
            log.error(exception.getMessage());
            log.error(message);
            throw new OkEwoIntegrationClientErrorException(message, exception);
        } catch (final HttpServerErrorException exception) {
            final String message = String.format("The request to get a person failed with %s.", exception.getStatusCode());
            log.error(exception.getMessage());
            log.error(message);
            throw new OkEwoIntegrationServerErrorException(message, exception);
        } catch (final RestClientException exception) {
            final String message = String.format("The request to get a person failed.");
            log.error(exception.getMessage());
            log.error(message);
            throw new OkEwoIntegrationException(message, exception);
        }
    }

    /**
     * Searches for {@link Person}s identified by {@link SuchePersonAnfrage}.
     *
     * @param suchePersonAnfrage to identify {@link Person}s.
     * @return the {@link SuchePersonAntwort} with the identified {@link Person}s.
     * @throws OkEwoIntegrationClientErrorException if the problem is with the client.
     * @throws OkEwoIntegrationServerErrorException if the problem is with OK.EWO.
     * @throws OkEwoIntegrationException            if the problem cannot be assigned directly to OK.EWO or client.
     */
    public SuchePersonAntwort searchPerson(final SuchePersonAnfrage suchePersonAnfrage) throws OkEwoIntegrationClientErrorException, OkEwoIntegrationServerErrorException, OkEwoIntegrationException {
        try {
            return this.personApi.deMuenchenEaiEwoRouteROUTEPROCESSSEARCHPERSON(suchePersonAnfrage);
        } catch (final HttpClientErrorException exception) {
            final String message = String.format("The request to search a person failed with %s.", exception.getStatusCode());
            log.error(exception.getMessage());
            log.error(message);
            throw new OkEwoIntegrationClientErrorException(message, exception);
        } catch (final HttpServerErrorException exception) {
            final String message = String.format("The request to search a person failed with %s.", exception.getStatusCode());
            log.error(exception.getMessage());
            log.error(message);
            throw new OkEwoIntegrationServerErrorException(message, exception);
        } catch (final RestClientException exception) {
            final String message = String.format("The request to search a person failed.");
            log.error(exception.getMessage());
            log.error(message);
            throw new OkEwoIntegrationException(message, exception);
        }
    }

}
