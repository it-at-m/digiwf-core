package de.muenchen.oss.digiwf.message.process.api.error;

import lombok.EqualsAndHashCode;
import lombok.Value;

/**
 * Exception to be thrown when an integration fails with a bpmn error that may be handled by the caller process.
 */
@EqualsAndHashCode(callSuper = false)
@Value
public class BpmnError extends RuntimeException {

    String errorCode;
    String errorMessage;

    public BpmnError(String errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
