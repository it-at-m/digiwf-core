package de.muenchen.oss.digiwf.address.integration.api;

import de.muenchen.oss.digiwf.address.integration.exception.AddressServiceIntegrationClientErrorException;
import de.muenchen.oss.digiwf.address.integration.exception.AddressServiceIntegrationException;
import de.muenchen.oss.digiwf.address.integration.exception.AddressServiceIntegrationServerErrorException;
import de.muenchen.oss.digiwf.address.integration.gen.model.BundesweiteAdresseResponse;
import de.muenchen.oss.digiwf.address.integration.model.request.SearchAdressesGermanyModel;

public interface AddressGermanyApi {

    BundesweiteAdresseResponse searchAddresses(final SearchAdressesGermanyModel searchAdressesGermanyModel) throws AddressServiceIntegrationClientErrorException, AddressServiceIntegrationServerErrorException, AddressServiceIntegrationException;

}
