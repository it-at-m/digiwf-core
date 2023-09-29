package de.muenchen.oss.digiwf.address.integration.repository;

import de.muenchen.oss.digiwf.address.integration.exception.AddressServiceIntegrationClientErrorException;
import de.muenchen.oss.digiwf.address.integration.exception.AddressServiceIntegrationException;
import de.muenchen.oss.digiwf.address.integration.exception.AddressServiceIntegrationServerErrorException;
import de.muenchen.oss.digiwf.address.integration.gen.api.AdressenMnchenApi;
import de.muenchen.oss.digiwf.address.integration.gen.model.AdresseDistanz;
import de.muenchen.oss.digiwf.address.integration.gen.model.AenderungResponse;
import de.muenchen.oss.digiwf.address.integration.gen.model.MuenchenAdresse;
import de.muenchen.oss.digiwf.address.integration.gen.model.MuenchenAdresseResponse;
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
public class AdressenMuenchenRepository {

    private final AdressenMnchenApi adressenMnchenApi;

    /**
     * Checks and gets a {@link MuenchenAdresse}.
     * <p>
     * For params and return value see {@link AdressenMnchenApi#checkAdresse}.
     *
     * @throws AddressServiceIntegrationClientErrorException if the problem is with the client.
     * @throws AddressServiceIntegrationServerErrorException if the problem is with address service.
     * @throws AddressServiceIntegrationException            if the problem cannot be assigned directly to address service or client.
     */
    public MuenchenAdresse checkAdresse(final String adresse,
                                        final String strassenname,
                                        final Integer strasseId,
                                        final String hausnummer,
                                        final String zusatz,
                                        final String plz,
                                        final String ortsname,
                                        final String gemeindeschluessel) throws AddressServiceIntegrationClientErrorException, AddressServiceIntegrationServerErrorException, AddressServiceIntegrationException {
        try {
            return this.adressenMnchenApi.checkAdresse(
                    adresse,
                    strassenname,
                    strasseId,
                    hausnummer,
                    zusatz,
                    plz,
                    ortsname,
                    gemeindeschluessel
            );
        } catch (final HttpClientErrorException exception) {
            final String message = String.format("The request to get address failed with %s.", exception.getStatusCode());
            log.error(exception.getMessage());
            log.error(message);
            throw new AddressServiceIntegrationClientErrorException(message, exception);
        } catch (final HttpServerErrorException exception) {
            final String message = String.format("The request to get address failed with %s.", exception.getStatusCode());
            log.error(exception.getMessage());
            log.error(message);
            throw new AddressServiceIntegrationServerErrorException(message, exception);
        } catch (final RestClientException exception) {
            final String message = String.format("The request to get address failed.");
            log.error(exception.getMessage());
            log.error(message);
            throw new AddressServiceIntegrationException(message, exception);
        }
    }

    /**
     * Gets {@link MuenchenAdresseResponse}.
     * <p>
     * For params and return value see {@link AdressenMnchenApi#listAdressen}.
     *
     * @throws AddressServiceIntegrationClientErrorException if the problem is with the client.
     * @throws AddressServiceIntegrationServerErrorException if the problem is with address service.
     * @throws AddressServiceIntegrationException            if the problem cannot be assigned directly to address service or client.
     */
    public MuenchenAdresseResponse listAdressen(final List<String> baublock,
                                                final List<String> erhaltungssatzung,
                                                final List<String> gemarkung,
                                                final List<String> kaminkehrerbezirk,
                                                final List<String> plz,
                                                final List<String> mittelschule,
                                                final List<String> grundschule,
                                                final List<String> polizeiinspektion,
                                                final List<Long> stimmbezirk,
                                                final List<Long> stimmkreis,
                                                final List<Long> wahlbezirk,
                                                final List<Long> wahlkreis,
                                                final List<String> stadtbezirk,
                                                final List<String> stadtbezirksteil,
                                                final List<String> stadtbezirksviertel,
                                                final String sort,
                                                final String sortdir,
                                                final Integer page,
                                                final Integer pagesize) throws RestClientException, AddressServiceIntegrationClientErrorException, AddressServiceIntegrationServerErrorException, AddressServiceIntegrationException {
        try {
            return this.adressenMnchenApi.listAdressen(
                    baublock,
                    erhaltungssatzung,
                    gemarkung,
                    kaminkehrerbezirk,
                    plz,
                    mittelschule,
                    grundschule,
                    polizeiinspektion,
                    stimmbezirk,
                    stimmkreis,
                    wahlbezirk,
                    wahlkreis,
                    stadtbezirk,
                    stadtbezirksteil,
                    stadtbezirksviertel,
                    sort,
                    sortdir,
                    page,
                    pagesize
            );
        } catch (final HttpClientErrorException exception) {
            final String message = String.format("The request to get address failed with %s.", exception.getStatusCode());
            log.error(exception.getMessage());
            log.error(message);
            throw new AddressServiceIntegrationClientErrorException(message, exception);
        } catch (final HttpServerErrorException exception) {
            final String message = String.format("The request to get address failed with %s.", exception.getStatusCode());
            log.error(exception.getMessage());
            log.error(message);
            throw new AddressServiceIntegrationServerErrorException(message, exception);
        } catch (final RestClientException exception) {
            final String message = String.format("The request to get address failed.");
            log.error(exception.getMessage());
            log.error(message);
            throw new AddressServiceIntegrationException(message, exception);
        }
    }

