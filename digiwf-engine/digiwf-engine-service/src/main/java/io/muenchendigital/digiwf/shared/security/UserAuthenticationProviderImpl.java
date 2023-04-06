/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.shared.security;

import org.springframework.context.annotation.Profile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

/**
 * User authentication provider.
 * Extracts the name from the token.
 *
 * @author externer.dl.horn
 */
@Component
@Profile("!no-security")
public class UserAuthenticationProviderImpl implements UserAuthenticationProvider {

    private static final String USER_ATTRIBUTE = "user_name";
    public static final String NAME_UNAUTHENTICATED_USER = "unauthenticated";

    @Override
    public String getLoggedInUser() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof Jwt) {
            final Jwt jwt = (Jwt) authentication.getPrincipal();
            return (String) jwt.getClaims().get(USER_ATTRIBUTE);
        }
        return NAME_UNAUTHENTICATED_USER;
    }

}
