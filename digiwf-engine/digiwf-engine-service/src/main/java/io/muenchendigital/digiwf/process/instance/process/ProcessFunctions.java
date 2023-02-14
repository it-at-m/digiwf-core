/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.process.instance.process;

import io.muenchendigital.digiwf.process.config.domain.service.ProcessConfigService;
import io.muenchendigital.digiwf.process.instance.domain.model.ServiceInstance;
import io.muenchendigital.digiwf.process.instance.domain.service.ServiceInstanceService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.camunda.bpm.engine.impl.context.Context;
import org.springframework.stereotype.Component;

import java.util.Optional;


/**
 * Functions to simplify modeling.
 *
 * @author externer.dl.horn
 */
@Component("process")
@RequiredArgsConstructor
public class ProcessFunctions {

    private final ProcessConfigService processConfigService;
    private final ServiceInstanceService processInstanceService;

    public void setStatus(final String statusKey) {

        val processConfig = this.processConfigService.getProcessConfig(this.getCurrentProcessKey()).orElseThrow();

        Context.getBpmnExecutionContext().getExecution().setVariable(ProcessConstants.PROCESS_STATUS, statusKey);

        final String instanceId = Context.getBpmnExecutionContext().getExecution().getProcessInstanceId();
        final ServiceInstance processInstanceInfo = Optional.ofNullable(Context.getBpmnExecutionContext().getExecution().getVariable(ProcessConstants.PROCESS_INFO_ID))
                .map(Object::toString)
                .map(this.processInstanceService::getServiceInstanceById)
                .orElseGet(() -> this.processInstanceService.getServiceInstanceByInstanceId(instanceId))
                .orElseThrow();

        processInstanceInfo.updateStatus(statusKey, processConfig.getStatus(statusKey));
        this.processInstanceService.saveServiceInstance(processInstanceInfo);
    }

    public void setDescription(final String description) {
        final String instanceId = Context.getBpmnExecutionContext().getExecution().getProcessInstanceId();

        final ServiceInstance processInstanceInfo = Optional.ofNullable(Context.getBpmnExecutionContext().getExecution().getVariable(ProcessConstants.PROCESS_INFO_ID))
                .map(Object::toString)
                .map(this.processInstanceService::getServiceInstanceById)
                .orElseGet(() -> this.processInstanceService.getServiceInstanceByInstanceId(instanceId))
                .orElseThrow();

        processInstanceInfo.updateDescription(description);
        this.processInstanceService.saveServiceInstance(processInstanceInfo);
    }

    private String getCurrentProcessKey() {
        return Context.getBpmnExecutionContext().getExecution().getProcessDefinition().getKey();
    }
}
