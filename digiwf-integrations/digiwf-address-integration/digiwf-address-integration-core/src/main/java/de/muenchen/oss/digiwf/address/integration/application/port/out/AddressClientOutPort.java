package de.muenchen.oss.digiwf.address.integration.application.port.out;

import de.muenchen.oss.digiwf.address.integration.client.gen.model.*;
import de.muenchen.oss.digiwf.address.integration.client.model.request.*;
import de.muenchen.oss.digiwf.address.integration.client.model.response.AddressDistancesModel;
import de.muenchen.oss.digiwf.message.process.api.error.BpmnError;
import de.muenchen.oss.digiwf.message.process.api.error.IncidentError;

/**
 * Port to integration infrastructure.
 */
public interface AddressClientOutPort {

    /**
     * Search for addresses in Germany.
     * @param searchAddressesGermanyModel
     * @return
     * @throws BpmnError
     * @throws IncidentError
     */
    BundesweiteAdresseResponse searchAddresses(final SearchAddressesGermanyModel searchAddressesGermanyModel) throws BpmnError, IncidentError;

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

    /**
     * List streets in Munich.
     * @param streetId
     * @return
     * @throws BpmnError
     * @throws IncidentError
     */
    Strasse findStreetsById(final long streetId) throws BpmnError, IncidentError;

    /**
     * List streets in Munich.
     * @param listStreetsModel
     * @return
     * @throws BpmnError
     * @throws IncidentError
     */
    StrasseResponse listStreets(final ListStreetsModel listStreetsModel) throws BpmnError, IncidentError;

}
