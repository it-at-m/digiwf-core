/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.adapter.in.web;

import io.muenchendigital.digiwf.application.port.in.HandleAuthenticationUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import java.io.IOException;

/**
 * Camunda Security configuration.
 * Adds the filter retrieving currently logged-in user and setting Camunda authorization to it for all REST requests.
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class DigiWFAuthenticationFilter {

    private final HandleAuthenticationUseCase handleAuthenticationUseCase;

    @Bean
    public FilterRegistrationBean<?> statelessUserAuthenticationFilter() {
        final FilterRegistrationBean<CamundaUserAuthenticationFilter> filterRegistration = new FilterRegistrationBean<>();
        filterRegistration.setFilter(new CamundaUserAuthenticationFilter());
        filterRegistration.setOrder(102); // make sure the filter is registered after the Spring Security Filter Chain
        // install the filter on all protected URLs to propagate the identity from the token to Camunda and Identity Service.
        filterRegistration.addUrlPatterns(
                "/rest/*", // custom rest api
                "/engine-rest/*" // camunda rest api should be protected
        );
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
            try {
                handleAuthenticationUseCase.initializeAuthentication();
                chain.doFilter(request, response);
            } finally {
                handleAuthenticationUseCase.clearAuthentication();
            }
        }

        @Override
        public void destroy() {
        }


    }

}
