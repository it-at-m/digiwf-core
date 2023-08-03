package de.muenchen.oss.digiwf.task.service.application.port.out.schema;

import de.muenchen.oss.digiwf.task.service.domain.JsonSchema;

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
