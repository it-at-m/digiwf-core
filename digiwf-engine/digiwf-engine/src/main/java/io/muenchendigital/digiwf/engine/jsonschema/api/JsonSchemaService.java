package io.muenchendigital.digiwf.engine.jsonschema.api;

public interface JsonSchemaService {

    JsonSchema createJsonSchema(JsonSchema jsonSchema);

    JsonSchema getByKey(String key);
}
