package io.muenchendigital.digiwf.message.example.process.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StartProcessDto {
    private String key;
    private Map<String, Object> variables;
}
