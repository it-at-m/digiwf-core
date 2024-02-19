package de.muenchen.oss.digiwf.task.service.domain;

import de.muenchen.oss.digiwf.task.TaskSchemaType;
import io.holunda.polyflow.view.Task;
import lombok.Data;
import org.springframework.lang.NonNull;

import java.util.List;

@Data
public class TaskWithSchemaRef {
  private final Task task;
  private final String schemaRef;
  private final boolean cancelable;
  private final TaskSchemaType taskSchemaType;
  private final String tag;
  @NonNull
  private final List<TaskLink> taskLinks;
}
