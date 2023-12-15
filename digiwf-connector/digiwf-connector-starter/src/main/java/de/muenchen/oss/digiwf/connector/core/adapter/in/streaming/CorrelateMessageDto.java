package de.muenchen.oss.digiwf.connector.core.adapter.in.streaming;

import lombok.*;

import java.util.Map;

@Setter
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CorrelateMessageDto {

    private String processInstanceId;

    private String messageName;

    private String businessKey;

    private Map<String, Object> payloadVariables;

    private Map<String, Object> payloadVariablesLocal;

}
