package de.muenchen.oss.digiwf.ocecosmos.integration.exception;

public class OceCosmosIntegrationServerErrorException extends Exception {

    public OceCosmosIntegrationServerErrorException(final String message, final Exception exception) {
        super(message, exception);
    }

    public OceCosmosIntegrationServerErrorException(final String message) {
        super(message);
    }

}
