package io.muenchendigital.digiwf.okewo.integration.exception;

public class OkEwoIntegrationServerErrorException extends Exception {

    public OkEwoIntegrationServerErrorException(final String message, final Exception exception) {
        super(message, exception);
    }

}
