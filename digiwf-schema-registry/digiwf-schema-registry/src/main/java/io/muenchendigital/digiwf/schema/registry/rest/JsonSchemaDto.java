package io.muenchendigital.digiwf.schema.registry.rest;

import lombok.Data;

import java.util.Map;

@Data
public class JsonSchemaDto {

    private String key;

    private Map<String, Object> schema;
}
