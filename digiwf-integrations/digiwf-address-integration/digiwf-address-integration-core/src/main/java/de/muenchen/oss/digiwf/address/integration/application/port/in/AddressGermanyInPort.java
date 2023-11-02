package de.muenchen.oss.digiwf.address.integration.application.port.in;

import de.muenchen.oss.digiwf.address.integration.client.gen.model.BundesweiteAdresseResponse;
import de.muenchen.oss.digiwf.address.integration.client.model.request.SearchAddressesGermanyModel;
import de.muenchen.oss.digiwf.message.process.api.error.BpmnError;
import de.muenchen.oss.digiwf.message.process.api.error.IncidentError;

/**
 * Port to integration infrastructure.
 */
public interface AddressGermanyInPort {

    /**
     * Search for addresses in Germany.
     *
     * @param searchAddressesGermanyModel
     * @return
     * @throws BpmnError
     * @throws IncidentError
     */
    BundesweiteAdresseResponse searchAddresses(final SearchAddressesGermanyModel searchAddressesGermanyModel) throws BpmnError, IncidentError;

}
