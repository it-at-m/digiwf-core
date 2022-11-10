/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.shared.security;

import io.muenchendigital.digiwf.legacy.user.domain.service.UserService;
import lombok.RequiredArgsConstructor;

import lombok.val;
import org.camunda.bpm.engine.IdentityService;

import javax.servlet.*;
import java.io.IOException;

/**
 * User authentication Filter for no-security environments.
 *
 * @author externer.dl.horn
 */
@RequiredArgsConstructor
public class NoSecurityCamundaUserAuthenticationFilter implements Filter {

    private final UserService userService;
    private final IdentityService identityService;

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {
        try {
            final String userId = "260"; // default user
            val groups = this.userService.getGroups(userId);
            this.identityService.setAuthentication(userId, groups);
            chain.doFilter(request, response);
        } finally {
            this.identityService.clearAuthentication();
        }
    }

    @Override
    public void destroy() {

    }

}
