package de.muenchen.oss.digiwf.okewo.integration.exception;

public class OkEwoIntegrationServerErrorException extends Exception {

    public OkEwoIntegrationServerErrorException(final String message, final Exception exception) {
        super(message, exception);
    }

}
