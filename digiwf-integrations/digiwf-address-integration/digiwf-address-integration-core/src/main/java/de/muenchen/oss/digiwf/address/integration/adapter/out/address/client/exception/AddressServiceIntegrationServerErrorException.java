package de.muenchen.oss.digiwf.address.integration.adapter.out.address.client.exception;

public class AddressServiceIntegrationServerErrorException extends Exception {

    public AddressServiceIntegrationServerErrorException(final String message, final Exception exception) {
        super(message, exception);
    }

}
