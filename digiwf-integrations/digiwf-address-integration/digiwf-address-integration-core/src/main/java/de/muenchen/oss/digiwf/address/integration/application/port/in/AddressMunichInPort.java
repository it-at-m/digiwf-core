package de.muenchen.oss.digiwf.address.integration.application.port.in;

import de.muenchen.oss.digiwf.address.integration.client.gen.model.AenderungResponse;
import de.muenchen.oss.digiwf.address.integration.client.gen.model.MuenchenAdresse;
import de.muenchen.oss.digiwf.address.integration.client.gen.model.MuenchenAdresseResponse;
import de.muenchen.oss.digiwf.address.integration.client.model.request.*;
import de.muenchen.oss.digiwf.address.integration.client.model.response.AddressDistancesModel;
import de.muenchen.oss.digiwf.message.process.api.error.BpmnError;
import de.muenchen.oss.digiwf.message.process.api.error.IncidentError;

public interface AddressMunichInPort {

    MuenchenAdresse checkAddress(final CheckAddressesModel checkAddressesModel) throws BpmnError, IncidentError;

    MuenchenAdresseResponse listAddresses(final ListAddressesModel listAddressesModel) throws BpmnError, IncidentError;

    AenderungResponse listChanges(final ListAddressChangesModel listAddressChangesModel) throws BpmnError, IncidentError;

    MuenchenAdresseResponse searchAddresses(final SearchAddressesModel searchAddressesModel) throws BpmnError, IncidentError;

    AddressDistancesModel searchAddressesGeo(final SearchAddressesGeoModel searchAddressesGeoModel) throws BpmnError, IncidentError;

}
