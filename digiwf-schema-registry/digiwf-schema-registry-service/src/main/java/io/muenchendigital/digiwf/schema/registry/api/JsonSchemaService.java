package io.muenchendigital.digiwf.schema.registry.api;

public interface JsonSchemaService {

    JsonSchema createJsonSchema(JsonSchema jsonSchema);

    JsonSchema getByKey(String key);
}
