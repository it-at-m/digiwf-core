/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.process.definition.domain.service;

import de.muenchen.oss.digiwf.process.config.domain.model.ProcessConfig;
import de.muenchen.oss.digiwf.process.config.domain.service.ProcessConfigService;
import de.muenchen.oss.digiwf.process.instance.domain.model.ServiceInstance;
import de.muenchen.oss.digiwf.process.instance.domain.service.ServiceInstanceService;
import de.muenchen.oss.digiwf.process.instance.process.ProcessConstants;
import de.muenchen.oss.digiwf.process.instance.process.properties.S3Properties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.variable.Variables;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static de.muenchen.oss.digiwf.process.instance.process.ProcessConstants.PROCESS_S3_ASYNC_CONFIG;
import static de.muenchen.oss.digiwf.process.instance.process.ProcessConstants.PROCESS_S3_SYNC_CONFIG;

/**
 * Service to initialize service instances.
 *
 * @author externer.dl.horn
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ServiceInstanceInitializationService {

    private final RepositoryService repositoryService;
    private final RuntimeService runtimeService;

    private final ServiceInstanceService serviceInstanceService;
    private final ProcessConfigService processConfigService;
    private final S3Properties s3Properties;


    public void initializeInstance(final String processInstanceId, final String key, final Map<String, Object> currentVariables) {

        final String processDefinitionName = this.getProcessDefinitionName(key);

        //create service instance
        final ServiceInstance serviceInstance = this.serviceInstanceService.creatServiceInstance(processDefinitionName, key);
        this.serviceInstanceService.updateInstanceId(serviceInstance.getId(), processInstanceId);

        if (currentVariables.containsKey(ProcessConstants.PROCESS_STARTER_OF_INSTANCE)) {
            this.serviceInstanceService.authorizeServiceInstance(processInstanceId, currentVariables.get(ProcessConstants.PROCESS_STARTER_OF_INSTANCE).toString());
        }

        updateVariables(processInstanceId, currentVariables, serviceInstance);
        initializeConfig(key, processInstanceId);

    }

    private void initializeConfig(String key, String processInstanceId) {
        val config = processConfigService.getProcessConfig(key)
                .orElse(null);


        Map<String, Object> variables = new HashMap<>();

        this.getConfigValue(config, "app_file_s3_async_config")
                .ifPresentOrElse(
                        configValue -> variables.put(PROCESS_S3_ASYNC_CONFIG, configValue),
                        () -> variables.put(PROCESS_S3_ASYNC_CONFIG, this.s3Properties.getTopic())
                );
        this.getConfigValue(config, "app_file_s3_sync_config")
                .ifPresentOrElse(
                        configValue -> variables.put(PROCESS_S3_SYNC_CONFIG, configValue),
                        () -> variables.put(PROCESS_S3_SYNC_CONFIG, this.s3Properties.getHttpAPI())
                );
        this.runtimeService.setVariables(processInstanceId, variables);

    }

    private Optional<String> getConfigValue(ProcessConfig config, String key) {

        if (config == null) {
            return Optional.empty();
        }

        return Optional.ofNullable(config.getConfig(key));
    }

    private void updateVariables(String processInstanceId, Map<String, Object> currentVariables, ServiceInstance serviceInstance) {
        Map<String, Object> variables = new HashMap<>();
        variables.put(ProcessConstants.PROCESS_START_DATE, ZonedDateTime.now().format(DateTimeFormatter.ISO_INSTANT));
        variables.put(ProcessConstants.PROCESS_STATUS, "Gestartet");
        currentVariables.computeIfAbsent(ProcessConstants.PROCESS_FILE_CONTEXT, k -> variables.put(ProcessConstants.PROCESS_FILE_CONTEXT, UUID.randomUUID()));
        variables.put(ProcessConstants.PROCESS_INFO_ID, Variables.stringValue(serviceInstance.getId(), true));


        this.runtimeService.setVariables(processInstanceId, variables);
    }


    private String getProcessDefinitionName(final String key) {

        final ProcessDefinition processDefinition = this.repositoryService.createProcessDefinitionQuery()
                .latestVersion()
                .processDefinitionKey(key)
                .singleResult();

        return processDefinition.getName();
    }

}
