package de.muenchen.oss.digiwf.cleanup.services;

import de.muenchen.oss.digiwf.process.definition.domain.model.ServiceDefinition;
import de.muenchen.oss.digiwf.process.definition.domain.service.ServiceDefinitionService;
import de.muenchen.oss.digiwf.process.definition.domain.service.ServiceDefinitionService.ProcessDefinitionWithInstanceInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CleanupProcessDefinitionService {

    private final ServiceDefinitionService serviceDefinitionService;

    public List<ProcessDefinitionWithInstanceInfo> getInfoForDefinitionKey(String key) {
        return serviceDefinitionService.getProcessDefinitionsWithInstanceInfoByKey(key);
    }

    public List<String> retrieveAllKeys() {
        return serviceDefinitionService
            .getServiceDefinitions()
            .stream()
            .peek(def -> log.error("Definition: {} [{}]", def.getName(), def.getKey()))
            .map(ServiceDefinition::getKey)
            .toList();
    }

    public void migrateAutomatically(String key) {
        var definitions = serviceDefinitionService.getProcessDefinitionsWithInstanceInfoByKey(key);
        // there should be latest
        var latest = definitions.stream().filter(ProcessDefinitionWithInstanceInfo::isLatest).findAny().orElseThrow();
        var remainingWithInstances = definitions.stream().filter(
            processDefinitionWithInstanceInfo -> processDefinitionWithInstanceInfo.instanceCount() > 0
                && !processDefinitionWithInstanceInfo.isLatest()
        ).toList();

        remainingWithInstances.forEach(
            sourceDefinition -> {
                try {
                    log.info("Creating a migration plan and migrating process definition {} from version {} to version {}", key, sourceDefinition.version(), latest.version());
                    serviceDefinitionService.createAutomaticMigrationAndRun(sourceDefinition.processDefinitionId(), latest.processDefinitionId());
                    log.info("Successfully migrated process definition {} from version {} ({}) to {}", key, sourceDefinition.version(), sourceDefinition.processDefinitionId(), latest.version());
                } catch (Exception e) {
                    log.error("Failed to automatically migrate from process definition {} from {}", key, sourceDefinition.version());
                }
            }
        );
    }

    public void deleteObviousDefinitions(String key) {
        var definitions = serviceDefinitionService.getProcessDefinitionsWithInstanceInfoByKey(key);
        var thresholdDate = Instant.now().minus(180, ChronoUnit.DAYS);
        var forDeletion = definitions
            .stream()
            .filter(definitionWithInstanceInfo -> isObviousForDeletion(definitionWithInstanceInfo, thresholdDate))
            .map(ProcessDefinitionWithInstanceInfo::processDefinitionId)
            .toList();
        var remaining = definitions.stream().map(ProcessDefinitionWithInstanceInfo::processDefinitionId).filter(
            definitionWithInstanceInfo -> !forDeletion.contains(definitionWithInstanceInfo)
        ).toList();

        log.info("Deleting definitions for key {}: {} and left over: {}",
            key,
            String.join(",\n", forDeletion),
            String.join(",\n", remaining));
        //
        serviceDefinitionService.deleteDefinitions(true, forDeletion.toArray(new String[0]));
    }


    public void deleteCascading(String key) {
        var thresholdCount = 5;
        var definitions = serviceDefinitionService.getProcessDefinitionsWithInstanceInfoByKey(key);
        if (definitions.size() <= thresholdCount) {
            // nothing to do
            return;
        }
        var forDeletion = definitions
            .stream()
            .limit(definitions.size() - thresholdCount) // take the definitions with lower version in order to get the last <thresholdCount> remaining
            .map(ProcessDefinitionWithInstanceInfo::processDefinitionId)
            .toList();
        var remaining = definitions.stream().map(ProcessDefinitionWithInstanceInfo::processDefinitionId).filter(
            def -> !forDeletion.contains(def)
        ).toList();

        log.info("Deleting definitions for key {}: {} and left over: {}",
            key,
            String.join(",\n", forDeletion),
            String.join(",\n", remaining));
        //
        serviceDefinitionService.deleteDefinitions(true, forDeletion.toArray(new String[0]));
    }


    public boolean isObviousForDeletion(ProcessDefinitionWithInstanceInfo info, Instant thresholdDate) {
        if (info.isLatest()) {
            return false; // never delete latest definition
        }
        if (info.instanceCount() == 0) {
            return true; // delete old definitions without instances.
        } else {
            return info.newestProcessInstanceStartTime() != null && info.newestProcessInstanceStartTime().toInstant().isBefore(thresholdDate);
        }
    }

}
