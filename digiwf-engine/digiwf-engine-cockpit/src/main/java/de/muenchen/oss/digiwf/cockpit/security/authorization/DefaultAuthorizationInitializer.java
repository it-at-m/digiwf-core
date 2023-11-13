/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.cockpit.security.authorization;

import de.muenchen.oss.digiwf.cockpit.CamundaWebappsProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.AuthorizationService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static de.muenchen.oss.digiwf.cockpit.security.authorization.AuthorizationHelper.*;
import static de.muenchen.oss.digiwf.spring.security.SecurityConfiguration.SECURITY;

/**
 * Authorization initializer.
 */
@Component
@RequiredArgsConstructor
@Slf4j
@Profile(SECURITY)
public class DefaultAuthorizationInitializer {

  private final AuthorizationService authorizationService;
  private final CamundaWebappsProperties camundaWebappsProperties;
  private static final String LEGACY_ADMIN_USER = "digiwf";

  @PostConstruct
  public void init() {
    // SSO role
    setupGroupAppPermissions(authorizationService, camundaWebappsProperties.getWebAppRole());
    setupGroupAuthorizationPermissions(authorizationService, camundaWebappsProperties.getWebAppRole());
     setupGroupGroupPermissions(authorizationService, camundaWebappsProperties.getWebAppRole());
    setupGroupGroupMembershipPermissions(authorizationService, camundaWebappsProperties.getWebAppRole());
    setupGroupUserPermissions(authorizationService, camundaWebappsProperties.getWebAppRole());

    // admin user
    setupUserAppPermissions(authorizationService, LEGACY_ADMIN_USER);
    setupUserProcessDefinitionPermissions(authorizationService, LEGACY_ADMIN_USER);
    setupUserProcessInstancePermissions(authorizationService, LEGACY_ADMIN_USER);
    setupUserTaskPermissions(authorizationService, LEGACY_ADMIN_USER);
    setupUserHistoricTaskPermissions(authorizationService, LEGACY_ADMIN_USER);
    setupUserHistoricProcessInstancePermissions(authorizationService, LEGACY_ADMIN_USER);
    setupUserBatchPermissions(authorizationService, LEGACY_ADMIN_USER);
    setupUserDashboardPermissions(authorizationService, LEGACY_ADMIN_USER);
    setupUserReportPermissions(authorizationService, LEGACY_ADMIN_USER);
    setupUserOpLogPermissions(authorizationService, LEGACY_ADMIN_USER);
    setupUserDeploymentPermissions(authorizationService, LEGACY_ADMIN_USER);
    setupUserDecisionRequirementPermissions(authorizationService, LEGACY_ADMIN_USER);
    setupUserDecisionPermissions(authorizationService, LEGACY_ADMIN_USER);
    setupUserSystemPermissions(authorizationService, LEGACY_ADMIN_USER);
  }

}
