package io.muenchendigital.digiwf.connector.jsonschema.api;

public interface JsonSchemaService {

    JsonSchema createJsonSchema(JsonSchema jsonSchema);

    JsonSchema getByKey(String key);
}
