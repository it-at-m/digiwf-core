package de.muenchen.oss.digiwf.address.integration.application.port.in;

import de.muenchen.oss.digiwf.address.integration.client.gen.model.Strasse;
import de.muenchen.oss.digiwf.address.integration.client.gen.model.StrasseResponse;
import de.muenchen.oss.digiwf.address.integration.client.model.request.ListStreetsModel;
import de.muenchen.oss.digiwf.message.process.api.error.BpmnError;
import de.muenchen.oss.digiwf.message.process.api.error.IncidentError;

public interface StreetsMunichInPort {

    Strasse findStreetsById(final long streetId) throws BpmnError, IncidentError;

    StrasseResponse listStreets(final ListStreetsModel listStreetsModel) throws BpmnError, IncidentError;

}
