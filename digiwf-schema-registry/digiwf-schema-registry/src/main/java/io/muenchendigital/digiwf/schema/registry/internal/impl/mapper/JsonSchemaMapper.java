package io.muenchendigital.digiwf.schema.registry.internal.impl.mapper;

import io.muenchendigital.digiwf.schema.registry.api.JsonSchema;
import io.muenchendigital.digiwf.schema.registry.internal.impl.model.JsonSchemaImpl;
import org.mapstruct.Mapper;

@Mapper
public interface JsonSchemaMapper {

    JsonSchemaImpl map(JsonSchema jsonSchema);

}
