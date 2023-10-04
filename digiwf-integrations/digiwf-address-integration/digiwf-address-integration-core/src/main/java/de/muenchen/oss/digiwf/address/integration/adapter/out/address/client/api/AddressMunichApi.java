package de.muenchen.oss.digiwf.address.integration.adapter.out.address.client.api;

import de.muenchen.oss.digiwf.address.integration.adapter.out.address.client.exception.AddressServiceIntegrationClientErrorException;
import de.muenchen.oss.digiwf.address.integration.adapter.out.address.client.exception.AddressServiceIntegrationException;
import de.muenchen.oss.digiwf.address.integration.adapter.out.address.client.exception.AddressServiceIntegrationServerErrorException;
import de.muenchen.oss.digiwf.address.integration.gen.model.AenderungResponse;
import de.muenchen.oss.digiwf.address.integration.gen.model.MuenchenAdresse;
import de.muenchen.oss.digiwf.address.integration.gen.model.MuenchenAdresseResponse;
import de.muenchen.oss.digiwf.address.integration.model.request.*;
import de.muenchen.oss.digiwf.address.integration.model.response.AddressDistancesModel;

public interface AddressMunichApi {

    MuenchenAdresse checkAddress(final CheckAddressesModel checkAddressesModel) throws AddressServiceIntegrationClientErrorException, AddressServiceIntegrationServerErrorException, AddressServiceIntegrationException;

    MuenchenAdresseResponse listAddresses(final ListAddressesModel listAddressesModel) throws AddressServiceIntegrationClientErrorException, AddressServiceIntegrationServerErrorException, AddressServiceIntegrationException;

    AenderungResponse listChanges(final ListAddressChangesModel listAddressChangesModel) throws AddressServiceIntegrationServerErrorException, AddressServiceIntegrationException, AddressServiceIntegrationClientErrorException;

    MuenchenAdresseResponse searchAddresses(final SearchAddressesModel searchAddressesModel) throws AddressServiceIntegrationServerErrorException, AddressServiceIntegrationException, AddressServiceIntegrationClientErrorException;

    AddressDistancesModel searchAddressesGeo(final SearchAddressesGeoModel searchAddressesGeoModel) throws AddressServiceIntegrationServerErrorException, AddressServiceIntegrationException, AddressServiceIntegrationClientErrorException;
}
