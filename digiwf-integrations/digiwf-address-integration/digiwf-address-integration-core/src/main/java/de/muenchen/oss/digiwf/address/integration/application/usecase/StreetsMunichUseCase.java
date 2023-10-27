package de.muenchen.oss.digiwf.address.integration.application.usecase;

import de.muenchen.oss.digiwf.address.integration.application.port.in.StreetsMunichInPort;
import de.muenchen.oss.digiwf.address.integration.application.port.out.AddressClientOutPort;
import de.muenchen.oss.digiwf.address.integration.client.gen.model.Strasse;
import de.muenchen.oss.digiwf.address.integration.client.gen.model.StrasseResponse;
import de.muenchen.oss.digiwf.address.integration.client.model.request.ListStreetsModel;
import de.muenchen.oss.digiwf.message.process.api.error.BpmnError;
import de.muenchen.oss.digiwf.message.process.api.error.IncidentError;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StreetsMunichUseCase implements StreetsMunichInPort {

    private final AddressClientOutPort addressClientOutPort;

    @Override
    public Strasse findStreetsById(long streetId) throws BpmnError, IncidentError {
        return this.addressClientOutPort.findStreetsById(streetId);
    }

    @Override
    public StrasseResponse listStreets(ListStreetsModel listStreetsModel) throws BpmnError, IncidentError {
        return this.addressClientOutPort.listStreets(listStreetsModel);
    }
}
