package de.muenchen.oss.digiwf.message.process.api.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Exception to be thrown when an integration fails with a bpmn error that may be handled by the caller process.
 */
@AllArgsConstructor
@Getter
public class BpmnError extends RuntimeException {

    private final String errorCode;
    private final String errorMessage;

}
