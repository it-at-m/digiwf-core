package io.muenchendigital.digiwf.task.importer;

import io.holunda.polyflow.taskpool.collector.task.TaskServiceCollectorService;
import io.muenchendigital.digiwf.task.listener.AssignmentCreateTaskListener;
import io.muenchendigital.digiwf.task.listener.CancelableTaskStatusCreateTaskListener;
import io.muenchendigital.digiwf.task.listener.TaskDescriptionCreateTaskListener;
import io.muenchendigital.digiwf.task.listener.TaskSchemaTypeCreateTaskListener;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.camunda.bpm.engine.ProcessEngineConfiguration;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.context.ProcessEngineContext;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.impl.persistence.entity.TaskEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.annotation.security.RolesAllowed;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.http.ResponseEntity.internalServerError;
import static org.springframework.http.ResponseEntity.noContent;

@RequiredArgsConstructor
@RestController
@Slf4j
@Transactional
public class TaskImporterService {

    public static final String CLIENT_IMPORT_TASKS = "clientrole_task_importer";
    private final TaskServiceCollectorService taskServiceCollectorService;
    private final TaskService taskService;
    private final ProcessEngineConfiguration processEngineConfiguration;

    private final AssignmentCreateTaskListener assignmentCreateTaskListener;
    private final CancelableTaskStatusCreateTaskListener cancelableTaskStatusCreateTaskListener;
    private final TaskSchemaTypeCreateTaskListener taskSchemaTypeCreateTaskListener;
    private final TaskDescriptionCreateTaskListener taskDescriptionCreateTaskListener;

    @PostConstruct
    void inform() {
      log.warn("Activating TASK ENRICH/IMPORT endpoint");
    }

    @PostMapping("/rest/admin/tasks/enrich")
    @RolesAllowed(CLIENT_IMPORT_TASKS)
    public ResponseEntity<Void> enrichExistingTasks() {
        log.info("Starting task enrichment");
        val tasks = new HashSet<TaskEntity>();
        tasks.addAll(taskService.createTaskQuery()
            .active()
            .taskAssigned()
            .list()
            .stream().map(task -> ((TaskEntity) task))
            .collect(Collectors.toList()));
        tasks.addAll(taskService.createTaskQuery()
            .active()
            .withCandidateGroups()
            .list()
            .stream().map(task -> ((TaskEntity) task))
            .collect(Collectors.toList()));
        tasks.addAll(taskService.createTaskQuery()
            .active()
            .withCandidateUsers()
            .list()
            .stream().map(task -> ((TaskEntity) task))
            .collect(Collectors.toList()));
        enrichTasks(tasks);
        return noContent().build();
    }

  @SneakyThrows
  private void enrichTasks(Set<TaskEntity> tasks) {
      log.info("Selected for enrichment {} tasks", tasks.size());
    ((ProcessEngineConfigurationImpl)processEngineConfiguration).getCommandExecutorTxRequired().execute(
        (context) -> {
          tasks.forEach((task) -> {
            assignmentCreateTaskListener.taskCreated(task);
            cancelableTaskStatusCreateTaskListener.taskCreated(task);
            taskSchemaTypeCreateTaskListener.taskCreated(task);
            taskDescriptionCreateTaskListener.taskCreated(task);
            taskService.saveTask(task);
          });
          log.info("Enrichment of {} tasks finished", tasks.size());
          return null;
        }
    );
    }


    @PostMapping("/rest/admin/tasks/import")
    @RolesAllowed(CLIENT_IMPORT_TASKS)
    public ResponseEntity<Void> importExistingTasks() {
        log.info("Starting import of tasks.");
        taskServiceCollectorService.collectAndPopulateExistingTasks(
           true,
           0,
           1000 // import 1000 tasks
        );
        log.info("Import of tasks completed.");
        return noContent().build();
    }
}
