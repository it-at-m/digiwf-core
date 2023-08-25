package de.muenchen.oss.digiwf.address.service.integration.repository;

import de.muenchen.oss.digiwf.address.service.integration.exception.AddressServiceIntegrationClientErrorException;
import de.muenchen.oss.digiwf.address.service.integration.exception.AddressServiceIntegrationException;
import de.muenchen.oss.digiwf.address.service.integration.exception.AddressServiceIntegrationServerErrorException;
import de.muenchen.oss.digiwf.address.service.integration.gen.api.StraenMnchenApi;
import de.muenchen.oss.digiwf.address.service.integration.gen.model.Strasse;
import de.muenchen.oss.digiwf.address.service.integration.gen.model.StrasseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class StrassenMuenchenRepository {

    private final StraenMnchenApi straenMnchenApi;

    /**
     * Gets a {@link Strasse}.
     * <p>
     * For params and return value see {@link StraenMnchenApi#findStrasseByNummer}.
     *
     * @throws AddressServiceIntegrationClientErrorException if the problem is with the client.
     * @throws AddressServiceIntegrationServerErrorException if the problem is with address service.
     * @throws AddressServiceIntegrationException            if the problem cannot be assigned directly to address service or client.
     */
    public Strasse findStrasseById(final Long strasseId) throws AddressServiceIntegrationClientErrorException, AddressServiceIntegrationServerErrorException, AddressServiceIntegrationException {
        try {
            return this.straenMnchenApi.findStrasseByNummer(strasseId);
        } catch (final HttpClientErrorException exception) {
            final String message = String.format("The request to get strasse failed with %s.", exception.getStatusCode());
            log.error(exception.getMessage());
            log.error(message);
            throw new AddressServiceIntegrationClientErrorException(message, exception);
        } catch (final HttpServerErrorException exception) {
            final String message = String.format("The request to get strasse failed with %s.", exception.getStatusCode());
            log.error(exception.getMessage());
            log.error(message);
            throw new AddressServiceIntegrationServerErrorException(message, exception);
        } catch (final RestClientException exception) {
            final String message = String.format("The request to get strasse failed.");
            log.error(exception.getMessage());
            log.error(message);
            throw new AddressServiceIntegrationException(message, exception);
        }

    }

    /**
     * Gets a {@link StrasseResponse}.
     * <p>
     * For params and return value see {@link StraenMnchenApi#listStrassen}.
     *
     * @throws AddressServiceIntegrationClientErrorException if the problem is with the client.
     * @throws AddressServiceIntegrationServerErrorException if the problem is with address service.
     * @throws AddressServiceIntegrationException            if the problem cannot be assigned directly to address service or client.
     */
    public StrasseResponse listStrassen(final List<String> stadtbezirksnamen,
                                        final List<Long> stadtbezirksnummern,
                                        final String strassenname,
                                        final String sortdir,
                                        final Integer page,
                                        final Integer pagesize) throws AddressServiceIntegrationClientErrorException, AddressServiceIntegrationServerErrorException, AddressServiceIntegrationException {
        try {
            return this.straenMnchenApi.listStrassen(
                    stadtbezirksnamen,
                    stadtbezirksnummern,
                    strassenname,
                    sortdir,
                    page,
                    pagesize
            );
        } catch (final HttpClientErrorException exception) {
            final String message = String.format("The request to get strasse failed with %s.", exception.getStatusCode());
            log.error(exception.getMessage());
            log.error(message);
            throw new AddressServiceIntegrationClientErrorException(message, exception);
        } catch (final HttpServerErrorException exception) {
            final String message = String.format("The request to get strasse failed with %s.", exception.getStatusCode());
            log.error(exception.getMessage());
            log.error(message);
            throw new AddressServiceIntegrationServerErrorException(message, exception);
        } catch (final RestClientException exception) {
            final String message = String.format("The request to get strasse failed.");
            log.error(exception.getMessage());
            log.error(message);
            throw new AddressServiceIntegrationException(message, exception);
        }
    }

}
