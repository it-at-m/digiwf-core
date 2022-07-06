package io.muenchendigital.digiwf.connector.jsonschema.internal.impl.mapper;

import io.muenchendigital.digiwf.connector.jsonschema.api.JsonSchema;
import io.muenchendigital.digiwf.connector.jsonschema.internal.impl.model.JsonSchemaImpl;
import org.mapstruct.Mapper;

@Mapper
public interface JsonSchemaMapper {

    JsonSchemaImpl map(JsonSchema jsonSchema);

}
