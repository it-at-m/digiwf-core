package io.muenchendigital.digiwf.task.service.domain;

import io.holunda.polyflow.view.Task;
import lombok.Data;

@Data
public class TaskWithSchema {
  private final Task task;
  private final JsonSchema schema;
  private final boolean cancelable;
}
