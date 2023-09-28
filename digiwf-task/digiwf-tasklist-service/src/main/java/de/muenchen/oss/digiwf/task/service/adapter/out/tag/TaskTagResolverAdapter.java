package de.muenchen.oss.digiwf.task.service.adapter.out.tag;

import de.muenchen.oss.digiwf.task.TaskVariables;
import de.muenchen.oss.digiwf.task.service.application.port.out.tag.TaskTagResolverPort;
import io.holunda.polyflow.view.Task;
import lombok.val;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class TaskTagResolverAdapter implements TaskTagResolverPort {
    @Override
    public Optional<String> apply(Task task) {
        val object =  task.getPayload().getOrDefault(TaskVariables.TASK_TAG.getName(), null);
        if(object != null) {
            return Optional.of(object.toString());
        }
        return Optional.empty();
    }
}
