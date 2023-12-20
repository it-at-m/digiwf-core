package de.muenchen.oss.digiwf.address.integration.application.usecase;

import de.muenchen.oss.digiwf.address.integration.application.port.in.AddressMunichInPort;
import de.muenchen.oss.digiwf.address.integration.application.port.out.AddressClientOutPort;
import de.muenchen.oss.digiwf.address.integration.client.gen.model.AenderungResponse;
import de.muenchen.oss.digiwf.address.integration.client.gen.model.MuenchenAdresse;
import de.muenchen.oss.digiwf.address.integration.client.gen.model.MuenchenAdresseResponse;
import de.muenchen.oss.digiwf.address.integration.client.model.request.*;
import de.muenchen.oss.digiwf.address.integration.client.model.response.AddressDistancesModel;
import de.muenchen.oss.digiwf.message.process.api.error.BpmnError;
import de.muenchen.oss.digiwf.message.process.api.error.IncidentError;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AddressesMunichUseCase implements AddressMunichInPort {

    private final AddressClientOutPort addressClientOutPort;

    @Override
    public MuenchenAdresse checkAddress(CheckAddressesModel checkAddressesModel) throws BpmnError, IncidentError {
        return this.addressClientOutPort.checkAddress(checkAddressesModel);
    }

    @Override
    public MuenchenAdresseResponse listAddresses(ListAddressesModel listAddressesModel) throws BpmnError, IncidentError {
        return this.addressClientOutPort.listAddresses(listAddressesModel);
    }

    @Override
    public AenderungResponse listChanges(ListAddressChangesModel listAddressChangesModel) throws BpmnError, IncidentError {
        return this.addressClientOutPort.listChanges(listAddressChangesModel);
    }

    @Override
    public MuenchenAdresseResponse searchAddresses(SearchAddressesModel searchAddressesModel) throws BpmnError, IncidentError {
        return this.addressClientOutPort.searchAddresses(searchAddressesModel);
    }

    @Override
    public AddressDistancesModel searchAddressesGeo(SearchAddressesGeoModel searchAddressesGeoModel) throws BpmnError, IncidentError {
        return this.addressClientOutPort.searchAddressesGeo(searchAddressesGeoModel);
    }

}
