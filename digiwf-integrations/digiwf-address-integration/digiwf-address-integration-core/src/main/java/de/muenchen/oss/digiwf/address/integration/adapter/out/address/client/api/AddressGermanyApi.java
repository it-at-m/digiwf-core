package de.muenchen.oss.digiwf.address.integration.adapter.out.address.client.api;

import de.muenchen.oss.digiwf.address.integration.adapter.out.address.client.exception.AddressServiceIntegrationClientErrorException;
import de.muenchen.oss.digiwf.address.integration.adapter.out.address.client.exception.AddressServiceIntegrationException;
import de.muenchen.oss.digiwf.address.integration.adapter.out.address.client.exception.AddressServiceIntegrationServerErrorException;
import de.muenchen.oss.digiwf.address.integration.gen.model.BundesweiteAdresseResponse;
import de.muenchen.oss.digiwf.address.integration.model.request.SearchAddressesGermanyModel;

public interface AddressGermanyApi {

    BundesweiteAdresseResponse searchAddresses(final SearchAddressesGermanyModel searchAddressesGermanyModel) throws AddressServiceIntegrationClientErrorException, AddressServiceIntegrationServerErrorException, AddressServiceIntegrationException;

}
