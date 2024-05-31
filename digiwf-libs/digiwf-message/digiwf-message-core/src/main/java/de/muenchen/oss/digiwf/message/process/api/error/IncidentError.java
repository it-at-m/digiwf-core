package de.muenchen.oss.digiwf.message.process.api.error;

import lombok.EqualsAndHashCode;
import lombok.Value;

/**
 * Exception to be thrown when an integration fails with an incident that should be created.
 */
@EqualsAndHashCode(callSuper = false)
@Value
public class IncidentError extends RuntimeException {

    String errorMessage;

    public IncidentError(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }
}
