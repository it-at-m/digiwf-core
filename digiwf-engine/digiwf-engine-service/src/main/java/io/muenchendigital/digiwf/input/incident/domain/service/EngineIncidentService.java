package io.muenchendigital.digiwf.input.incident.domain.service;


import io.muenchendigital.digiwf.input.incident.domain.model.Incident;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.EventSubscription;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class EngineIncidentService {

    private static final String INCIDENT_TYPE   = "integrationError";
    private static final String EVENT_TYPE      = "message";

    private final RuntimeService runtimeService;

    public void createIncident(@Valid final Incident incident) {
        log.debug("createIncident {}", incident);

        //load corresponding event subscription
        final String executionId = runtimeService.createEventSubscriptionQuery()
                .eventName(incident.getMessageName())
                .eventType(EVENT_TYPE)
                .processInstanceId(incident.getProcessInstanceId()).list()
                .stream()
                .findFirst()
                .map(EventSubscription::getExecutionId)
                .orElseThrow();

        // create incident
        runtimeService.createIncident(INCIDENT_TYPE, executionId, null, incident.getErrorMessage());

        log.info("Incident created: {}", incident);
    }

}
