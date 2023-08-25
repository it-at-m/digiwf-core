package io.muenchendigital.digiwf.okverkehr.integration.exception;

public class OkVerkehrIntegrationException extends Exception {
    public OkVerkehrIntegrationException(final String message, final Exception exception) {
        super(message, exception);
    }
}
