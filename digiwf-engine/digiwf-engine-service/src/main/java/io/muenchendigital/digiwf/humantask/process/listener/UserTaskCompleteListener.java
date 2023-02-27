/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.humantask.process.listener;

import io.holunda.camunda.bpm.data.factory.VariableFactory;
import io.muenchendigital.digiwf.legacy.user.domain.service.UserService;
import io.muenchendigital.digiwf.process.instance.domain.service.ServiceInstanceAuthService;
import io.muenchendigital.digiwf.shared.security.AppAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import static io.holunda.camunda.bpm.data.CamundaBpmData.stringVariable;

/**
 * Complete listener for user tasks.
 * Sets variable and optional assignment to the corresponding process instance.
 *
 * @author externer.dl.horn
 */
@Component
@RequiredArgsConstructor
public class UserTaskCompleteListener {

    private final RuntimeService runtimeService;
    private final UserService userService;
    private final AppAuthenticationProvider camundaUserAuthenticationProvider;
    private final ServiceInstanceAuthService serviceInstanceAuthService;

    /**
     * @deprecated Use APP_ASSIGN_USER_TO_PROCESSINSTANCE instead.
     */
    @Deprecated
    private static final VariableFactory<String> ASSIGN_USER_TO_PROCESSINSTANCE = stringVariable("digitalwf_assign_user_to_processinstance");

    private static final VariableFactory<String> APP_ASSIGN_USER_TO_PROCESSINSTANCE = stringVariable("app_assign_user_to_processinstance");

    @EventListener
    public void delegateTask(final DelegateTask delegateTask) {
        if (delegateTask.getEventName().equals("complete")) {

            val user = this.userService.getUser(this.camundaUserAuthenticationProvider.getCurrentUserId());

            val format = new SimpleDateFormat("dd.MM.yyyy HH:mm");
            this.runtimeService
                    .setVariable(delegateTask.getProcessInstanceId(), delegateTask.getTaskDefinitionKey() + "_complete_time", format.format(new Date()));
            this.runtimeService.setVariable(delegateTask.getProcessInstanceId(), delegateTask.getTaskDefinitionKey() + "_complete_user", user.getLhmObjectId());
            this.runtimeService.setVariable(delegateTask.getProcessInstanceId(), delegateTask.getTaskDefinitionKey() + "_complete_user_name",
                    user.getForename() + " " + user.getSurname() + ", " + user.getOu());

            // Deprecated
            Optional<String> assign = ASSIGN_USER_TO_PROCESSINSTANCE.from(delegateTask).getOptional();
            if (assign.isPresent() && "true".equals(assign.get())) {
                this.serviceInstanceAuthService.createAuthorization(delegateTask.getProcessInstanceId(), user.getLhmObjectId());
            }

            assign = APP_ASSIGN_USER_TO_PROCESSINSTANCE.from(delegateTask).getOptional();
            if (assign.isPresent() && "true".equals(assign.get())) {
                this.serviceInstanceAuthService.createAuthorization(delegateTask.getProcessInstanceId(), user.getLhmObjectId());
            }
        }
    }

}
