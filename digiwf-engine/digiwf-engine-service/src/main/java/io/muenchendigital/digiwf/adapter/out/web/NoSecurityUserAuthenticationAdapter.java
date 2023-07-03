/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.adapter.out.web;

import io.muenchendigital.digiwf.application.port.out.CurrentUserPort;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * Security provider for no-security environments.
 */
@Component
@Profile("no-security")
public class NoSecurityUserAuthenticationAdapter implements CurrentUserPort {

    public static final String DEFAULT_USER = "externer.john.doe";

    @Override
    public String getLoggedInUsername() {
        return DEFAULT_USER;
    }

    @Override
    public Set<String> getLoggedInUserRoles() {
        return Set.of("role-1");
    }

}
