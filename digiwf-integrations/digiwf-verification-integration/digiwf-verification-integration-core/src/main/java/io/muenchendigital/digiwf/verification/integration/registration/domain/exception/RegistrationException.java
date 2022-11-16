package io.muenchendigital.digiwf.verification.integration.registration.domain.exception;

public class RegistrationException extends Exception {

    private static final long serialVersionUID = -8830142200673439453L;

    public RegistrationException(final String message, final Exception exception) {
        super(message, exception);
    }

    public RegistrationException(final String message) {
        super(message);
    }

}
