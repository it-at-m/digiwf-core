/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */
package de.muenchen.oss.digiwf.shared.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Profile("no-security")
@EnableWebSecurity
public class NoSecurityConfiguration {

    @Bean
    public SecurityFilterChain mainSecurityFilterChain(HttpSecurity http) throws Exception {
        // @formatter:off
        http
                .headers()
                    .frameOptions().disable()
                    .and()
                .authorizeRequests()
                    .anyRequest().permitAll()
                    .and()
                .csrf()
                    .disable();
        // @formatter:on
        return http.build();
    }

}
