package de.muenchen.oss.digiwf.connector.core.application.port.out;

import reactor.util.annotation.Nullable;

public interface CreateIncidentOutPort {

    /**
     * Create a incident
     *
     * @param processInstanceId id of the process instance
     * @param integrationName   name of the integration that should be answered
     * @param messageContent    optional content of message for provide detailed information
     */
    void createIncident(String processInstanceId, String integrationName, @Nullable String messageContent);

}
