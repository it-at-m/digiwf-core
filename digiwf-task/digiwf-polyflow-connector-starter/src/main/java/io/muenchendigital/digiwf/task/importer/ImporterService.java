package io.muenchendigital.digiwf.task.importer;

import io.holunda.polyflow.taskpool.collector.task.TaskServiceCollectorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

import static org.springframework.http.ResponseEntity.noContent;

@RequiredArgsConstructor
@RestController
@Slf4j
public class ImporterService {

    private final TaskServiceCollectorService taskServiceCollectorService;

    @PostConstruct
    void inform() {
      log.warn("Starting TASK IMPORT ENDPOINT");
    }

    @PostMapping("/rest/admin/tasks/import")
    public ResponseEntity<Void> importExistingTasks() {
        taskServiceCollectorService.collectAndPopulateExistingTasks();
        return noContent().build();
    }
}
