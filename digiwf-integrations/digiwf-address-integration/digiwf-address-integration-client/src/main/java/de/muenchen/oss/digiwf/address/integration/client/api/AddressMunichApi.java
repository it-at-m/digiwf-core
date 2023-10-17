package de.muenchen.oss.digiwf.address.integration.client.api;


import de.muenchen.oss.digiwf.address.integration.client.exception.AddressServiceIntegrationClientErrorException;
import de.muenchen.oss.digiwf.address.integration.client.exception.AddressServiceIntegrationException;
import de.muenchen.oss.digiwf.address.integration.client.exception.AddressServiceIntegrationServerErrorException;
import de.muenchen.oss.digiwf.address.integration.client.gen.model.AenderungResponse;
import de.muenchen.oss.digiwf.address.integration.client.gen.model.MuenchenAdresse;
import de.muenchen.oss.digiwf.address.integration.client.gen.model.MuenchenAdresseResponse;
import de.muenchen.oss.digiwf.address.integration.client.model.request.*;
import de.muenchen.oss.digiwf.address.integration.client.model.response.AddressDistancesModel;

public interface AddressMunichApi {

    MuenchenAdresse checkAddress(final CheckAddressesModel checkAddressesModel) throws AddressServiceIntegrationClientErrorException, AddressServiceIntegrationServerErrorException, AddressServiceIntegrationException;

    MuenchenAdresseResponse listAddresses(final ListAddressesModel listAddressesModel) throws AddressServiceIntegrationClientErrorException, AddressServiceIntegrationServerErrorException, AddressServiceIntegrationException;

    AenderungResponse listChanges(final ListAddressChangesModel listAddressChangesModel) throws AddressServiceIntegrationServerErrorException, AddressServiceIntegrationException, AddressServiceIntegrationClientErrorException;

    MuenchenAdresseResponse searchAddresses(final SearchAddressesModel searchAddressesModel) throws AddressServiceIntegrationServerErrorException, AddressServiceIntegrationException, AddressServiceIntegrationClientErrorException;

    AddressDistancesModel searchAddressesGeo(final SearchAddressesGeoModel searchAddressesGeoModel) throws AddressServiceIntegrationServerErrorException, AddressServiceIntegrationException, AddressServiceIntegrationClientErrorException;
}
