package de.muenchen.oss.digiwf.address.integration.application.port.in;

import de.muenchen.oss.digiwf.address.integration.gen.model.BundesweiteAdresseResponse;
import de.muenchen.oss.digiwf.address.integration.model.request.SearchAddressesGermanyModel;
import de.muenchen.oss.digiwf.message.process.api.error.BpmnError;
import de.muenchen.oss.digiwf.message.process.api.error.IncidentError;

public interface AddressGermanyInPort {

    BundesweiteAdresseResponse searchAddresses(final SearchAddressesGermanyModel searchAddressesGermanyModel) throws BpmnError, IncidentError;

}
