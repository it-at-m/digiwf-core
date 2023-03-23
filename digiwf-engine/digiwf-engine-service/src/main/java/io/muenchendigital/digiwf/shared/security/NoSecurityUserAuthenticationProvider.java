/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.shared.security;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * Security provider for no-security environments.
 */
@Component
@Profile("no-security")
public class NoSecurityUserAuthenticationProvider implements UserAuthenticationProvider {

    public static final String DEFAULT_USER = "externer.john.doe";

    @Override
    public String getLoggedInUser() {
        return DEFAULT_USER;
    }

}
