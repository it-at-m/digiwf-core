package de.muenchen.oss.digiwf.example.integration.core.application.port.out;

import de.muenchen.oss.digiwf.message.process.api.error.BpmnError;
import de.muenchen.oss.digiwf.message.process.api.error.IncidentError;

import java.util.Map;

public interface ProcessResponseOutPort {
    void correlateMessage(Map<String, Object> originMessageHeaders, Map<String, Object> message);

    boolean handleBpmnError(Map<String, Object> originMessageHeaders, BpmnError bpmnError);

    boolean handleIncident(Map<String, Object> originMessageHeaders, IncidentError incidentError);
}
