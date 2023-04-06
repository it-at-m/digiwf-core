/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.shared.configuration;

import io.muenchendigital.digiwf.legacy.user.domain.service.UserService;
import io.muenchendigital.digiwf.shared.security.UserAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.camunda.bpm.engine.IdentityService;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.servlet.*;
import java.io.IOException;

/**
 * Camunda Security configuration.
 * Adds the filter retrieving currently logged-in user and setting Camunda authorization to it for all REST requests.
 */
@Configuration
@RequiredArgsConstructor
@Profile("!no-security")
@Slf4j
public class CamundaAuthenticationFilterConfiguration {

    private final IdentityService identityService;
    private final UserAuthenticationProvider userProvider;
    private final UserService userService;

    @Bean
    public FilterRegistrationBean<?> statelessUserAuthenticationFilter() {
        final FilterRegistrationBean<CamundaUserAuthenticationFilter> filterRegistration = new FilterRegistrationBean<>();
        filterRegistration.setFilter(new CamundaUserAuthenticationFilter());
        filterRegistration.setOrder(102); // make sure the filter is registered after the Spring Security Filter Chain
        filterRegistration.addUrlPatterns("/rest/*");
        return filterRegistration;
    }

    /**
     * Filter to set authentication / authorization information.
     * This information is used for restrict access to resources.
     */
    @RequiredArgsConstructor
    class CamundaUserAuthenticationFilter implements Filter {

        @Override
        public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {
            val username = userProvider.getLoggedInUser();
            try {
                val user = userService.getUserByUserName(username);
                // could be a service account
                if (user.isPresent()) {
                    val groups = userService.getGroups(user.get().getLhmObjectId());
                    identityService.setAuthentication(user.get().getLhmObjectId(), groups);
                    log.debug("Accessing {} [ {} ]", username, groups);
                } else {
                    identityService.setAuthentication(username, null);
                    log.debug("Accessing {}", username);
                }
                chain.doFilter(request, response);
            } finally {
                identityService.clearAuthentication();
            }
        }

        @Override
        public void destroy() {
        }
    }

}
