package io.muenchendigital.digiwf.enginestreaming.message;

import io.muenchendigital.digiwf.engine.message.api.CorrelateMessage;
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
