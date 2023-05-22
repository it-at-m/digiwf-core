package io.muenchendigital.digiwf.task.service.domain;

import io.holunda.polyflow.view.Task;
import io.muenchendigital.digiwf.task.TaskSchemaType;
import lombok.Data;
import org.springframework.lang.NonNull;

import java.util.Map;

@Data
public class TaskWithSchema {
  @NonNull
  private final Task task;
  @NonNull
  private final boolean cancelable;
  @NonNull
  private final TaskSchemaType taskSchemaType;

  private final JsonSchema schema;
  private final Map<String, Object> legacyForm;
}
