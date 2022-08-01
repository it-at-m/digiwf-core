package io.muenchendigital.digiwf.connector.api.bpmnerror;

public interface BpmnError {

    String getProcessInstanceId();

    String getMessageName();

    String getErrorCode();

    String getErrorMessage();

}
