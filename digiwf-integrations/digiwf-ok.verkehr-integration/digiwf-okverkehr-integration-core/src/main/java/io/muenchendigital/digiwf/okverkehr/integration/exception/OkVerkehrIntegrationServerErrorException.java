package io.muenchendigital.digiwf.okverkehr.integration.exception;

public class OkVerkehrIntegrationServerErrorException extends Exception {
    public OkVerkehrIntegrationServerErrorException(final String message, final Exception exception) {
        super(message, exception);
    }
}
