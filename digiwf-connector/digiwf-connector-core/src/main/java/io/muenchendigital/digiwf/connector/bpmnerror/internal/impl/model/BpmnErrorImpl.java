package io.muenchendigital.digiwf.connector.bpmnerror.internal.impl.model;

import io.muenchendigital.digiwf.connector.bpmnerror.api.BpmnError;
import lombok.*;

@Setter
@Getter
@Builder
@ToString
@AllArgsConstructor
public class BpmnErrorImpl implements BpmnError {

    private String processInstanceId;

    private String messageName;

    private String errorCode;

    private String errorMessage;

}
