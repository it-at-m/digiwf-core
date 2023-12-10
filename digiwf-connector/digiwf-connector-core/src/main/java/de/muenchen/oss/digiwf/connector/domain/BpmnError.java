package de.muenchen.oss.digiwf.connector.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Setter
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BpmnError {

    @NotBlank
    private String processInstanceId;

    @NotBlank
    private String messageName;

    private String errorCode;

    private String errorMessage;

}
