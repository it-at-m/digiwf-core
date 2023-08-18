/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.cockpit.security.authorization;

import de.muenchen.oss.digiwf.cockpit.CamundaWebappsProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.camunda.bpm.engine.AuthorizationService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import java.util.stream.Stream;

import static de.muenchen.oss.digiwf.cockpit.security.authorization.AuthorizationHelper.*;

/**
 * Authorization initializer for local development.
 */
@Component
@RequiredArgsConstructor
@Slf4j
@Profile("local")
public class LocalAuthorizationInitializer {

    private final AuthorizationService authorizationService;

    @PostConstruct
    public void init() {
        // let local test users see everything
        val johnDoe = "123456789";
        val janeDoe = "234567890";
        Stream.of(johnDoe, janeDoe).forEach(user -> {
            setupUserProcessDefinitionPermissions(authorizationService, user);
            setupUserProcessInstancePermissions(authorizationService, user);
            setupUserTaskPermissions(authorizationService, user);
            setupUserBatchPermissions(authorizationService, user);
            setupUserHistoricTaskPermissions(authorizationService, user);
            setupUserHistoricProcessInstancePermissions(authorizationService, user);
            setupUserDashboardPermissions(authorizationService, user);
            setupUserReportPermissions(authorizationService, user);
            setupUserOpLogPermissions(authorizationService, user);
            setupUserDeploymentPermissions(authorizationService, user);
            setupUserDecisionRequirementPermissions(authorizationService, user);
            setupUserSystemPermissions(authorizationService, user);
        });
    }

}
