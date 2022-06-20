package io.muenchendigital.digiwf.enginerest.jsonschema;

import io.muenchendigital.digiwf.engine.jsonschema.api.JsonSchema;
import lombok.Data;

@Data
public class JsonSchemaDto implements JsonSchema {

    private String key;

    private String schema;
}
