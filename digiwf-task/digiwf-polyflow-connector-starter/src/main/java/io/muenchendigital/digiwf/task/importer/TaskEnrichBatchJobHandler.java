package io.muenchendigital.digiwf.task.importer;

import io.muenchendigital.digiwf.task.listener.AssignmentCreateTaskListener;
import io.muenchendigital.digiwf.task.listener.CancelableTaskStatusCreateTaskListener;
import io.muenchendigital.digiwf.task.listener.TaskDescriptionCreateTaskListener;
import io.muenchendigital.digiwf.task.listener.TaskSchemaTypeCreateTaskListener;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.impl.cfg.ProcessEnginePlugin;
import org.camunda.bpm.engine.impl.interceptor.CommandContext;
import org.camunda.bpm.engine.impl.persistence.entity.TaskEntity;
import org.camunda.community.batch.CustomBatchJobHandler;
import org.camunda.community.batch.plugin.CustomBatchHandlerPlugin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class TaskEnrichBatchJobHandler extends CustomBatchJobHandler<String> {
    private final TaskService taskService;

    private final AssignmentCreateTaskListener assignmentCreateTaskListener;
    private final CancelableTaskStatusCreateTaskListener cancelableTaskStatusCreateTaskListener;
    private final TaskSchemaTypeCreateTaskListener taskSchemaTypeCreateTaskListener;
    private final TaskDescriptionCreateTaskListener taskDescriptionCreateTaskListener;

    public TaskEnrichBatchJobHandler(
            @Lazy TaskService taskService,
            AssignmentCreateTaskListener assignmentCreateTaskListener,
            CancelableTaskStatusCreateTaskListener cancelableTaskStatusCreateTaskListener,
            TaskSchemaTypeCreateTaskListener taskSchemaTypeCreateTaskListener,
            TaskDescriptionCreateTaskListener taskDescriptionCreateTaskListener
    ) {
        this.taskService = taskService;
        this.assignmentCreateTaskListener = assignmentCreateTaskListener;
        this.cancelableTaskStatusCreateTaskListener = cancelableTaskStatusCreateTaskListener;
        this.taskSchemaTypeCreateTaskListener = taskSchemaTypeCreateTaskListener;
        this.taskDescriptionCreateTaskListener = taskDescriptionCreateTaskListener;
    }

    @Override
    public void execute(List<String> taskIds, CommandContext commandContext) {
        log.info("Beginning enrichment for {} tasks", taskIds.size());
        val tasks = taskService.createTaskQuery()
                .taskIdIn(taskIds.toArray(new String[0]))
                .list().stream()
                .map(task -> ((TaskEntity) task)).collect(Collectors.toList());
        tasks.forEach((task) -> {
            assignmentCreateTaskListener.taskCreated(task);
            cancelableTaskStatusCreateTaskListener.taskCreated(task);
            taskSchemaTypeCreateTaskListener.taskCreated(task);
            taskDescriptionCreateTaskListener.taskCreated(task);
            taskService.saveTask(task);
        });
        log.info("Enrichment of {} tasks finished", tasks.size());
    }

    @Override
    public String getType() {
        return "dwf-task-enrich-batch-handler";
    }

    @Bean
    public ProcessEnginePlugin customBatchHandlerPlugin(TaskEnrichBatchJobHandler taskEnrichBatchJobHandler) {
        return CustomBatchHandlerPlugin.of(taskEnrichBatchJobHandler);
    }
}
