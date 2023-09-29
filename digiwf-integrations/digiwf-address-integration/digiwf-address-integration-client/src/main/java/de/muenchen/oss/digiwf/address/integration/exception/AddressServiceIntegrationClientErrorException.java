package de.muenchen.oss.digiwf.address.integration.exception;

public class AddressServiceIntegrationClientErrorException extends Exception {

    public AddressServiceIntegrationClientErrorException(final String message, final Exception exception) {
        super(message, exception);
    }

}
