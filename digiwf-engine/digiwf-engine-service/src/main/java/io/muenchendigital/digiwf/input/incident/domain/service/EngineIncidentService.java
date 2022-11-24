package io.muenchendigital.digiwf.input.incident.domain.service;


import io.muenchendigital.digiwf.input.incident.domain.model.Incident;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.EventSubscription;
import org.camunda.bpm.engine.runtime.Execution;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class EngineIncidentService {

    private final RuntimeService runtimeService;

    public void createIncident(final Incident incident) {
        log.debug("createIncident {}", incident);

        final List<EventSubscription> list = runtimeService.createEventSubscriptionQuery().eventName(incident.getMessageName()).processInstanceId(incident.getProcessInstanceId()).list();
        log.debug("EventSubscriptions: {}", list.size());
        if (list.isEmpty()){
            log.error("No event subscriptions found to create incident for instance: {}", incident.getProcessInstanceId());
            return;
        }
        list.forEach(s -> System.out.println("Activity: " + s));
        final String activity = list.get(0).getActivityId();

//        Incident incident = runtimeService.createIncident(Incident.FAILED_JOB_HANDLER_TYPE, incident.getProcessInstanceId(), activity, incident.getMessageName());

//        String execId = list.get(0).getExecutionId();
//        Execution exec = runtimeService.createExecutionQuery().executionId(execId).singleResult();
//        exec.c

//        Incident incident = runtimeService.createIncident("anytype", incident.getProcessInstanceId(), activity, incident.getMessageName());
//        Incident incident = runtimeService.createIncident("anytype", incident.getProcessInstanceId(), list.get(0).getEventName(), incident.getMessageName());

        final List<Execution> e = runtimeService.createExecutionQuery().processInstanceId(incident.getProcessInstanceId()).list();
        e.forEach(e2 -> System.out.println(e2.getClass().getName()));
        System.out.println("ex: " + e.getClass().getName());

        for (final Execution ex : e) {
            if (ex.getId().equals(incident.getProcessInstanceId())) {
                continue;
            }
            try {
//                System.out.println("ex: " + ex.getId()+" / " + ex.getProcessInstanceId());
                runtimeService.createIncident("custom", ex.getId(), "foo", "bar");
                System.out.println("EXEC: " + ex.getId());
                return;
            } catch (final Exception e1) {
                System.out.println("exception: " + ex.getId());
            }
        }

//        log.info("Incident created: {}", incident);
    }

}
