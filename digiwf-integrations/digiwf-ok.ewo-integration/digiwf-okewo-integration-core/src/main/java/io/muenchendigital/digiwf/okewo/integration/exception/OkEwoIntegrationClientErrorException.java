package io.muenchendigital.digiwf.okewo.integration.exception;

public class OkEwoIntegrationClientErrorException extends Exception {

    public OkEwoIntegrationClientErrorException(final String message, final Exception exception) {
        super(message, exception);
    }

}
