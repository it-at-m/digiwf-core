/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */
package io.muenchendigital.digiwf.shared.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * The central class for configuration of all security aspects.
 */
@Configuration
@Profile("!no-security")
@EnableResourceServer
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfiguration extends ResourceServerConfigurerAdapter {

    @Value("${security.oauth2.resource.user-info-uri}")
    private String userInfoUri;

    @Value("${security.oauth2.client.client-id}")
    private String resourceId;

    @Autowired
    private String[] swaggerWhitelist;

    @Override
    public void configure(final ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId(null);
    }

    @Override
    public void configure(final HttpSecurity http) throws Exception {
        http
                .antMatcher("/**").authorizeRequests()
                // allow access to rest api
                .antMatchers("/engine-rest/**").permitAll()
                // allow access to /actuator/info
                .antMatchers("/actuator/info").permitAll()
                // allow access to /actuator/health for OpenShift Health Check
                .antMatchers("/actuator/health").permitAll()
                // allow access to /actuator/metrics for Prometheus monitoring in OpenShift
                .antMatchers("/actuator/metrics").permitAll()
                // allow access to swagger in non-prod environments
                .antMatchers(this.swaggerWhitelist).permitAll()
                .antMatchers("/**").authenticated();
    }

    @Bean
    @Primary
    public UserInfoTokenServices tokenServices() {
        return new CustomUserInfoTokenServices(this.userInfoUri, this.resourceId);
    }

}
