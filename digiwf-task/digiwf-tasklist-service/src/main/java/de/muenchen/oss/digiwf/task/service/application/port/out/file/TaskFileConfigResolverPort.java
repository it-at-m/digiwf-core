package de.muenchen.oss.digiwf.task.service.application.port.out.file;

import io.holunda.polyflow.view.Task;
import de.muenchen.oss.digiwf.task.service.domain.TaskFileConfig;

import java.util.function.Function;

/**
 * Resolves the files variables for task.
 */
public interface TaskFileConfigResolverPort extends Function<Task, TaskFileConfig> {
}
