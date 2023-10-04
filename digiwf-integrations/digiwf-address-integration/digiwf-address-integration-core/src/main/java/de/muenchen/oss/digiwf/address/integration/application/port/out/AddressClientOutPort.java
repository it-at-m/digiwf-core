package de.muenchen.oss.digiwf.address.integration.application.port.out;

import de.muenchen.oss.digiwf.address.integration.gen.model.*;
import de.muenchen.oss.digiwf.address.integration.model.request.*;
import de.muenchen.oss.digiwf.address.integration.model.response.AddressDistancesModel;
import de.muenchen.oss.digiwf.message.process.api.error.BpmnError;
import de.muenchen.oss.digiwf.message.process.api.error.IncidentError;

public interface AddressClientOutPort {

    BundesweiteAdresseResponse searchAddresses(final SearchAddressesGermanyModel searchAddressesGermanyModel) throws BpmnError, IncidentError;

    MuenchenAdresse checkAddress(final CheckAddressesModel checkAddressesModel) throws BpmnError, IncidentError;

    MuenchenAdresseResponse listAddresses(final ListAddressesModel listAddressesModel) throws BpmnError, IncidentError;

    AenderungResponse listChanges(final ListAddressChangesModel listAddressChangesModel) throws BpmnError, IncidentError;

    MuenchenAdresseResponse searchAddresses(final SearchAddressesModel searchAddressesModel) throws BpmnError, IncidentError;

    AddressDistancesModel searchAddressesGeo(final SearchAddressesGeoModel searchAddressesGeoModel) throws BpmnError, IncidentError;

    Strasse findStreetsById(final long streetId) throws BpmnError, IncidentError;

    StrasseResponse listStreets(final ListStreetsModel listStreetsModel) throws BpmnError, IncidentError;

}
