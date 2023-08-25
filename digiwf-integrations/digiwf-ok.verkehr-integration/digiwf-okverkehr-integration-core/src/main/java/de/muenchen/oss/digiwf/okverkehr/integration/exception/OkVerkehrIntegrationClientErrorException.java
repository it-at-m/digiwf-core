package de.muenchen.oss.digiwf.okverkehr.integration.exception;

public class OkVerkehrIntegrationClientErrorException extends Exception {
    public OkVerkehrIntegrationClientErrorException(final String message, final Exception exception) {
        super(message, exception);
    }
}
