/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */
package io.muenchendigital.digiwf.shared.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * The central class for configuration of all security aspects.
 */
@Configuration
@Profile("!no-security")
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfiguration {

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @Value("${spring.security.oauth2.client.provider.keycloak.user-info-uri}")
    private String userInfoUri;

    private static final String[] PERMITTED_URLS = {
            "/engine-rest/**", // allow access to rest api
            "/actuator/info", // allow access to /actuator/info
            "/actuator/health", // allow access to /actuator/health for OpenShift Health Check
            "/actuator/metrics", // allow access to /actuator/metrics for Prometheus monitoring in OpenShift
    };

    @Bean
    public SecurityFilterChain configure(final HttpSecurity http) throws Exception {
        // @formatter:off
        http
                .csrf()
                .ignoringAntMatchers(PERMITTED_URLS)
                .disable()
                .authorizeRequests()
                .antMatchers(PERMITTED_URLS).permitAll()
                .anyRequest().authenticated()
                .and().oauth2ResourceServer().jwt()
                .jwtAuthenticationConverter(new JwtUserInfoAuthenticationConverter(
                        new UserInfoAuthoritiesService(this.userInfoUri, this.restTemplateBuilder)));

        return http.build();
        // @formatter:on
    }


}
