package de.muenchen.oss.digiwf.address.integration.adapter.out.address.client.exception;

public class AddressServiceIntegrationClientErrorException extends Exception {

    public AddressServiceIntegrationClientErrorException(final String message, final Exception exception) {
        super(message, exception);
    }

}
