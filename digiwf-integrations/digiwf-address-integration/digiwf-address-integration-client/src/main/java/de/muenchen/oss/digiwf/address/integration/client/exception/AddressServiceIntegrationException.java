package de.muenchen.oss.digiwf.address.integration.client.exception;

public class AddressServiceIntegrationException extends Exception {

    public AddressServiceIntegrationException(final String message, final Exception exception) {
        super(message, exception);
    }

}
