package io.muenchendigital.digiwf.task.service.application.port.out.engine;

import io.muenchendigital.digiwf.task.service.domain.legacy.Form;

import java.util.Map;

/**
 * @deprecated  legacy adapter to support old schema tasks.
 * Will be removed as soon as all processes have been migrated to schema-based forms.
 */
@Deprecated
public interface LegacyPayloadTaskCommandPort {
  /**
   * Complete user task running old schema (vuetify form base).
   * @param taskId id of the task.
   * @param payload payload to use on completion.
   */
  void completeOldSchemaUserTask(String taskId, Map<String, Object> payload);

  /**
   * Complete user task running old schema (vuetify form base).
   * @param taskId id of the task.
   * @param payload payload to use on completion.
   */
  void saveOldSchemaUserTask(String taskId, Map<String, Object> payload);

  /**
   * Loads form data by task id.
   * @param taskId id of the task.
   * @return form data of the task.
   */
  Form loadFormById(String taskId);
}
