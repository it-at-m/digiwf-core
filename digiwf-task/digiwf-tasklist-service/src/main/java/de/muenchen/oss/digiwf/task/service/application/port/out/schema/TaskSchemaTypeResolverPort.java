package de.muenchen.oss.digiwf.task.service.application.port.out.schema;

import de.muenchen.oss.digiwf.task.TaskSchemaType;
import io.holunda.polyflow.view.Task;

import java.util.function.Function;

/**
 * Resolves the schema type for task.
 */
public interface TaskSchemaTypeResolverPort extends Function<Task, TaskSchemaType> {

}
