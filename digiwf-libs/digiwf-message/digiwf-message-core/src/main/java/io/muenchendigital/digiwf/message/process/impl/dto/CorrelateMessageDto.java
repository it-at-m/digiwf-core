package io.muenchendigital.digiwf.message.process.impl.dto;

import lombok.*;

import java.util.Map;

@Data
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
