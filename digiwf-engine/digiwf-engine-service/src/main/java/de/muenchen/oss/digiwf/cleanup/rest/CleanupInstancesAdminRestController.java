package de.muenchen.oss.digiwf.cleanup.rest;

import de.muenchen.oss.digiwf.cleanup.services.CleanupProcessDefinitionService;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class CleanupInstancesAdminRestController {

    public static final String CLEANUP_ROLE = "admin";
    private final CleanupProcessDefinitionService cleanupProcessDefinitionService;

    @GetMapping("/rest/admin/process-definitions/key")
    @RolesAllowed(CLEANUP_ROLE)
    public ResponseEntity<List<String>> retrieveDefinitionsKeys() {
        return ResponseEntity.ok(cleanupProcessDefinitionService.retrieveAllKeys());
    }

    @GetMapping("/rest/admin/process-definitions/key/{key}")
    @RolesAllowed(CLEANUP_ROLE)
    public ResponseEntity<List<DefinitionInfoDto>> retrieveDefinitionInfo(@PathVariable("key") String key) {
        return ResponseEntity.ok(cleanupProcessDefinitionService
            .getInfoForDefinitionKey(key)
            .stream()
            .map(instanceInfo -> new DefinitionInfoDto(
                instanceInfo.processDefinitionId(),
                instanceInfo.version(),
                instanceInfo.isLatest(),
                instanceInfo.instanceCount(),
                instanceInfo.newestProcessInstanceStartTime()
            ))
            .collect(Collectors.toList())
        );
    }

    @PostMapping("/rest/admin/process-definitions/key/{key}/migrate")
    @RolesAllowed(CLEANUP_ROLE)
    public ResponseEntity<Void> migrate(@PathVariable("key") String key) {
        cleanupProcessDefinitionService.migrateAutomatically(key);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/rest/admin/process-definitions/key/{key}/obvious")
    @RolesAllowed(CLEANUP_ROLE)
    public ResponseEntity<Void> deleteObviousDefinitions(@PathVariable("key") String key, @RequestParam(value = "remove-with-historical-process-instances", defaultValue = "false") Boolean removeWithHistoricalProcessInstances) {
        cleanupProcessDefinitionService.deleteObviousDefinitions(key, removeWithHistoricalProcessInstances);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/rest/admin/process-definitions/key/{key}/threshold")
    @RolesAllowed(CLEANUP_ROLE)
    public ResponseEntity<Void> deleteDefinitionsCascading(@PathVariable("key") String key, @RequestParam(value = "threshold") Integer thresholdCount, @RequestParam(value = "remove-with-historical-process-instances", defaultValue = "false") Boolean removeWithHistoricalProcessInstances) {
        cleanupProcessDefinitionService.deleteAboveThreshold(key, thresholdCount, removeWithHistoricalProcessInstances);
        return ResponseEntity.noContent().build();
    }

    public record DefinitionInfoDto(
        String processDefinitionId,
        long version,
        boolean isLatest,
        int count,
        Date newestInstance
    ) {
        // empty body
    }
}
