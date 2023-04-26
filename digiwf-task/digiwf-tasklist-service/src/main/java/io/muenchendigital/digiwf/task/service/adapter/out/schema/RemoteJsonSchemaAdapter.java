package io.muenchendigital.digiwf.task.service.adapter.out.schema;

import io.muenchendigital.digiwf.task.service.application.port.out.schema.JsonSchemaNotFoundException;
import io.muenchendigital.digiwf.task.service.application.port.out.schema.JsonSchemaPort;
import io.muenchendigital.digiwf.task.service.domain.JsonSchema;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RemoteJsonSchemaAdapter implements JsonSchemaPort {

  private final JsonSchemaClient jsonSchemaClient;
  @Override
  public JsonSchema getSchemaById(String schemaId) throws JsonSchemaNotFoundException {
    return JsonSchema.of(schemaId, jsonSchemaClient.getSchemaById(schemaId));
  }
}
