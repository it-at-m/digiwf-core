package io.muenchendigital.digiwf.task.service.application.port.out.schema;

import io.muenchendigital.digiwf.task.service.domain.legacy.Form;

import java.util.Map;

/**
 * Port to access Legacy schema validation services.
 */
public interface LegacyFormValidationPort {

  /**
   * Filters variables by legacy schema.
   *
   * @param variables data to filter
   * @param form schema to use for filtering
   * @return filtered data
   */
   Map<String, Object> filterVariables(Map<String, Object> variables, Form form);

}
