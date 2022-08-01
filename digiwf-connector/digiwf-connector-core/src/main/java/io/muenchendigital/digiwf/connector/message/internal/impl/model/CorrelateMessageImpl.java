package io.muenchendigital.digiwf.connector.message.internal.impl.model;

import io.muenchendigital.digiwf.connector.api.message.CorrelateMessage;
import lombok.*;

import java.util.Map;

@Setter
@Getter
@Builder
@ToString
@AllArgsConstructor
public class CorrelateMessageImpl implements CorrelateMessage {

    private String processInstanceId;

    private String messageName;

    private String businessKey;

    private Map<String, Object> payloadVariables;

    private Map<String, Object> payloadVariablesLocal;

}
