package io.muenchendigital.digiwf.task.service.application.port.out.schema;

import io.muenchendigital.digiwf.task.service.domain.JsonSchema;

import java.util.Map;

/**
 * Port to access the schema.
 */
public interface JsonSchemaPort {

  /**
   * Load schema by id.
   * @param schemaId id of the schema.
   * @return schema
   * @throws JsonSchemaNotFoundException if no schema is available or access is restricted.
   */
  JsonSchema getSchemaById(String schemaId) throws JsonSchemaNotFoundException;

}
