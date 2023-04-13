package io.muenchendigital.digiwf.message.process.impl.dto;

import lombok.*;

import java.util.Map;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class StartProcessDto {
    private String key;
    private String fileContext;
    private Map<String, Object> data;
}
