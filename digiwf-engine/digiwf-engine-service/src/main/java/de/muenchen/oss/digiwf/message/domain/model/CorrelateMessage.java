package de.muenchen.oss.digiwf.message.domain.model;

import lombok.*;

import java.util.Map;

@Setter
@Getter
@Builder
@ToString
@AllArgsConstructor
public class CorrelateMessage {

    private String processInstanceId;

    private String messageName;

    private String businessKey;

    private Map<String, Object> correlationVariables;

    private Map<String, Object> correlationVariablesLocal;

    private Map<String, Object> payloadVariables;

    private Map<String, Object> payloadVariablesLocal;

}
