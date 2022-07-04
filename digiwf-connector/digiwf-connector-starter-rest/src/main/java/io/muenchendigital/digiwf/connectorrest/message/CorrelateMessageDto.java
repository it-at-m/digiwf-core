package io.muenchendigital.digiwf.connectorrest.message;

import io.muenchendigital.digiwf.connector.message.api.CorrelateMessage;
import lombok.Data;

import java.util.Map;

@Data
public class CorrelateMessageDto implements CorrelateMessage {

    private String processInstanceId;

    private String messageName;

    private String businessKey;

    private Map<String, Object> payloadVariables;

    private Map<String, Object> payloadVariablesLocal;

}
