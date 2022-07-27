package io.muenchendigital.digiwf.json.serialization.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class DataDto {
    private String schema;
    private Map<String, Object> data;
    private Map<String, Object> previousData;
}
