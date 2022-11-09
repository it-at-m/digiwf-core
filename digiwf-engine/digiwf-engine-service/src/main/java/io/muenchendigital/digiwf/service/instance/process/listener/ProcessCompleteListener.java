/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.service.instance.process.listener;

import io.muenchendigital.digiwf.cleanup.services.calculation.CleanupCalculator;
import io.muenchendigital.digiwf.service.instance.domain.model.ServiceInstance;
import io.muenchendigital.digiwf.service.instance.domain.service.ServiceInstanceService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.spring.boot.starter.event.ExecutionEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

/**
 * Cleanup process instances.
 *
 * @author externer.dl.horn
 */
@Component
@RequiredArgsConstructor
public class ProcessCompleteListener {

    private final ServiceInstanceService processInstanceService;
    private final CleanupCalculator cleanupCalculator;

    @EventListener
    public void executionEvent(final ExecutionEvent executionEvent) {

        //only clean up on end events
        if (!executionEvent.getEventName().equals("end")) {
            return;
        }

        //only clean process instances
        if (StringUtils.isBlank(executionEvent.getActivityInstanceId()) || !executionEvent.getActivityInstanceId()
                .equals(executionEvent.getProcessInstanceId())) {
            return;
        }

        final Optional<ServiceInstance> processInstanceInfo = this.processInstanceService.getServiceInstanceByInstanceId(executionEvent.getProcessInstanceId());

        if (processInstanceInfo.isEmpty()) {
            return;
        }

        ServiceInstance instanceItem = processInstanceInfo.get();
        Date endTime = new Date();

        Date removalTime = cleanupCalculator.calculateRemovalTime(instanceItem.getDefinitionKey(), instanceItem.getStartTime(), endTime);
        instanceItem.finished(endTime, removalTime);

        this.processInstanceService.saveServiceInstance(instanceItem);
    }

}
