package de.muenchen.oss.digiwf.connector.core.application.port.in;

public interface CreateIncidentInPort {

    /**
     * Create an incident
     *
     * @param processInstanceId id of the process instance
     * @param integrationName   name of the integration that should be answered
     * @param messageContent    optional content of message for provide detailed information
     */
    void createIncident(String processInstanceId, String integrationName, String messageContent);

}
