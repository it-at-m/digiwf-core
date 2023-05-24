/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.humantask.process.listener;

import io.muenchendigital.digiwf.shared.security.UserAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * UserTask assignment listener for no-security mode.
 * Always sets 123456789 as default user.
 *
 * @author externer.dl.horn
 */
@Component
@Profile("no-security")
@Deprecated
@RequiredArgsConstructor
public class NoSecurityUserTaskListener {

    private final UserAuthenticationProvider userAuthenticationProvider;

    @EventListener
    public void delegateTask(final DelegateTask delegateTask) {
        if (delegateTask.getEventName().equals("assignment")) {
            if (!StringUtils.isBlank(delegateTask.getAssignee())) {
                delegateTask.setAssignee("123456789");
            }
        }

        if (delegateTask.getEventName().equals("create")) {
            if (StringUtils.isBlank(delegateTask.getAssignee())) {
                delegateTask.addCandidateGroup("itm");
            }
        }
    }
}
