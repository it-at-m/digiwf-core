package de.muenchen.oss.digiwf.address.integration.client.api;


import de.muenchen.oss.digiwf.address.integration.client.exception.AddressServiceIntegrationClientErrorException;
import de.muenchen.oss.digiwf.address.integration.client.exception.AddressServiceIntegrationException;
import de.muenchen.oss.digiwf.address.integration.client.exception.AddressServiceIntegrationServerErrorException;
import de.muenchen.oss.digiwf.address.integration.client.gen.model.Strasse;
import de.muenchen.oss.digiwf.address.integration.client.gen.model.StrasseResponse;
import de.muenchen.oss.digiwf.address.integration.client.model.request.ListStreetsModel;

/**
 * This interface defines the methods used for the communication with the address service.
 */
public interface StreetsMunichApi {

    /**
     * This method finds a street by id in the address service.
     * @param streetId
     * @return
     * @throws AddressServiceIntegrationServerErrorException
     * @throws AddressServiceIntegrationException
     * @throws AddressServiceIntegrationClientErrorException
     */
    Strasse findStreetsById(final long streetId) throws AddressServiceIntegrationServerErrorException, AddressServiceIntegrationException, AddressServiceIntegrationClientErrorException;

    /**
     * This method lists streets in the address service.
     * @param listStreetsModel
     * @return
     * @throws AddressServiceIntegrationServerErrorException
     * @throws AddressServiceIntegrationException
     * @throws AddressServiceIntegrationClientErrorException
     */
    StrasseResponse listStreets(final ListStreetsModel listStreetsModel) throws AddressServiceIntegrationServerErrorException, AddressServiceIntegrationException, AddressServiceIntegrationClientErrorException;

}
