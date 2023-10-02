package de.muenchen.oss.digiwf.address.integration.api;

import de.muenchen.oss.digiwf.address.integration.exception.AddressServiceIntegrationClientErrorException;
import de.muenchen.oss.digiwf.address.integration.exception.AddressServiceIntegrationException;
import de.muenchen.oss.digiwf.address.integration.exception.AddressServiceIntegrationServerErrorException;
import de.muenchen.oss.digiwf.address.integration.gen.model.Strasse;
import de.muenchen.oss.digiwf.address.integration.gen.model.StrasseResponse;
import de.muenchen.oss.digiwf.address.integration.model.request.ListStreetsModel;

public interface StreetsMunichApi {

    Strasse findStreetsById(final long streetId) throws AddressServiceIntegrationServerErrorException, AddressServiceIntegrationException, AddressServiceIntegrationClientErrorException;

    StrasseResponse listStreets(final ListStreetsModel listStreetsModel) throws AddressServiceIntegrationServerErrorException, AddressServiceIntegrationException, AddressServiceIntegrationClientErrorException;

}
