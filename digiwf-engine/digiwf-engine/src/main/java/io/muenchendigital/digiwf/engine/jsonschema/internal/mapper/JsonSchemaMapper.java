package io.muenchendigital.digiwf.engine.jsonschema.internal.mapper;

import io.muenchendigital.digiwf.engine.jsonschema.api.JsonSchema;
import io.muenchendigital.digiwf.engine.jsonschema.internal.model.JsonSchemaImpl;
import org.mapstruct.Mapper;

@Mapper
public interface JsonSchemaMapper {

    JsonSchemaImpl map(JsonSchema jsonSchema);

}
