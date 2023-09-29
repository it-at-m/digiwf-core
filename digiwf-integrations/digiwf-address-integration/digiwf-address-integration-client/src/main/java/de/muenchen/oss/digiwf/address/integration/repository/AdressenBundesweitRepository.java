package de.muenchen.oss.digiwf.address.integration.repository;

import de.muenchen.oss.digiwf.address.integration.exception.AddressServiceIntegrationClientErrorException;
import de.muenchen.oss.digiwf.address.integration.exception.AddressServiceIntegrationException;
import de.muenchen.oss.digiwf.address.integration.exception.AddressServiceIntegrationServerErrorException;
import de.muenchen.oss.digiwf.address.integration.gen.api.AdressenBundesweitApi;
import de.muenchen.oss.digiwf.address.integration.gen.model.BundesweiteAdresse;
import de.muenchen.oss.digiwf.address.integration.gen.model.BundesweiteAdresseResponse;
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
public class AdressenBundesweitRepository {

    private final AdressenBundesweitApi adressenBundesweitApi;

    /**
     * Gets a {@link BundesweiteAdresse}.
     * <p>
     * For params and return value see {@link AdressenBundesweitApi#searchAdressen}.
     *
     * @throws AddressServiceIntegrationClientErrorException if the problem is with the client.
     * @throws AddressServiceIntegrationServerErrorException if the problem is with address service.
     * @throws AddressServiceIntegrationException            if the problem cannot be assigned directly to address service or client.
     */
    public BundesweiteAdresseResponse searchAdressen(final String query,
                                                     final String plz,
                                                     final String ortsname,
                                                     final String gemeindeschluessel,
                                                     final List<Long> hausnummerfilter,
                                                     final List<String> buchstabefilter,
                                                     final String sort,
                                                     final String sortdir,
                                                     final Integer page,
                                                     final Integer pagesize) throws AddressServiceIntegrationClientErrorException, AddressServiceIntegrationServerErrorException, AddressServiceIntegrationException {
        try {
            return this.adressenBundesweitApi.searchAdressen(
                    query,
                    plz,
                    ortsname,
                    gemeindeschluessel,
                    hausnummerfilter,
                    buchstabefilter,
                    sort,
                    sortdir,
                    page,
                    pagesize
            );
        } catch (final HttpClientErrorException exception) {
            final String message = String.format("The request to get address bundesweit failed with %s.", exception.getStatusCode());
            log.error(exception.getMessage());
            log.error(message);
            throw new AddressServiceIntegrationClientErrorException(message, exception);
        } catch (final HttpServerErrorException exception) {
            final String message = String.format("The request to get address bundesweit failed with %s.", exception.getStatusCode());
            log.error(exception.getMessage());
            log.error(message);
            throw new AddressServiceIntegrationServerErrorException(message, exception);
        } catch (final RestClientException exception) {
            final String message = String.format("The request to get address bundesweit failed.");
            log.error(exception.getMessage());
            log.error(message);
            throw new AddressServiceIntegrationException(message, exception);
        }
    }

}
