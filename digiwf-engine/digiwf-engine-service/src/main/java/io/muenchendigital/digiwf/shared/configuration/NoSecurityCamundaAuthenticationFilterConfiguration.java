/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.shared.configuration;

import io.muenchendigital.digiwf.legacy.user.domain.service.UserService;
import io.muenchendigital.digiwf.shared.security.UserAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.camunda.bpm.engine.IdentityService;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.servlet.*;
import java.io.IOException;

/**
 * Camunda no-security configuration.
 * Adds the corresponding filter.
 *
 * @author externer.dl.horn
 */
@Configuration
@Profile("no-security")
@RequiredArgsConstructor
public class NoSecurityCamundaAuthenticationFilterConfiguration {

    private final IdentityService identityService;
    private final UserService userService;
    private final UserAuthenticationProvider userAuthenticationProvider;

    @Bean
    public FilterRegistrationBean<?> statelessUserAuthenticationFilter() {
        final FilterRegistrationBean<NoSecurityCamundaUserAuthenticationFilter> filterRegistration = new FilterRegistrationBean<>();
        filterRegistration.setFilter(new NoSecurityCamundaUserAuthenticationFilter());
        filterRegistration.setOrder(102); // make sure the filter is registered after the Spring Security Filter Chain
        filterRegistration.addUrlPatterns("/rest/*");
        return filterRegistration;
    }

    /**
     * User authentication Filter for no-security environments.
     */
    class NoSecurityCamundaUserAuthenticationFilter implements Filter {

        @Override
        public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {
            try {
                final String username = userAuthenticationProvider.getLoggedInUser();
                val user = userService.getUserByUserName(username);
                if (user.isPresent()) {
                    val groups = userService.getGroups(user.get().getLhmObjectId());
                    identityService.setAuthentication(user.get().getLhmObjectId(), groups);
                } else {
                    identityService.setAuthentication(username, null);
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
