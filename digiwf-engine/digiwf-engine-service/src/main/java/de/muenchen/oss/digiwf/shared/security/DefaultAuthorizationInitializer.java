/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.shared.security;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Authorization initializer for in-memory environments.
 *
 * @author externer.dl.horn
 */
@Component
@RequiredArgsConstructor
public class DefaultAuthorizationInitializer {

    //    private final AuthorizationService authorizationService;

    //    @PostConstruct
    //    public void init() {
    //                final AuthorizationEntity taskAuth = new AuthorizationEntity(AUTH_TYPE_GRANT);
    //                taskAuth.setUserId("admin");
    //                taskAuth.addPermission(Permissions.ALL);
    //                taskAuth.setResource(Resources.TASK);
    //                taskAuth.setResourceId(ANY);
    //                this.authorizationService.saveAuthorization(taskAuth);
    //
    //                final AuthorizationEntity processDefinitionAuth = new AuthorizationEntity(AUTH_TYPE_GRANT);
    //                processDefinitionAuth.setUserId("admin");
    //                processDefinitionAuth.addPermission(Permissions.READ);
    //                processDefinitionAuth.addPermission(Permissions.READ_INSTANCE);
    //                processDefinitionAuth.addPermission(Permissions.CREATE_INSTANCE);
    //                processDefinitionAuth.addPermission(Permissions.READ_HISTORY);
    //                processDefinitionAuth.setResource(Resources.PROCESS_DEFINITION);
    //                processDefinitionAuth.setResourceId(ANY);
    //                this.authorizationService.saveAuthorization(processDefinitionAuth);
    //
    //                final AuthorizationEntity processInstanceAuth = new AuthorizationEntity(AUTH_TYPE_GRANT);
    //                processInstanceAuth.setUserId("admin");
    //                processInstanceAuth.addPermission(Permissions.CREATE);
    //                processInstanceAuth.addPermission(Permissions.READ);
    //                processInstanceAuth.setResource(Resources.PROCESS_INSTANCE);
    //                processInstanceAuth.setResourceId(ANY);
    //                this.authorizationService.saveAuthorization(processInstanceAuth);
    //
    //                final AuthorizationEntity processInstanceAuth = new AuthorizationEntity(AUTH_TYPE_GRANT);
    //                processInstanceAuth.setUserId("admin");
    //                processInstanceAuth.addPermission(Permissions.CREATE);
    //                processInstanceAuth.setResource(Resources.PROCESS_INSTANCE);
    //                processDefinitionAuth.setResourceId(ANY);
    //                this.authorizationService.saveAuthorization(processInstanceAuth);
    //}
}
