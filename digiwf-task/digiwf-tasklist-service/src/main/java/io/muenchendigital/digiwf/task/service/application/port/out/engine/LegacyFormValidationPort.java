package io.muenchendigital.digiwf.task.service.application.port.out.engine;

import io.muenchendigital.digiwf.task.service.domain.legacy.Form;

import java.util.Map;

/**
 * @deprecated  legacy adapter to support old schema tasks.
 * Will be removed as soon as all processes have been migrated to schema-based forms.
 */
@Deprecated
public interface LegacyFormValidationPort {

  /**
   * Filters variables by legacy form.
   *
   * @param variables data to filter
   * @param form form to use for filtering
   * @return filtered data
   */
   Map<String, Object> filterVariables(Map<String, Object> variables, Form form);

}
