package io.muenchendigital.digiwf.connector.bpmnerror.api;

public interface BpmnError {

    String getProcessInstanceId();

    String getMessageName();

    String getErrorCode();

    String getErrorMessage();

}
