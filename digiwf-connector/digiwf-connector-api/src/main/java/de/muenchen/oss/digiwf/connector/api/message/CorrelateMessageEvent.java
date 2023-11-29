package de.muenchen.oss.digiwf.connector.api.message;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Map;

@Setter
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CorrelateMessageEvent implements CorrelateMessage {

    private String processInstanceId;

    @NotBlank
    private String messageName;

    private String businessKey;

    private Map<String, Object> payloadVariables;

    private Map<String, Object> payloadVariablesLocal;

}
