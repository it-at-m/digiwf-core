package de.muenchen.oss.digiwf.address.integration.application.port.in;

import de.muenchen.oss.digiwf.address.integration.client.gen.model.Strasse;
import de.muenchen.oss.digiwf.address.integration.client.gen.model.StrasseResponse;
import de.muenchen.oss.digiwf.address.integration.client.model.request.ListStreetsModel;
import de.muenchen.oss.digiwf.message.process.api.error.BpmnError;
import de.muenchen.oss.digiwf.message.process.api.error.IncidentError;

/**
 * Port to integration infrastructure.
 */
public interface StreetsMunichInPort {

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
