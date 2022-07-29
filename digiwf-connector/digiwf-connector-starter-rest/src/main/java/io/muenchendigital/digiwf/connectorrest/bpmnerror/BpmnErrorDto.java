package io.muenchendigital.digiwf.connectorrest.bpmnerror;

import io.muenchendigital.digiwf.connector.bpmnerror.api.BpmnError;
import lombok.Data;

@Data
public class BpmnErrorDto implements BpmnError {

    private String processInstanceId;

    private String messageName;

    private String errorCode;

    private String errorMessage;

}
