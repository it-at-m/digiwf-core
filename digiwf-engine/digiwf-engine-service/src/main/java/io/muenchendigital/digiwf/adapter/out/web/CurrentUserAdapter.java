/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.adapter.out.web;

import io.muenchendigital.digiwf.application.port.out.CurrentUserPort;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

import static io.muenchendigital.digiwf.adapter.out.ldap.LdapUserAdapter.getCurrentHttpRequest;
import static io.muenchendigital.digiwf.task.HttpHeaders.HEADER_AUTHORIZED_USERNAME;

/**
 * User authentication provider.
 * Extracts the name from the token.
 *
 * @author externer.dl.horn
 */
@Component
@Profile("!no-security")
class CurrentUserAdapter implements CurrentUserPort {

    static final String USER_ATTRIBUTE = "user_name";
    static final String ROLES_ATTRIBUTE = "roles";
    static final String NAME_UNAUTHENTICATED_USER = "unauthenticated";


    @Override
    public String getLoggedInUsername() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        //check if user is a user string -> AnonymousAuthenticationToken
        if (authentication.getPrincipal() instanceof Jwt) {
            final Jwt jwt = (Jwt) authentication.getPrincipal();
            return (String) jwt.getClaims().get(USER_ATTRIBUTE);
        }
        return NAME_UNAUTHENTICATED_USER;
    }


    @Override
    public Set<String> getLoggedInUserRoles() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof Jwt) {
            final Jwt jwt = (Jwt) authentication.getPrincipal();
            return Optional.ofNullable((Set<String>) jwt.getClaims().get(ROLES_ATTRIBUTE)).orElse(Set.of());
        }
        return Set.of();
    }


    //TODO in einem ersten Filter als user setzen, wenn vorhanden -> AnonymousAuthenticationToken
    private Optional<String> extractUserNameFromHeader() {
        return getCurrentHttpRequest().map(it -> it.getHeader(HEADER_AUTHORIZED_USERNAME));
    }

}
