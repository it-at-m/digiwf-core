package de.muenchen.oss.digiwf.connector.core.domain;

import lombok.Getter;

@Getter
public class ProcessDefinitionLoadingException extends RuntimeException {

    private final String detailedMessage;

    public ProcessDefinitionLoadingException(final String message, final String detailedMessage) {
        super(message);
        this.detailedMessage = detailedMessage;
    }

}
