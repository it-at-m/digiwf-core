package de.muenchen.oss.digiwf.address.service.integration.exception;

public class AddressServiceIntegrationServerErrorException extends Exception {

    public AddressServiceIntegrationServerErrorException(final String message, final Exception exception) {
        super(message, exception);
    }

}
