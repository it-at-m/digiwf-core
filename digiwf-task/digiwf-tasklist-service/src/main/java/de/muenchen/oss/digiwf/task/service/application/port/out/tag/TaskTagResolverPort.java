package de.muenchen.oss.digiwf.task.service.application.port.out.tag;

import io.holunda.polyflow.view.Task;

import java.util.Optional;
import java.util.function.Function;

/**
 * resolves the tag of the given task
 */
public interface TaskTagResolverPort extends Function<Task, Optional<String>> {
}
