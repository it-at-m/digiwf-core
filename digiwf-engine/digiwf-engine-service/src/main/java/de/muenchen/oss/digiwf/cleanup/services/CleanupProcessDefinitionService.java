package de.muenchen.oss.digiwf.cleanup.services;

import de.muenchen.oss.digiwf.process.definition.domain.model.ServiceDefinition;
import de.muenchen.oss.digiwf.process.definition.domain.service.ServiceDefinitionService;
import de.muenchen.oss.digiwf.process.definition.domain.service.ServiceDefinitionService.ProcessDefinitionWithInstanceInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@Slf4j
public class CleanupProcessDefinitionService {

    private final ServiceDefinitionService serviceDefinitionService;

    private final int cleanupThreshholdInDays;

    public CleanupProcessDefinitionService(ServiceDefinitionService serviceDefinitionService, @Value("${digiwf.cleanup.threshhold-in-days:180}") int cleanupThreshholdInDays) {
        this.serviceDefinitionService = serviceDefinitionService;
        this.cleanupThreshholdInDays = cleanupThreshholdInDays;
    }

    public List<ProcessDefinitionWithInstanceInfo> getInfoForDefinitionKey(String key) {
        return serviceDefinitionService.getProcessDefinitionsWithInstanceInfoByKey(key);
    }

    public List<String> retrieveAllKeys() {
        return serviceDefinitionService
            .getServiceDefinitions(false)
            .stream()
            .peek(def -> log.error("Definition: {} [{}]", def.getName(), def.getKey()))
            .map(ServiceDefinition::getKey)
            .toList();
    }

    public void migrateAutomatically(String key) {
        var definitions = serviceDefinitionService.getProcessDefinitionsWithInstanceInfoByKey(key);
        // there should be latest
        var latest = definitions.stream().filter(ProcessDefinitionWithInstanceInfo::isLatest).findAny().orElseThrow(() -> new IllegalStateException("Could not find latest definition of process key " + key));
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

    public void deleteObviousDefinitions(String key, boolean removeWithHistoricalProcessInstances) {
        var definitions = serviceDefinitionService.getProcessDefinitionsWithInstanceInfoByKey(key);
        var thresholdDate = Instant.now().minus(cleanupThreshholdInDays, ChronoUnit.DAYS);
        var forDeletion = definitions
            .stream()
            .filter(definitionWithInstanceInfo -> isObviousForDeletion(definitionWithInstanceInfo, thresholdDate, removeWithHistoricalProcessInstances))
            .map(ProcessDefinitionWithInstanceInfo::processDefinitionId)
            .toList();
        var remaining = definitions.stream().map(ProcessDefinitionWithInstanceInfo::processDefinitionId).filter(
            definitionWithInstanceInfo -> !forDeletion.contains(definitionWithInstanceInfo)
        ).toList();

        deleteProcessDefinitions(key, forDeletion, remaining);
    }


    public void deleteAboveThreshold(String key, Integer thresholdCount, boolean removeWithHistoricalProcessInstances) {
        var definitions = serviceDefinitionService.getProcessDefinitionsWithInstanceInfoByKey(key);
        if (definitions.size() <= thresholdCount) {
            // nothing to do
            return;
        }
        var forDeletion = definitions
            .stream()
            .limit(definitions.size() - thresholdCount) // take the definitions with lower version in order to get the last <thresholdCount> remaining
            .filter(info -> removeWithHistoricalProcessInstances || info.instanceCount() == 0) // only remove definitions with instances if removeWithHistoricalProcessInstances is true
            .map(ProcessDefinitionWithInstanceInfo::processDefinitionId)
            .toList();
        var remaining = definitions.stream().map(ProcessDefinitionWithInstanceInfo::processDefinitionId).filter(
            def -> !forDeletion.contains(def)
        ).toList();

        deleteProcessDefinitions(key, forDeletion, remaining);
    }

    private void deleteProcessDefinitions(String key, List<String> forDeletion, List<String> remaining) {
        log.info("Deleting definitions for key {}: \n[\n{}\n] \nand left over: \n[\n{}\n].",
            key,
            String.join(",\n", forDeletion),
            String.join(",\n", remaining));
        //
        serviceDefinitionService.deleteDefinitions(true, forDeletion.toArray(new String[0]));
    }


    public boolean isObviousForDeletion(ProcessDefinitionWithInstanceInfo info, Instant thresholdDate, boolean removeWithHistoricalProcessInstances) {
        if (info.isLatest()) {
            return false; // never delete latest definition
        }
       if (info.instanceCount() == 0) return true;

       if (!removeWithHistoricalProcessInstances) return false;
       else return info.newestProcessInstanceStartTime() != null && info.newestProcessInstanceStartTime().toInstant().isBefore(thresholdDate);
    }

}
