package io.muenchendigital.digiwf.connector.incident.api;

/**
 * Service to create incidents in the digiwf engine.
 */
public interface IncidentService {

    /**
     * Create a incident
     */
    void createIncident(String refId);

}
