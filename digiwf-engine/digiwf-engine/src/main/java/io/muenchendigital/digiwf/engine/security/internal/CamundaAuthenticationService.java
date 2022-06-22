/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.engine.security.internal;

import io.muenchendigital.digiwf.engine.security.api.UserContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.camunda.bpm.engine.IdentityService;

/**
 * Filter to set authentication / authorization information.
 * This information is used for restrict access to resources.
 *
 * @author externer.dl.horn
 */
@RequiredArgsConstructor
@Slf4j
public class CamundaAuthenticationService {

    private final UserContext userContext;
    private final IdentityService identityService;

    public void setAuthentication() {
        val userId = this.userContext.getLoggedInUserId();
        val userGroups = this.userContext.getUserGroups();
        log.info("Accessing {} [ {} ]", userId, userGroups);
        this.identityService.setAuthentication(userId, userGroups);
    }

    public void clearAuthentication() {
        this.identityService.clearAuthentication();
    }

}