    /**
     * Gets {@link AenderungResponse}.
     * <p>
     * For params and return value see {@link AdressenMnchenApi#listAenderungen}.
     *
     * @throws AddressServiceIntegrationClientErrorException if the problem is with the client.
     * @throws AddressServiceIntegrationServerErrorException if the problem is with address service.
     * @throws AddressServiceIntegrationException            if the problem cannot be assigned directly to address service or client.
     */
    public AenderungResponse listAenderungen(final String wirkungsdatumvon,
                                             final String wirkungsdatumbis,
                                             final String strassenname,
                                             final Long hausnummer,
                                             final String plz,
                                             final String zusatz,
                                             final String sort,
                                             final String sortdir,
                                             final Integer page,
                                             final Integer pagesize) throws AddressServiceIntegrationClientErrorException, AddressServiceIntegrationServerErrorException, AddressServiceIntegrationException {
        try {
            return this.adressenMnchenApi.listAenderungen(
                    wirkungsdatumvon,
                    wirkungsdatumbis,
                    strassenname,
                    hausnummer,
                    plz,
                    zusatz,
                    sort,
                    sortdir,
                    page,
                    pagesize
            );
        } catch (final HttpClientErrorException exception) {
            final String message = String.format("The request to get address failed with %s.", exception.getStatusCode());
            log.error(exception.getMessage());
            log.error(message);
            throw new AddressServiceIntegrationClientErrorException(message, exception);
        } catch (final HttpServerErrorException exception) {
            final String message = String.format("The request to get address failed with %s.", exception.getStatusCode());
            log.error(exception.getMessage());
            log.error(message);
            throw new AddressServiceIntegrationServerErrorException(message, exception);
        } catch (final RestClientException exception) {
            final String message = String.format("The request to get address failed.");
            log.error(exception.getMessage());
            log.error(message);
            throw new AddressServiceIntegrationException(message, exception);
        }
    }

    /**
     * Gets {@link MuenchenAdresseResponse}.
     * <p>
     * For params and return value see {@link AdressenMnchenApi#searchAdressen1}.
     *
     * @throws AddressServiceIntegrationClientErrorException if the problem is with the client.
     * @throws AddressServiceIntegrationServerErrorException if the problem is with address service.
     * @throws AddressServiceIntegrationException            if the problem cannot be assigned directly to address service or client.
     */
    public MuenchenAdresseResponse searchAdressen(final String query,
                                                  final List<String> plzfilter,
                                                  final List<Long> hausnummerfilter,
                                                  final List<String> buchstabefilter,
                                                  final String searchtype,
                                                  final String sort,
                                                  final String sortdir,
                                                  final Integer page,
                                                  final Integer pagesize) throws AddressServiceIntegrationClientErrorException, AddressServiceIntegrationServerErrorException, AddressServiceIntegrationException {
        try {
            return this.adressenMnchenApi.searchAdressen1(
                    query,
                    plzfilter,
                    hausnummerfilter,
                    buchstabefilter,
                    searchtype,
                    sort,
                    sortdir,
                    page,
                    pagesize
            );
        } catch (final HttpClientErrorException exception) {
            final String message = String.format("The request to get address failed with %s.", exception.getStatusCode());
            log.error(exception.getMessage());
            log.error(message);
            throw new AddressServiceIntegrationClientErrorException(message, exception);
        } catch (final HttpServerErrorException exception) {
            final String message = String.format("The request to get address failed with %s.", exception.getStatusCode());
            log.error(exception.getMessage());
            log.error(message);
            throw new AddressServiceIntegrationServerErrorException(message, exception);
        } catch (final RestClientException exception) {
            final String message = String.format("The request to get address failed.");
            log.error(exception.getMessage());
            log.error(message);
            throw new AddressServiceIntegrationException(message, exception);
        }
    }

    /**
     * Gets {@link AdresseDistanz}s.
     * <p>
     * For params and return value see {@link AdressenMnchenApi#searchAdressenGeo}.
     *
     * @throws AddressServiceIntegrationClientErrorException if the problem is with the client.
     * @throws AddressServiceIntegrationServerErrorException if the problem is with address service.
     * @throws AddressServiceIntegrationException            if the problem cannot be assigned directly to address service or client.
     */
    public List<AdresseDistanz> searchAdressenGeo(final String geometrie,
                                                  final Double lat,
                                                  final Double lng,
                                                  final Double distanz,
                                                  final Double topleftlat,
                                                  final Double topleftlng,
                                                  final Double bottomrightlat,
                                                  final Double bottomrightlng,
                                                  final String format) throws AddressServiceIntegrationClientErrorException, AddressServiceIntegrationServerErrorException, AddressServiceIntegrationException {
        try {
            return this.adressenMnchenApi.searchAdressenGeo(
                    geometrie,
                    lat,
                    lng,
                    distanz,
                    topleftlat,
                    topleftlng,
                    bottomrightlat,
                    bottomrightlng,
                    format
            );
        } catch (final HttpClientErrorException exception) {
            final String message = String.format("The request to get address failed with %s.", exception.getStatusCode());
            log.error(exception.getMessage());
            log.error(message);
            throw new AddressServiceIntegrationClientErrorException(message, exception);
        } catch (final HttpServerErrorException exception) {
            final String message = String.format("The request to get address failed with %s.", exception.getStatusCode());
            log.error(exception.getMessage());
            log.error(message);
            throw new AddressServiceIntegrationServerErrorException(message, exception);
        } catch (final RestClientException exception) {
            final String message = String.format("The request to get address failed.");
            log.error(exception.getMessage());
            log.error(message);
            throw new AddressServiceIntegrationException(message, exception);
        }
    }

}
