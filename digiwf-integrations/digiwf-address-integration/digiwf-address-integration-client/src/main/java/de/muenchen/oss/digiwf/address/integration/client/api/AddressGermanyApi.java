package de.muenchen.oss.digiwf.address.integration.client.api;


import de.muenchen.oss.digiwf.address.integration.client.exception.AddressServiceIntegrationClientErrorException;
import de.muenchen.oss.digiwf.address.integration.client.exception.AddressServiceIntegrationException;
import de.muenchen.oss.digiwf.address.integration.client.exception.AddressServiceIntegrationServerErrorException;
import de.muenchen.oss.digiwf.address.integration.client.gen.model.BundesweiteAdresseResponse;
import de.muenchen.oss.digiwf.address.integration.client.model.request.SearchAddressesGermanyModel;

/**
 * This interface defines the methods used for the communication with the address service.
 */
public interface AddressGermanyApi {

    /**
     * This method searches for addresses in the address service.
     * @param searchAddressesGermanyModel
     * @return
     * @throws AddressServiceIntegrationClientErrorException
     * @throws AddressServiceIntegrationServerErrorException
     * @throws AddressServiceIntegrationException
     */
    BundesweiteAdresseResponse searchAddresses(final SearchAddressesGermanyModel searchAddressesGermanyModel) throws AddressServiceIntegrationClientErrorException, AddressServiceIntegrationServerErrorException, AddressServiceIntegrationException;

}
