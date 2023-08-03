package de.muenchen.oss.digiwf.schema.registry.api;

public interface JsonSchemaService {

    JsonSchema createJsonSchema(JsonSchema jsonSchema);

    JsonSchema getByKey(String key);
}
