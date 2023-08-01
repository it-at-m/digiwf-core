package io.muenchendigital.digiwf.task.service.application.port.out.file;

import io.holunda.polyflow.view.Task;
import io.muenchendigital.digiwf.task.service.domain.TaskFileConfig;

import java.util.function.Function;

/**
 * Resolves the files variables for task.
 */
public interface TaskFileConfigResolverPort extends Function<Task, TaskFileConfig> {
}
