package io.muenchendigital.digiwf.connector.api.message;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.Map;

@Setter
@Getter
@Builder
@ToString
@AllArgsConstructor
public class CorrelateMessageEvent implements CorrelateMessage {


    private String processInstanceId;

    @NotBlank
    private String messageName;

    private String businessKey;

    private Map<String, Object> payloadVariables;

    private Map<String, Object> payloadVariablesLocal;

}
