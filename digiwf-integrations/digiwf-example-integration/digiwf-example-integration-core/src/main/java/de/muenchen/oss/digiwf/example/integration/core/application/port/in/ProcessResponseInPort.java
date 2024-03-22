package de.muenchen.oss.digiwf.example.integration.core.application.port.in;

import de.muenchen.oss.digiwf.message.process.api.error.BpmnError;
import de.muenchen.oss.digiwf.message.process.api.error.IncidentError;

import java.util.Map;

public interface ProcessResponseInPort {
    void correlateMessage(final Map<String, Object> originMessageHeaders, final Map<String, Object> message);

    boolean handleBpmnError(final Map<String, Object> originMessageHeaders, final BpmnError bpmnError);

    boolean handleIncident(final Map<String, Object> originMessageHeaders, final IncidentError incidentError);
}
