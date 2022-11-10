/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.shared.security;

import org.springframework.context.annotation.Profile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * User authentication provider.
 * Extracts the name from the token.
 *
 * @author externer.dl.horn
 */
@Component
@Profile("!no-security")
public class UserAuthenticationProviderImpl implements UserAuthenticationProvider {
    private static final String NAME_UNAUTHENTICATED_USER = "unauthenticated";

    private static final String TOKEN_USER_NAME = "user_name";

    @Override
    public String getLoggedInUser() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof OAuth2Authentication) {
            final OAuth2Authentication oauth2Authentication = (OAuth2Authentication) authentication;
            final HashMap details = (HashMap) oauth2Authentication.getUserAuthentication().getDetails();
            return (String) details.get(TOKEN_USER_NAME);
        } else {
            return NAME_UNAUTHENTICATED_USER;
        }
    }

}
