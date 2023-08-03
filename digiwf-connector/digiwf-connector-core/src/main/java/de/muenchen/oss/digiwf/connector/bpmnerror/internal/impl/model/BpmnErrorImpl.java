package de.muenchen.oss.digiwf.connector.bpmnerror.internal.impl.model;

import de.muenchen.oss.digiwf.connector.api.bpmnerror.BpmnError;
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
