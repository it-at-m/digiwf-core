package io.muenchendigital.digiwf.task.service.application.port.out.schema;

import io.holunda.polyflow.view.Task;
import io.muenchendigital.digiwf.task.TaskSchemaType;

import java.util.function.Function;

/**
 * Resolves the schema type for task.
 */
public interface TaskSchemaTypeResolverPort extends Function<Task, TaskSchemaType> {

}
