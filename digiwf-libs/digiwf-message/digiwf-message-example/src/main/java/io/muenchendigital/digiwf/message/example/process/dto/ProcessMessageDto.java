package io.muenchendigital.digiwf.message.example.process.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProcessMessageDto {
    private String processInstanceId;
    private String messageName;
    private Map<String, Object> variables;
}
