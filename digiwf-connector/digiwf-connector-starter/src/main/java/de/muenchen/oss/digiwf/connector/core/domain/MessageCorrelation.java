package de.muenchen.oss.digiwf.connector.core.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Map;

@Setter
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MessageCorrelation {

    @NotBlank
    private String processInstanceId;

    @NotBlank
    private String messageName;

    private String businessKey;

    private Map<String, Object> payloadVariables;

    private Map<String, Object> payloadVariablesLocal;

}
