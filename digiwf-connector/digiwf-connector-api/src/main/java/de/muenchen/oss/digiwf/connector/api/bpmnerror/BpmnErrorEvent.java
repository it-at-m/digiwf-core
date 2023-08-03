package de.muenchen.oss.digiwf.connector.api.bpmnerror;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BpmnErrorEvent implements BpmnError {

    private String processInstanceId;

    @NotBlank
    private String messageName;

    private String errorCode;

    private String errorMessage;

}
