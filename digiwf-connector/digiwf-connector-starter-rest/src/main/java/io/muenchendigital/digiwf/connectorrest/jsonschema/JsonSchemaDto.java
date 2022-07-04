package io.muenchendigital.digiwf.connectorrest.jsonschema;

import io.muenchendigital.digiwf.connector.jsonschema.api.JsonSchema;
import lombok.Data;

@Data
public class JsonSchemaDto implements JsonSchema {

    private String key;

    private String schema;
}
