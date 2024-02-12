package de.muenchen.oss.digiwf.message.process.api.error;

import lombok.Getter;

/**
 * Exception to be thrown when an integration fails with an incident that should be created.
 */
@Getter
public class IncidentError extends RuntimeException {

    private final String errorMessage;

    public IncidentError(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }
}
