package de.muenchen.oss.digiwf.task.importer;

import io.holunda.polyflow.taskpool.collector.task.TaskServiceCollectorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.camunda.bpm.engine.ProcessEngineConfiguration;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.camunda.community.batch.CustomBatchBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.security.RolesAllowed;
import java.util.stream.Collectors;

import static org.springframework.http.ResponseEntity.noContent;

@RequiredArgsConstructor
@RestController
@Slf4j
@Transactional
public class TaskImporterService {

    public static final String CLIENT_IMPORT_TASKS = "clientrole_task_importer";
    private final TaskServiceCollectorService taskServiceCollectorService;
    private final TaskService taskService;
    private final ProcessEngineConfiguration engineConfiguration;
    private final TaskEnrichBatchJobHandler taskEnrichBatchJobHandler;

    @PostConstruct
    void inform() {
      log.warn("Activating TASK ENRICH/IMPORT endpoint");
    }

    @PostMapping("/rest/admin/tasks/enrich")
    @RolesAllowed(CLIENT_IMPORT_TASKS)
    public ResponseEntity<Void> enrichExistingTasks() {
        log.info("Starting task enrichment");
        val taskIds = taskService.createTaskQuery()
                .active().list().stream()
                .map(Task::getId).collect(Collectors.toList());
        log.info("Selected {} tasks for enrichment", taskIds.size());
        CustomBatchBuilder
                .of(taskIds)
                .jobHandler(taskEnrichBatchJobHandler)
                .configuration(engineConfiguration)
                .invocationsPerBatchJob(100)
                .create();
        return noContent().build();
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
