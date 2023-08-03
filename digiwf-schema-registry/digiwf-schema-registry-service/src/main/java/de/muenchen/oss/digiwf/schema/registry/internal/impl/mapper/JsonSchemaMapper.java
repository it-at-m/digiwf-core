package de.muenchen.oss.digiwf.schema.registry.internal.impl.mapper;

import de.muenchen.oss.digiwf.schema.registry.api.JsonSchema;
import de.muenchen.oss.digiwf.schema.registry.internal.impl.model.JsonSchemaImpl;
import org.mapstruct.Mapper;

@Mapper
public interface JsonSchemaMapper {

    JsonSchemaImpl map(JsonSchema jsonSchema);

}
