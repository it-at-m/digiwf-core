/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */
package io.muenchendigital.digiwf.shared.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@Profile("no-security")
@EnableResourceServer
@EnableWebSecurity
public class NoSecurityConfiguration extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(final ResourceServerSecurityConfigurer resources) {
        resources.resourceId(null);
    }

    @Override
    public void configure(final HttpSecurity http) throws Exception {
        http
                .headers()
                .frameOptions()
                .disable()
                .and().antMatcher("/**")
                .authorizeRequests()
                .anyRequest()
                .permitAll()
                .and().csrf()
                .disable();
    }

}
