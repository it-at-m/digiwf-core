package de.muenchen.oss.digiwf.task.service.domain;

import de.muenchen.oss.digiwf.task.TaskSchemaType;
import de.muenchen.oss.digiwf.task.service.domain.legacy.Form;
import io.holunda.polyflow.view.Task;
import lombok.Data;
import org.springframework.lang.NonNull;

@Data
public class TaskWithSchema {
  @NonNull
  private final Task task;
  @NonNull
  private final boolean cancelable;
  @NonNull
  private final TaskSchemaType taskSchemaType;

  private final JsonSchema schema;
  private final Form legacyForm;
}
