package io.muenchendigital.digiwf.message.process.api.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Exception to be thrown when an integration fails with an incident that should be created.
 */
@AllArgsConstructor
@Getter
public class IncidentError extends RuntimeException {

    private final String errorMessage;

}
