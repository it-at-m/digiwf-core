/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.shared.security;

import io.muenchendigital.digiwf.legacy.user.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import lombok.val;
import org.camunda.bpm.engine.IdentityService;

import javax.servlet.*;
import java.io.IOException;

/**
 * Filter to set authentication / authorization information.
 * This information is used for restrict access to resources.
 *
 * @author externer.dl.horn
 */
@RequiredArgsConstructor
@Slf4j
public class CamundaUserAuthenticationFilter implements Filter {

    private final UserAuthenticationProvider userProvider;
    private final UserService userService;
    private final IdentityService identityService;

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {
        val username = this.userProvider.getLoggedInUser();
        try {
            val user = this.userService.getUserByUserName(username);

            //could be a serviceaccount
            if (user.isPresent()) {
                val groups = this.userService.getGroups(user.get().getLhmObjectId());
                this.identityService.setAuthentication(user.get().getLhmObjectId(), groups);
                log.info("Accessing {} [ {} ]", username, groups);
            } else {
                this.identityService.setAuthentication(username, null);
                log.info("Accessing {}", username);
            }
            chain.doFilter(request, response);
        } finally {
            this.identityService.clearAuthentication();
        }
    }

    @Override
    public void destroy() {

    }

}
