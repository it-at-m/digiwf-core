package de.muenchen.oss.digiwf.address.integration.client.api;


import de.muenchen.oss.digiwf.address.integration.client.exception.AddressServiceIntegrationClientErrorException;
import de.muenchen.oss.digiwf.address.integration.client.exception.AddressServiceIntegrationException;
import de.muenchen.oss.digiwf.address.integration.client.exception.AddressServiceIntegrationServerErrorException;
import de.muenchen.oss.digiwf.address.integration.client.gen.model.Strasse;
import de.muenchen.oss.digiwf.address.integration.client.gen.model.StrasseResponse;
import de.muenchen.oss.digiwf.address.integration.client.model.request.ListStreetsModel;

public interface StreetsMunichApi {

    Strasse findStreetsById(final long streetId) throws AddressServiceIntegrationServerErrorException, AddressServiceIntegrationException, AddressServiceIntegrationClientErrorException;

    StrasseResponse listStreets(final ListStreetsModel listStreetsModel) throws AddressServiceIntegrationServerErrorException, AddressServiceIntegrationException, AddressServiceIntegrationClientErrorException;

}
