package de.muenchen.oss.digiwf.address.service.integration.exception;

public class AddressServiceIntegrationException extends Exception {

    public AddressServiceIntegrationException(final String message, final Exception exception) {
        super(message, exception);
    }

}
