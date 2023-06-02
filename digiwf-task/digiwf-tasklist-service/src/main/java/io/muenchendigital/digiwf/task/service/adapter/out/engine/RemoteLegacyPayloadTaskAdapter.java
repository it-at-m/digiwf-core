package io.muenchendigital.digiwf.task.service.adapter.out.engine;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapLikeType;
import io.muenchendigital.digiwf.task.service.application.port.out.engine.LegacyPayloadTaskCommandPort;
import io.muenchendigital.digiwf.task.service.domain.legacy.Form;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
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
  private final ObjectMapper objectMapper;
  private MapLikeType mapType;

  @PostConstruct
  public void initMapType() {
    mapType = objectMapper.getTypeFactory().constructMapLikeType(Map.class, String.class, Object.class);
  }

  @Override
  public void completeOldSchemaUserTask(String taskId, Map<String, Object> payload) {
    legacyTaskClient.completeTask(new LegacyTaskClient.CompleteTO(taskId, payload));
  }

  @Override
  public void saveOldSchemaUserTask(String taskId, Map<String, Object> payload) {
    legacyTaskClient.saveTask(new LegacyTaskClient.SaveTO(taskId, payload));
  }

  @Override
  public Form loadFormById(String formKey) {
    LegacyTaskClient.FormTO form = legacyTaskClient.getForm(formKey);
    return objectMapper.convertValue(form, Form.class);
  }
}
