package de.muenchen.oss.digiwf.address.integration.client.impl;

import de.muenchen.oss.digiwf.address.integration.client.api.AddressMunichApi;
import de.muenchen.oss.digiwf.address.integration.client.exception.AddressServiceIntegrationClientErrorException;
import de.muenchen.oss.digiwf.address.integration.client.exception.AddressServiceIntegrationException;
import de.muenchen.oss.digiwf.address.integration.client.exception.AddressServiceIntegrationServerErrorException;
import de.muenchen.oss.digiwf.address.integration.client.gen.api.AdressenMnchenApi;
import de.muenchen.oss.digiwf.address.integration.client.gen.model.AdresseDistanz;
import de.muenchen.oss.digiwf.address.integration.client.gen.model.AenderungResponse;
import de.muenchen.oss.digiwf.address.integration.client.gen.model.MuenchenAdresse;
import de.muenchen.oss.digiwf.address.integration.client.gen.model.MuenchenAdresseResponse;
import de.muenchen.oss.digiwf.address.integration.client.model.request.*;
import de.muenchen.oss.digiwf.address.integration.client.model.response.AddressDistancesModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class AddressesMunichImpl implements AddressMunichApi {

    private final AdressenMnchenApi adressenMuenchenApi;

    @Override
    public MuenchenAdresse checkAddress(final CheckAddressesModel checkAddressesModel) throws AddressServiceIntegrationClientErrorException, AddressServiceIntegrationServerErrorException, AddressServiceIntegrationException {
        try {
            return this.adressenMuenchenApi.checkAdresse(
                    checkAddressesModel.getAddress(),
                    checkAddressesModel.getStreetName(),
                    checkAddressesModel.getStreetId(),
                    checkAddressesModel.getHouseNumber(),
                    checkAddressesModel.getAdditionalInfo(),
                    checkAddressesModel.getZip(),
                    checkAddressesModel.getCityName(),
                    checkAddressesModel.getGemeindeschluessel()
            );
        } catch (final HttpClientErrorException exception) {
            final String message = String.format("The request to get address failed with %s. %s", exception.getStatusCode(), exception.getMessage());
            log.debug(message);
            throw new AddressServiceIntegrationClientErrorException(message, exception);
        } catch (final HttpServerErrorException exception) {
            final String message = String.format("The request to get address failed with %s. %s", exception.getStatusCode(), exception.getMessage());
            log.debug(message);
            throw new AddressServiceIntegrationServerErrorException(message, exception);
        } catch (final RestClientException exception) {
            final String message = "The request to get address failed.";
            log.debug(message);
            throw new AddressServiceIntegrationException(message, exception);
        }
    }

    @Override
    public MuenchenAdresseResponse listAddresses(final ListAddressesModel listAddressesModel) throws AddressServiceIntegrationClientErrorException, AddressServiceIntegrationServerErrorException, AddressServiceIntegrationException {
        try {
            return this.adressenMuenchenApi.listAdressen(
                    listAddressesModel.getBaublock(),
                    listAddressesModel.getErhaltungssatzung(),
                    listAddressesModel.getGemarkung(),
                    listAddressesModel.getKaminkehrerbezirk(),
                    listAddressesModel.getZip(),
                    listAddressesModel.getMittelschule(),
                    listAddressesModel.getGrundschule(),
                    listAddressesModel.getPolizeiinspektion(),
                    listAddressesModel.getStimmbezirk(),
                    listAddressesModel.getStimmkreis(),
                    listAddressesModel.getWahlbezirk(),
                    listAddressesModel.getWahlkreis(),
                    listAddressesModel.getStadtbezirk(),
                    listAddressesModel.getStadtbezirksteil(),
                    listAddressesModel.getStadtbezirksviertel(),
                    listAddressesModel.getSort(),
                    listAddressesModel.getSortdir(),
                    listAddressesModel.getPage(),
                    listAddressesModel.getPagesize()
            );
        } catch (final HttpClientErrorException exception) {
            final String message = String.format("The request to get address failed with %s. %s", exception.getStatusCode(), exception.getMessage());
            log.debug(message);
            throw new AddressServiceIntegrationClientErrorException(message, exception);
        } catch (final HttpServerErrorException exception) {
            final String message = String.format("The request to get address failed with %s. %s", exception.getStatusCode(), exception.getMessage());
            log.debug(message);
            throw new AddressServiceIntegrationServerErrorException(message, exception);
        } catch (final RestClientException exception) {
            final String message = "The request to get address failed.";
            log.debug(message);
            throw new AddressServiceIntegrationException(message, exception);
        }
    }

    @Override
    public AenderungResponse listChanges(final ListAddressChangesModel listAddressChangesModel) throws AddressServiceIntegrationServerErrorException, AddressServiceIntegrationException, AddressServiceIntegrationClientErrorException {
        try {
            return this.adressenMuenchenApi.listAenderungen(
                    listAddressChangesModel.getEffectiveDateFrom(),
                    listAddressChangesModel.getEffectiveDateTo(),
                    listAddressChangesModel.getStreetName(),
                    listAddressChangesModel.getHouseNumber(),
                    listAddressChangesModel.getZip(),
                    listAddressChangesModel.getAdditionalInfo(),
                    listAddressChangesModel.getSorting(),
                    listAddressChangesModel.getSortingDir(),
                    listAddressChangesModel.getPageNumber(),
                    listAddressChangesModel.getPageSize()
            );
        } catch (final HttpClientErrorException exception) {
            final String message = String.format("The request to get address failed with %s. %s", exception.getStatusCode(), exception.getMessage());
            log.debug(message);
            throw new AddressServiceIntegrationClientErrorException(message, exception);
        } catch (final HttpServerErrorException exception) {
            final String message = String.format("The request to get address failed with %s. %s", exception.getStatusCode(), exception.getMessage());
            log.debug(message);
            throw new AddressServiceIntegrationServerErrorException(message, exception);
        } catch (final RestClientException exception) {
            final String message = "The request to get address failed.";
            log.debug(message);
            throw new AddressServiceIntegrationException(message, exception);
        }
    }

    @Override
    public MuenchenAdresseResponse searchAddresses(final SearchAddressesModel searchAddressesModel) throws AddressServiceIntegrationServerErrorException, AddressServiceIntegrationException, AddressServiceIntegrationClientErrorException {
        try {
            return this.adressenMuenchenApi.searchAdressen1(
                    searchAddressesModel.getQuery(),
                    searchAddressesModel.getZipFilter(),
                    searchAddressesModel.getHouseNumberFilter(),
                    searchAddressesModel.getLetterFilter(),
                    searchAddressesModel.getSearchtype(),
                    searchAddressesModel.getSort(),
                    searchAddressesModel.getSortdir(),
                    searchAddressesModel.getPage(),
                    searchAddressesModel.getPagesize()
            );
        } catch (final HttpClientErrorException exception) {
            final String message = String.format("The request to get address failed with %s. %s", exception.getStatusCode(), exception.getMessage());
            log.debug(message);
            throw new AddressServiceIntegrationClientErrorException(message, exception);
        } catch (final HttpServerErrorException exception) {
            final String message = String.format("The request to get address failed with %s. %s", exception.getStatusCode(), exception.getMessage());
            log.debug(message);
            throw new AddressServiceIntegrationServerErrorException(message, exception);
        } catch (final RestClientException exception) {
            final String message = "The request to get address failed.";
            log.debug(message);
            throw new AddressServiceIntegrationException(message, exception);
        }
    }

    @Override
    public AddressDistancesModel searchAddressesGeo(final SearchAddressesGeoModel searchAddressesGeoModel) throws AddressServiceIntegrationServerErrorException, AddressServiceIntegrationException, AddressServiceIntegrationClientErrorException {
        try {
            final List<AdresseDistanz> addressDistances = this.adressenMuenchenApi.searchAdressenGeo(
                    searchAddressesGeoModel.getGeometry(),
                    searchAddressesGeoModel.getLat(),
                    searchAddressesGeoModel.getLng(),
                    searchAddressesGeoModel.getDistance(),
                    searchAddressesGeoModel.getTopleftlat(),
                    searchAddressesGeoModel.getTopleftlng(),
                    searchAddressesGeoModel.getBottomrightlat(),
                    searchAddressesGeoModel.getBottomrightlng(),
                    searchAddressesGeoModel.getFormat()
            );
            return AddressDistancesModel.builder()
                    .addressDistances(addressDistances)
                    .build();
        } catch (final HttpClientErrorException exception) {
            final String message = String.format("The request to get address failed with %s. %s", exception.getStatusCode(), exception.getMessage());
            log.debug(message);
            throw new AddressServiceIntegrationClientErrorException(message, exception);
        } catch (final HttpServerErrorException exception) {
            final String message = String.format("The request to get address failed with %s. %s", exception.getStatusCode(), exception.getMessage());
            log.debug(message);
            throw new AddressServiceIntegrationServerErrorException(message, exception);
        } catch (final RestClientException exception) {
            final String message = "The request to get address failed.";
            log.debug(message);
            throw new AddressServiceIntegrationException(message, exception);
        }
    }
}
