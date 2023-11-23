package de.muenchen.oss.digiwf.address.integration.client.impl;

import de.muenchen.oss.digiwf.address.integration.client.api.AddressGermanyApi;
import de.muenchen.oss.digiwf.address.integration.client.exception.AddressServiceIntegrationClientErrorException;
import de.muenchen.oss.digiwf.address.integration.client.exception.AddressServiceIntegrationException;
import de.muenchen.oss.digiwf.address.integration.client.exception.AddressServiceIntegrationServerErrorException;
import de.muenchen.oss.digiwf.address.integration.client.gen.api.AdressenBundesweitApi;
import de.muenchen.oss.digiwf.address.integration.client.gen.model.BundesweiteAdresseResponse;
import de.muenchen.oss.digiwf.address.integration.client.model.request.SearchAddressesGermanyModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;

@RequiredArgsConstructor
@Slf4j
public class AddressGermanyImpl implements AddressGermanyApi {

    private final AdressenBundesweitApi adressenBundesweitApi;

    @Override
    public BundesweiteAdresseResponse searchAddresses(final SearchAddressesGermanyModel searchAddressesGermanyModel) throws AddressServiceIntegrationClientErrorException, AddressServiceIntegrationServerErrorException, AddressServiceIntegrationException {
        try {
            return this.adressenBundesweitApi.searchAdressen(
                    searchAddressesGermanyModel.getQuery(),
                    searchAddressesGermanyModel.getZip(),
                    searchAddressesGermanyModel.getCityName(),
                    searchAddressesGermanyModel.getGemeindeschluessel(),
                    searchAddressesGermanyModel.getHouseNumberFilter(),
                    searchAddressesGermanyModel.getLetterFilter(),
                    searchAddressesGermanyModel.getSort(),
                    searchAddressesGermanyModel.getSortdir(),
                    searchAddressesGermanyModel.getPage(),
                    searchAddressesGermanyModel.getPagesize()
            ).block();
        } catch (final HttpClientErrorException exception) {
            final String message = String.format("The request to get address bundesweit failed with %s.", exception.getStatusCode());
            log.debug(message);
            throw new AddressServiceIntegrationClientErrorException(message, exception);
        } catch (final HttpServerErrorException exception) {
            final String message = String.format("The request to get address bundesweit failed with %s.", exception.getStatusCode());
            log.debug(message);
            throw new AddressServiceIntegrationServerErrorException(message, exception);
        } catch (final RestClientException exception) {
            final String message = String.format("The request to get address bundesweit failed.");
            log.debug(message);
            throw new AddressServiceIntegrationException(message, exception);
        }
    }

}
