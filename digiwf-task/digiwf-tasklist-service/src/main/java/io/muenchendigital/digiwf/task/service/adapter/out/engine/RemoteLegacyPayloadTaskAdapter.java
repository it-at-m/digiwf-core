package io.muenchendigital.digiwf.task.service.adapter.out.engine;

import io.muenchendigital.digiwf.task.service.application.port.out.engine.LegacyPayloadTaskCommandPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @deprecated  legacy adapter to support old schema tasks.
 * Will be removed as soon as all processes have been migrated to schema-based forms.
 */
@Deprecated
@Component
@RequiredArgsConstructor
public class RemoteLegacyPayloadTaskAdapter implements LegacyPayloadTaskCommandPort {

  private final LegacyTaskClient legacyTaskClient;

  @Override
  public void completeOldSchemaUserTask(String taskId, Map<String, Object> payload) {
    legacyTaskClient.completeTask(new LegacyTaskClient.CompleteTO(taskId, payload));
  }

  @Override
  public void saveOldSchemaUserTask(String taskId, Map<String, Object> payload) {
    legacyTaskClient.saveTask(new LegacyTaskClient.SaveTO(taskId, payload));
  }
}
