/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.process.definition.domain.service;

import de.muenchen.oss.digiwf.process.definition.domain.mapper.ServiceDefinitionMapper;
import de.muenchen.oss.digiwf.process.definition.domain.model.ServiceDefinition;
import de.muenchen.oss.digiwf.process.definition.domain.model.ServiceDefinitionDetail;
import de.muenchen.oss.digiwf.process.definition.domain.model.StartContext;
import de.muenchen.oss.digiwf.process.instance.process.ProcessConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.history.HistoricProcessInstance;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.repository.ProcessDefinitionQuery;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.variable.Variables;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Service to query service definitions.
 *
 * @author externer.dl.horn
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ServiceDefinitionService {

    private final RepositoryService repositoryService;
    private final RuntimeService runtimeService;
    private final HistoryService historyService;

    private final ServiceDefinitionMapper serviceDefinitionMapper;

    /**
     * Start a service definition with the given parameters.
     *
     * @param detail       service definition
     * @param variables    data with which the process is started
     * @param userId       id of the user that starts the process
     * @param startContext start context
     */
    public void startInstance(final ServiceDefinitionDetail detail, final String businessKey, final Map<String, Object> variables, final String userId, final StartContext startContext) {
        log.debug("Start Process: " + detail.getKey());

        //add variables
        variables.put(ProcessConstants.PROCESS_STARTER_OF_INSTANCE, userId);
        variables.put(ProcessConstants.PROCESS_START_DATE, ZonedDateTime.now().format(DateTimeFormatter.ISO_INSTANT));
        variables.put(ProcessConstants.PROCESS_STATUS, "Gestartet");
        variables.put(ProcessConstants.PROCESS_FILE_CONTEXT, startContext.getFileContext());

        //start instance
        ProcessInstance processInstance;
        if (StringUtils.isEmpty(businessKey)) {
            processInstance = this.runtimeService.startProcessInstanceByKey(detail.getKey(), variables);
        } else {
            processInstance = this.runtimeService.startProcessInstanceByKey(detail.getKey(), businessKey, variables);
        }

        log.info("process instance for key {} started: {}", detail.getKey(), processInstance.getId());
    }

    /**
     * Get a service definition by id.
     *
     * @param id Id of the service definition
     * @return service definition
     */
    public ProcessDefinition getServiceDefinition(final String id) {
        final ProcessDefinition serviceDefinition = this.repositoryService.createProcessDefinitionQuery()
            .processDefinitionId(id)
            .singleResult();

        if (serviceDefinition == null) {
            throw new IllegalArgumentException(String.format("The servicedefinition with the id %s is not available.", id));
        }
        return serviceDefinition;
    }


    /**
     * Get all service definitions.
     *
     * @return all service definitions
     */
    public List<ServiceDefinition> getServiceDefinitions(boolean onlyStartableInTasklist) {

         ProcessDefinitionQuery processDefinitionQuery = this.repositoryService.createProcessDefinitionQuery();

         if(onlyStartableInTasklist){
             processDefinitionQuery.startableInTasklist();
         }

        List<ProcessDefinition> serviceDefinitions = processDefinitionQuery
            .active()
            .latestVersion()
            .list();
        return this.serviceDefinitionMapper.map(serviceDefinitions);
    }

    /**
     * Get a service definition.
     * Does a check if the given authentication can access the service.
     *
     * @param key    key of the service definition
     * @param userId id of the user
     * @param groups groups of the user
     * @return service definition detail
     */
    public ServiceDefinitionDetail getServiceDefinitionDetail(final String key, final String userId, final List<String> groups) {

        final ProcessDefinition processDefinition = this.repositoryService.createProcessDefinitionQuery()
            .latestVersion()
            .processDefinitionKey(key)
            .singleResult();

        return this.serviceDefinitionMapper.map(processDefinition);
    }

    /**
     * For a given process definition key returns the number of running process instances, the time of the latest started instance and the flag if the definition is latest.
     *
     * @param key process definition key (the one defined in BPMN)
     * @return list of information about running process instances. The definitions are ordered by their "version" ascending.
     */
    public List<ProcessDefinitionWithInstanceInfo> getProcessDefinitionsWithInstanceInfoByKey(final String key) {

        ProcessDefinition latest = this.repositoryService
            .createProcessDefinitionQuery()
            .active()
            .processDefinitionKey(key)
            .latestVersion()
            .singleResult();

        List<ProcessDefinition> definitions = this.repositoryService
            .createProcessDefinitionQuery()
            .active()
            .processDefinitionKey(key)
            .orderByProcessDefinitionVersion()
            .asc()
            .list();

        return definitions
            .stream()
            .map(definition -> {
                var instanceCount = this
                    .runtimeService
                    .createProcessInstanceQuery()
                    .active()
                    .processDefinitionId(definition.getId())
                    .count();
                var instanceStartTimes = this
                    .historyService
                    .createHistoricProcessInstanceQuery()
                    .processDefinitionId(definition.getId())
                    .list()
                    .stream()
                    .map(HistoricProcessInstance::getStartTime)
                    .sorted(Comparator.reverseOrder())
                    .toList();
                return new ProcessDefinitionWithInstanceInfo(
                    definition.getId(),
                    definition.getVersion(),
                    definition.getId().equals(latest.getId()),
                    (int)instanceCount,
                    instanceStartTimes.isEmpty() ? null : instanceStartTimes.get(0)
                );
            })
            .toList();
    }

    public void deleteDefinitions(boolean cascade, String... processDefinitionIds) {
        var builder = this.repositoryService
            .deleteProcessDefinitions()
            .byIds(processDefinitionIds);
        if (cascade) {
            builder = builder.cascade();
        }
        builder.delete();
    }

    public void createAutomaticMigrationAndRun(String sourceId, String targetId) {
        var sourceDefinition = this.repositoryService.createProcessDefinitionQuery().processDefinitionId(sourceId).singleResult();
        var targetDefinition = this.repositoryService.createProcessDefinitionQuery().processDefinitionId(targetId).singleResult();
        if (!sourceDefinition.getKey().equals(targetDefinition.getKey())) {
            throw new IllegalArgumentException("automatic migration only works for the same process definition key");
        }

        var plan = this.runtimeService
            .createMigrationPlan(sourceId, targetId)
            .mapEqualActivities()
            .setVariables(Variables.createVariables().putValue("digiwf_auto_migration", true))
            .build();

        this.runtimeService
            .newMigration(plan)
            .processInstanceQuery(
                // take all instances of the source definition
                this.runtimeService
                    .createProcessInstanceQuery()
                    .processDefinitionId(plan.getSourceProcessDefinitionId())
                    .active()
            )
            .execute();
    }

    public record ProcessDefinitionWithInstanceInfo(
        String processDefinitionId,
        long version,
        boolean isLatest,
        int instanceCount,
        Date newestProcessInstanceStartTime
    ) {

        @Override
        public String toString() {
            return "ProcessDefinitionWithInstanceInfo{" +
                "processDefinitionId='" + processDefinitionId + '\'' +
                "version='" + version + '\'' +
                ", isLatest=" + isLatest +
                ", instanceCount=" + instanceCount +
                ", newestProcessInstanceStartTime=" + newestProcessInstanceStartTime +
                '}';
        }
    }

}
