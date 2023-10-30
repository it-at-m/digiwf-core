package de.muenchen.oss.digiwf.address.integration.client.impl;

import de.muenchen.oss.digiwf.address.integration.client.api.StreetsMunichApi;
import de.muenchen.oss.digiwf.address.integration.client.exception.AddressServiceIntegrationClientErrorException;
import de.muenchen.oss.digiwf.address.integration.client.exception.AddressServiceIntegrationException;
import de.muenchen.oss.digiwf.address.integration.client.exception.AddressServiceIntegrationServerErrorException;
import de.muenchen.oss.digiwf.address.integration.client.gen.api.StraenMnchenApi;
import de.muenchen.oss.digiwf.address.integration.client.gen.model.Strasse;
import de.muenchen.oss.digiwf.address.integration.client.gen.model.StrasseResponse;
import de.muenchen.oss.digiwf.address.integration.client.model.request.ListStreetsModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;

@RequiredArgsConstructor
@Slf4j
public class StreetsMunichImpl implements StreetsMunichApi {

    private final StraenMnchenApi straessenMuenchenApi;

    @Override
    public Strasse findStreetsById(final long streetId) throws AddressServiceIntegrationServerErrorException, AddressServiceIntegrationException, AddressServiceIntegrationClientErrorException {
        try {
            return this.straessenMuenchenApi.findStrasseByNummer(streetId);
        } catch (final HttpClientErrorException exception) {
            final String message = String.format("The request to get street failed with %s. %s", exception.getStatusCode(), exception.getMessage());
            log.debug(message);
            throw new AddressServiceIntegrationClientErrorException(message, exception);
        } catch (final HttpServerErrorException exception) {
            final String message = String.format("The request to get street failed with %s. %s", exception.getStatusCode(), exception.getMessage());
            log.debug(message);
            throw new AddressServiceIntegrationServerErrorException(message, exception);
        } catch (final RestClientException exception) {
            final String message = "The request to get street failed.";
            log.debug(message);
            throw new AddressServiceIntegrationException(message, exception);
        }
    }

    @Override
    public StrasseResponse listStreets(final ListStreetsModel listStreetsModel) throws AddressServiceIntegrationServerErrorException, AddressServiceIntegrationException, AddressServiceIntegrationClientErrorException {
        try {
            return this.straessenMuenchenApi.listStrassen(
                    listStreetsModel.getCityDistrictNames(),
                    listStreetsModel.getCityDistrictNumbers(),
                    listStreetsModel.getStreetName(),
                    listStreetsModel.getSortdir(),
                    listStreetsModel.getPage(),
                    listStreetsModel.getPagesize()
            );
        } catch (final HttpClientErrorException exception) {
            final String message = String.format("The request to get street failed with %s. %s", exception.getStatusCode(), exception.getMessage());
            log.debug(message);
            throw new AddressServiceIntegrationClientErrorException(message, exception);
        } catch (final HttpServerErrorException exception) {
            final String message = String.format("The request to get street failed with %s. %s", exception.getStatusCode(), exception.getMessage());
            log.debug(message);
            throw new AddressServiceIntegrationServerErrorException(message, exception);
        } catch (final RestClientException exception) {
            final String message = "The request to get street failed.";
            log.debug(message);
            throw new AddressServiceIntegrationException(message, exception);
        }
    }

}
