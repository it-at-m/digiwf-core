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
    // let local test user see everything
    val johnDoe = "123456789";

    setupUserProcessDefinitionPermissions(authorizationService, johnDoe);
    setupUserProcessInstancePermissions(authorizationService, johnDoe);
    setupUserTaskPermissions(authorizationService, johnDoe);
    setupUserBatchPermissions(authorizationService, johnDoe);
    setupUserHistoricTaskPermissions(authorizationService, johnDoe);
    setupUserHistoricProcessInstancePermissions(authorizationService, johnDoe);
    setupUserDashboardPermissions(authorizationService, johnDoe);
    setupUserReportPermissions(authorizationService, johnDoe);
    setupUserOpLogPermissions(authorizationService, johnDoe);
    setupUserDeploymentPermissions(authorizationService, johnDoe);
    setupUserDecisionRequirementPermissions(authorizationService, johnDoe);
    setupUserSystemPermissions(authorizationService, johnDoe);

  }

}
