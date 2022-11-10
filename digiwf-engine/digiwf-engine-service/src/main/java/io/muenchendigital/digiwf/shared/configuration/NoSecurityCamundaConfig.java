/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.shared.configuration;

import io.muenchendigital.digiwf.legacy.user.domain.service.UserService;
import io.muenchendigital.digiwf.shared.security.NoSecurityCamundaUserAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.IdentityService;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Camunda no-security configuration.
 * Adds the corresponding filter.
 *
 * @author externer.dl.horn
 */
@Configuration
@Profile("no-security")
@RequiredArgsConstructor
public class NoSecurityCamundaConfig {

    private final IdentityService identityService;
    private final UserService userService;

    @Bean
    public FilterRegistrationBean statelessUserAuthenticationFilter() {
        final FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
        filterRegistration.setFilter(new NoSecurityCamundaUserAuthenticationFilter(this.userService, this.identityService));
        filterRegistration.setOrder(102); // make sure the filter is registered after the Spring Security Filter Chain
        filterRegistration.addUrlPatterns("/rest/*");
        return filterRegistration;
    }

}
