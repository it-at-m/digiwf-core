package de.muenchen.oss.digiwf.task.service.application.port.out.links;

import de.muenchen.oss.digiwf.task.service.domain.TaskLink;
import io.holunda.polyflow.view.Task;

import java.util.List;
import java.util.function.Function;

/**
 * Port to resolve external task links.
 */
public interface TaskLinkResolverPort extends Function<Task, List<TaskLink>> {
}
