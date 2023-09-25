package de.muenchen.oss.digiwf.connector.rest.message;

import de.muenchen.oss.digiwf.connector.api.message.CorrelateMessage;
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
