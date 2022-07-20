package io.muenchendigital.digiwf.connector.incident.api;

/**
 * Service to create incidents in the digiwf engine.
 */
public interface IncidentService {

    /**
     * Create a incident
     *
     * @param processInstanceId id of the process instance
     * @param messageName       name of the message that should be answered
     */
    void createIncident(String processInstanceId, String messageName);

}
