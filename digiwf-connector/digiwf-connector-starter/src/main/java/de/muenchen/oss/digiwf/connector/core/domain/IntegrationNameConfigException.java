package de.muenchen.oss.digiwf.connector.core.domain;

import lombok.Getter;

@Getter
public class IntegrationNameConfigException extends RuntimeException {

    private final String detailedMessage;

    public IntegrationNameConfigException(final String message, final String integrationName) {
        super(message);
        this.detailedMessage = "Integration " + integrationName + " is not registered in configuration. Make sure to register it in configuration or provide a custom topic.";
    }

}
