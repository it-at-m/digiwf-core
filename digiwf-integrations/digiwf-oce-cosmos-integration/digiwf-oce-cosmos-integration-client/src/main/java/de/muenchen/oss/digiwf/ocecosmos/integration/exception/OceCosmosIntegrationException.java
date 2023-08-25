package de.muenchen.oss.digiwf.ocecosmos.integration.exception;

public class OceCosmosIntegrationException extends Exception {

    public OceCosmosIntegrationException(final String message, final Exception exception) {
        super(message, exception);
    }

}
