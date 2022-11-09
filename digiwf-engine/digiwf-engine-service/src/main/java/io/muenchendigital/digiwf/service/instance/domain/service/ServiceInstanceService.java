/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.service.instance.domain.service;

import io.muenchendigital.digiwf.jsonschema.domain.model.JsonSchema;
import io.muenchendigital.digiwf.jsonschema.domain.service.JsonSchemaService;
import io.muenchendigital.digiwf.service.config.domain.service.ProcessConfigService;
import io.muenchendigital.digiwf.service.instance.domain.mapper.HistoryTaskMapper;
import io.muenchendigital.digiwf.service.instance.domain.mapper.ServiceInstanceMapper;
import io.muenchendigital.digiwf.service.instance.domain.model.ServiceInstance;
import io.muenchendigital.digiwf.service.instance.domain.model.ServiceInstanceDetail;
import io.muenchendigital.digiwf.service.instance.infrastructure.entity.ServiceInstanceEntity;
import io.muenchendigital.digiwf.service.instance.infrastructure.repository.ProcessInstanceInfoRepository;
import io.muenchendigital.digiwf.service.instance.process.ProcessConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.history.HistoricProcessInstance;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Service to interact with process instances.
 *
 * @author externer.dl.horn
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ServiceInstanceService {

    private final HistoryService historyService;

    private final ProcessConfigService processConfigService;
    private final ServiceInstanceAuthService serviceInstanceAuthService;

    private final ProcessInstanceInfoRepository processInstanceInfoRepository;

    private final ServiceInstanceMapper serviceInstanceMapper;
    private final HistoryTaskMapper historyTaskMapper;

    private final JsonSchemaService jsonSchemaService;
    private final ServiceInstanceDataService serviceInstanceDataService;


    /**
     * Get all assigned  instances
     *
     * @return assigned  instances
     */
    public List<ServiceInstance> getProcessInstanceByUser(final String userId) {
        final List<String> processAuthIds = this.serviceInstanceAuthService.getAllServiceInstanceIdsByUser(userId);
        return this.serviceInstanceMapper.map2Model(this.processInstanceInfoRepository.findAllByInstanceIdIn(processAuthIds));
    }

    /**
     * Get detail information of a instance.
     *
     * @param infoId Id of the  instance
     * @return instance detail
     */
    public ServiceInstanceDetail getServiceInstanceDetail(final String infoId) {

        final ServiceInstance processInstanceInfo = this.getServiceInstanceById(infoId).orElseThrow();

        val processConfig = this.processConfigService.getProcessConfig(processInstanceInfo.getDefinitionKey()).orElseThrow();
        val tasks = this.historyService.createHistoricTaskInstanceQuery()
                .processInstanceId(processInstanceInfo.getInstanceId())
                .list();

        final ServiceInstanceDetail detail = this.serviceInstanceMapper.map2Detail(processInstanceInfo);
        detail.setConfig(processConfig);
        detail.setHistoryTasks(this.historyTaskMapper.map2Model(tasks));

        if (StringUtils.isNotBlank(processConfig.getInstanceSchemaKey())) {
            final JsonSchema jsonSchema = this.jsonSchemaService.getByKey(processConfig.getInstanceSchemaKey()).orElseThrow();
            final Map<String, Object> data = this.serviceInstanceDataService.getVaribales(processInstanceInfo.getInstanceId(), jsonSchema);

            detail.setData(data);
            detail.setJsonSchema(jsonSchema.getSchemaMap());
        }

        return detail;
    }

    /**
     * Get data of process instances for the given variables
     *
     * @param key       key of the process definition
     * @param variables list of variable keys that should be returned
     * @return data grouped by process instance
     */
    public List<Map<String, Object>> getInstanceDataByProcessKey(final String key, final List<String> variables) {
        return this.getInstanceDataByProcessKey(key, variables, null);
    }

    /**
     * Get data of instance for the given variables.
     *
     * @param key       key of the  definition
     * @param variables list of variable keys that should be returned
     * @param status    status of the  instance
     * @return data grouped by process instance
     */
    public List<Map<String, Object>> getInstanceDataByProcessKey(final String key, final List<String> variables, final String status) {
        final List<Map<String, Object>> data = new ArrayList<>();
        val instances = this.getAllInstancesByKey(key);
        for (val instance : instances) {
            val instanceData = this.getInstanceData(instance.getId());

            if (StringUtils.isNotBlank(status)) {
                if (!instanceData.containsKey(ProcessConstants.PROCESS_STATUS) || !instanceData.get(ProcessConstants.PROCESS_STATUS)
                        .equals(status)) {
                    continue;
                }
            }
            val filteredInstanceData = this.filterInstanceData(variables, instanceData);
            data.add(filteredInstanceData);
        }
        return data;
    }

    /**
     * Get service instance by  id.
     *
     * @param id Id of the service instance
     * @return service instance
     */
    public Optional<ServiceInstance> getServiceInstanceById(final String id) {
        return this.processInstanceInfoRepository.findById(id)
                .map(this.serviceInstanceMapper::map2Model);
    }


    /**
     * Get service instance by instance id.
     *
     * @param instanceId Id of the instance
     * @return service instance
     */
    public Optional<ServiceInstance> getServiceInstanceByInstanceId(final String instanceId) {
        return this.processInstanceInfoRepository.findByInstanceId(instanceId)
                .map(this.serviceInstanceMapper::map2Model);
    }

    /**
     * Create a Service Instance object.
     *
     * @param definitionName name of the definition
     * @param definitionKey  key of the definition
     * @return created ServiceInstance
     */
    public ServiceInstance creatServiceInstance(final String definitionName, final String definitionKey) {
        final ServiceInstance serviceInstance = ServiceInstance.builder()
                .definitionName(definitionName)
                .startTime(new Date())
                .definitionKey(definitionKey)
                .status("Gestartet")
                .build();
        return this.saveServiceInstance(serviceInstance);
    }

    /**
     * Save an extisting service instance
     *
     * @param serviceInstance Instance that is saved
     * @return saved service instance
     */
    public ServiceInstance saveServiceInstance(final ServiceInstance serviceInstance) {
        final ServiceInstanceEntity persistedProcessInstanceInfo = this.processInstanceInfoRepository.save(this.serviceInstanceMapper.map2Entity(serviceInstance));
        return this.serviceInstanceMapper.map2Model(persistedProcessInstanceInfo);
    }

    /**
     * Create
     *
     * @param instanceId Id of the corresponding
     * @param userId     Id of the user
     */
    public void authorizeServiceInstance(final String instanceId, final String userId) {
        this.serviceInstanceAuthService.createAuthorization(instanceId, userId);
    }

    /**
     * Update the instance Id of a service instance
     *
     * @param serviceInstanceId Id of the service instance
     * @param instanceId        Id of the corresponding process instance
     */
    public void updateInstanceId(final String serviceInstanceId, final String instanceId) {
        final ServiceInstance serviceInstance = this.getServiceInstanceById(serviceInstanceId).orElseThrow();
        serviceInstance.updateProcessInstanceId(instanceId);
        this.saveServiceInstance(serviceInstance);
    }

    /**
     * Get all instances expired at a reference date.
     *
     * @param referenceDate the reference date for expiration
     * @return expired instances
     */
    public List<ServiceInstance> getProcessInstanceByRemovalTimeBefore(final Date referenceDate) {
        final List<ServiceInstanceEntity> instances = this.processInstanceInfoRepository.findByRemovalTimeBefore(referenceDate);
        return this.serviceInstanceMapper.map2Model(instances);
    }

    /**
     * Cleanup instance with given id.
     *
     * @param instanceId the id
     */
    public void cleanupInstance(final String instanceId) {
        final Optional<ServiceInstanceEntity> entity = this.processInstanceInfoRepository.findByInstanceId(instanceId);
        if (entity.isPresent()) {
            this.processInstanceInfoRepository.delete(entity.get());
            log.info("Service instance cleaned up: {}", entity.get().getInstanceId());
        }
    }

    //------------------------------------------------------- helper methods -------------------------------------------------------//

    private Map<String, Object> filterInstanceData(final List<String> variables, final Map<String, Object> instanceData) {
        val data = instanceData.entrySet()
                .stream()
                .filter(entry -> variables.contains(entry.getKey()))
                .collect(Collectors.toList());

        //sortieren nach Variable

        final Map<String, Object> filteredData = new HashMap<>();
        for (val obj : data) {
            filteredData.put(obj.getKey(), obj.getValue());
        }

        return filteredData;
    }

    private Map<String, Object> getInstanceData(final String instanceId) {
        val data = this.historyService.createHistoricVariableInstanceQuery()
                .processInstanceId(instanceId).list();
        final Map<String, Object> instanceData = new HashMap<>();
        for (val obj : data) {
            instanceData.put(obj.getName(), obj.getValue());
        }

        return instanceData;
    }

    private List<HistoricProcessInstance> getAllInstancesByKey(final String key) {
        return this.historyService.createHistoricProcessInstanceQuery()
                .processDefinitionKey(key)
                .list();
    }

}
