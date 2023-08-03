package de.muenchen.oss.digiwf.connector.api.bpmnerror;

public interface BpmnError {

    String getProcessInstanceId();

    String getMessageName();

    String getErrorCode();

    String getErrorMessage();

}
