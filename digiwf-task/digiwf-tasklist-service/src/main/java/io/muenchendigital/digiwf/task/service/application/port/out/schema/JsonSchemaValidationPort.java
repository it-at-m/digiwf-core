package io.muenchendigital.digiwf.task.service.application.port.out.schema;

import io.holunda.polyflow.view.Task;
import io.muenchendigital.digiwf.task.service.domain.JsonSchema;

import java.util.Map;

/**
 * Port to access JSON validation services.
 */
public interface JsonSchemaValidationPort {

  /**
   * Validates variables and returns the serialized version of them.
   * @param schema schema to validate the input
   * @param task user task
   * @param variables variables to validate and serialize.
   * @return serialized and validated version.
   */
  Map<String, Object> validateAndSerialize(JsonSchema schema, Task task, Map<String, Object> variables);

  /**
   * Filters variables by schema.
   *
   * @param data data to filter
   * @param schema schema to use for filtering
   * @return filtered data
   */
   Map<String, Object> filterVariables(final Map<String, Object> data, JsonSchema schema);

}
