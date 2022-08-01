package io.muenchendigital.digiwf.connector.api.bpmnerror;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@Builder
@ToString
@AllArgsConstructor
public class BpmnErrorEvent implements BpmnError {

    private String processInstanceId;

    @NotBlank
    private String messageName;

    private String errorCode;

    private String errorMessage;

}
