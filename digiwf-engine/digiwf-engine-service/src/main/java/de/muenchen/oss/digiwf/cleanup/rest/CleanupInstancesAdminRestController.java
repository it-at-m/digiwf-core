package de.muenchen.oss.digiwf.cleanup.rest;

import de.muenchen.oss.digiwf.cleanup.services.CleanupProcessDefinitionService;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class CleanupInstancesAdminRestController {

    public static final String CLEANUP_DEFINITIONS = "clientrole_cleanup_definitions";
    private final CleanupProcessDefinitionService cleanupProcessDefinitionService;

    @GetMapping("/rest/admin/process-definitions/key")
    @RolesAllowed(CLEANUP_DEFINITIONS)
    public ResponseEntity<List<String>> retrieveDefinitionsKeys() {
        return ResponseEntity.ok(cleanupProcessDefinitionService.retrieveAllKeys());
    }

    @GetMapping("/rest/admin/process-definitions/key/{key}")
    @RolesAllowed(CLEANUP_DEFINITIONS)
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
    @RolesAllowed(CLEANUP_DEFINITIONS)
    public ResponseEntity<Void> migrate(@PathVariable("key") String key) {
        cleanupProcessDefinitionService.migrateAutomatically(key);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/rest/admin/process-definitions/key/{key}/obvious")
    @RolesAllowed(CLEANUP_DEFINITIONS)
    public ResponseEntity<Void> deleteObviousDefinitions(@PathVariable("key") String key) {
        cleanupProcessDefinitionService.deleteObviousDefinitions(key);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/rest/admin/process-definitions/key/{key}/cascading")
    @RolesAllowed(CLEANUP_DEFINITIONS)
    public ResponseEntity<Void> deleteDefinitionsCascading(@PathVariable("key") String key) {
        cleanupProcessDefinitionService.deleteCascading(key);
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
