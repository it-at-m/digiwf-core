package io.muenchendigital.digiwf.okewo.integration.repository;

import io.muenchendigital.digiwf.okewo.integration.exception.OkEwoIntegrationClientErrorException;
import io.muenchendigital.digiwf.okewo.integration.exception.OkEwoIntegrationException;
import io.muenchendigital.digiwf.okewo.integration.exception.OkEwoIntegrationServerErrorException;
import io.muenchendigital.digiwf.okewo.integration.gen.api.PersonErweitertApi;
import io.muenchendigital.digiwf.okewo.integration.gen.model.PersonErweitert;
import io.muenchendigital.digiwf.okewo.integration.gen.model.SuchePersonerweitertAnfrage;
import io.muenchendigital.digiwf.okewo.integration.gen.model.SuchePersonerweitertAntwort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;

@Repository
@RequiredArgsConstructor
@Slf4j
public class OkEwoPersonErweitertRepository {

    private final PersonErweitertApi personErweitertApi;

    /**
     * Gets a {@link PersonErweitert} by Ordnungsmerkmal.
     *
     * @param om         as Ordnungsmerkmal which identifies a {@link PersonErweitert}.
     * @param benutzerId to identify the requesting application.
     * @return the {@link PersonErweitert} identified by Ordnungsmerkmal.
     * @throws OkEwoIntegrationClientErrorException if the problem is with the client.
     * @throws OkEwoIntegrationServerErrorException if the problem is with OK.EWO.
     * @throws OkEwoIntegrationException            if the problem cannot be assigned directly to OK.EWO or client.
     */
    public PersonErweitert getPerson(final String om, final String benutzerId) throws OkEwoIntegrationClientErrorException, OkEwoIntegrationServerErrorException, OkEwoIntegrationException {
        try {
            return this.personErweitertApi.deMuenchenEaiEwoRouteROUTEPROCESSGETPERSONERWEITERT(om, benutzerId);
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
     * Searches for {@link PersonErweitert}s identified by {@link SuchePersonerweitertAnfrage}.
     *
     * @param suchePersonerweitertAnfrage to identify {@link PersonErweitert}s.
     * @return the {@link SuchePersonerweitertAntwort} with the identified {@link PersonErweitert}s.
     * @throws OkEwoIntegrationClientErrorException if the problem is with the client.
     * @throws OkEwoIntegrationServerErrorException if the problem is with OK.EWO.
     * @throws OkEwoIntegrationException            if the problem cannot be assigned directly to OK.EWO or client.
     */
    public SuchePersonerweitertAntwort searchPerson(final SuchePersonerweitertAnfrage suchePersonerweitertAnfrage) throws OkEwoIntegrationClientErrorException, OkEwoIntegrationServerErrorException, OkEwoIntegrationException {
        try {
            return this.personErweitertApi.deMuenchenEaiEwoRouteROUTEPROCESSSEARCHPERSONERWEITERT(suchePersonerweitertAnfrage);
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
