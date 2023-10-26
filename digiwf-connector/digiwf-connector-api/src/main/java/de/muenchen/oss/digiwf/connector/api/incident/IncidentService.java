package de.muenchen.oss.digiwf.connector.api.incident;

import reactor.util.annotation.Nullable;

/**
 * Service to create incidents in the digiwf engine.
 */
public interface IncidentService {

    /**
     * Create a incident
     *
     * @param processInstanceId id of the process instance
     * @param messageName       name of the message that should be answered
     * @param messageContent    optional content of message for provide detailed information
     */
    void createIncident(String processInstanceId, String messageName, @Nullable String messageContent);

}
