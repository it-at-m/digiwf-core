package de.muenchen.oss.digiwf.address.integration.application.port.in;

import de.muenchen.oss.digiwf.address.integration.client.gen.model.AenderungResponse;
import de.muenchen.oss.digiwf.address.integration.client.gen.model.MuenchenAdresse;
import de.muenchen.oss.digiwf.address.integration.client.gen.model.MuenchenAdresseResponse;
import de.muenchen.oss.digiwf.address.integration.client.model.request.*;
import de.muenchen.oss.digiwf.address.integration.client.model.response.AddressDistancesModel;
import de.muenchen.oss.digiwf.message.process.api.error.BpmnError;
import de.muenchen.oss.digiwf.message.process.api.error.IncidentError;

/**
 * Port to integration infrastructure.
 */
public interface AddressMunichInPort {

    /**
     * Check an address in Munich.
     * @param checkAddressesModel
     * @return
     * @throws BpmnError
     * @throws IncidentError
     */
    MuenchenAdresse checkAddress(final CheckAddressesModel checkAddressesModel) throws BpmnError, IncidentError;

    /**
     * List addresses in Munich.
     * @param listAddressesModel
     * @return
     * @throws BpmnError
     * @throws IncidentError
     */
    MuenchenAdresseResponse listAddresses(final ListAddressesModel listAddressesModel) throws BpmnError, IncidentError;


    /**
     * List changes in Munich.
     * @param listAddressChangesModel
     * @return
     * @throws BpmnError
     * @throws IncidentError
     */
    AenderungResponse listChanges(final ListAddressChangesModel listAddressChangesModel) throws BpmnError, IncidentError;

    /**
     * Search for addresses in Munich.
     * @param searchAddressesModel
     * @return
     * @throws BpmnError
     * @throws IncidentError
     */
    MuenchenAdresseResponse searchAddresses(final SearchAddressesModel searchAddressesModel) throws BpmnError, IncidentError;

    /**
     * Search for addresses in Munich.
     * @param searchAddressesGeoModel
     * @return
     * @throws BpmnError
     * @throws IncidentError
     */
    AddressDistancesModel searchAddressesGeo(final SearchAddressesGeoModel searchAddressesGeoModel) throws BpmnError, IncidentError;

}
