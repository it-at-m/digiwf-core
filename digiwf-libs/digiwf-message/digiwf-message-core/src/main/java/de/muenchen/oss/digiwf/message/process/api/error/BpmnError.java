package de.muenchen.oss.digiwf.message.process.api.error;

import lombok.Getter;

/**
 * Exception to be thrown when an integration fails with a bpmn error that may be handled by the caller process.
 */
@Getter
public class BpmnError extends RuntimeException {

    private final String errorCode;
    private final String errorMessage;

    public BpmnError(String errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
