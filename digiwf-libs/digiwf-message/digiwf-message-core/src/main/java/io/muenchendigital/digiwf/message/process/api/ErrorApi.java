package io.muenchendigital.digiwf.message.process.api;

import io.muenchendigital.digiwf.message.process.api.error.BpmnError;
import io.muenchendigital.digiwf.message.process.api.error.IncidentError;

import java.util.Map;

public interface ErrorApi {

    /**
     * Handles an incident with the specified process instance ID, origin message name, and error message.
     * @param processInstanceId The ID of the process instance associated with the incident.
     * @param originMessageName The name of the message that caused the incident.
     * @param errorMessage The error message associated with the incident.
     * @return true if the incident was successfully handled, false otherwise.
     */
    boolean handleIncident(String processInstanceId, String originMessageName, String errorMessage);

    /**
     * Handles a bpmn error with the specified process instance ID, error code, and error message.
     * @param processInstanceId The ID of the process instance associated with the technical error.
     * @param errorCode The error code associated with the technical error.
     * @param errorMessage The error message associated with the technical error.
     * @return true if the technical error was successfully handled, false otherwise.
     */
    boolean handleBpmnError(String processInstanceId, String errorCode, String errorMessage);

    /**
     * Handles a bpmn error with the specified origin message headers.
     * @param originMessageHeaders The headers of the message that caused the bpmn error.
     * @param bpmnError The bpmn error to be handled.
     * @return true if the bpmn error was successfully handled, false otherwise.
     */
    boolean handleBpmnError(Map<String, Object> originMessageHeaders, BpmnError bpmnError);

    /**
     * Handles an incident error with the specified origin message headers.
     * @param originMessageHeaders The headers of the message that caused the incident.
     * @param incidentError The incident error to be handled.
     * @return true if the incident was successfully handled, false otherwise.
     */
    boolean handleIncident(Map<String, Object> originMessageHeaders, IncidentError incidentError);

}
