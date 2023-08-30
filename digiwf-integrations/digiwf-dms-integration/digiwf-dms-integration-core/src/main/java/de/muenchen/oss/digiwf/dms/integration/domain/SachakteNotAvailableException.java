package de.muenchen.oss.digiwf.dms.integration.domain;

import de.muenchen.oss.digiwf.message.process.api.error.BpmnError;

public class SachakteNotAvailableException extends BpmnError {
    public SachakteNotAvailableException(String errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }
}
