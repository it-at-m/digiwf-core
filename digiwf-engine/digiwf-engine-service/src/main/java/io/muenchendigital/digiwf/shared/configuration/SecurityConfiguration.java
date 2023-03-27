/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */
package io.muenchendigital.digiwf.shared.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
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
@RequiredArgsConstructor
public class SecurityConfiguration {

  private final GrantedAuthoritiesConverter grantedAuthoritiesConverter;

  private static final String[] PERMITTED_URLS = {
      "/error", // allow the error page
      "/actuator/info", // allow access to /actuator/info
      "/actuator/health", // allow access to /actuator/health for OpenShift Health Check
      "/actuator/metrics", // allow access to /actuator/metrics for Prometheus monitoring in OpenShift
      "/swagger-ui/index.html", // allow access to swagger
      "/swagger-ui*/*swagger-initializer.js", // allow access to swagger
      "/swagger-ui*/**", // allow access to swagger
  };

  /**
   * Protected URLs, the access will be protected by the token.
   */
  public static final String[] PROTECTED = {
      "/api/**",
      "/rest/**",
      "/engine-rest/**", // camunda rest api
  };

  @Bean
  public SecurityFilterChain configure(final HttpSecurity http) throws Exception {
    // @formatter:off
        http
            .csrf()
                .ignoringAntMatchers(PERMITTED_URLS)
                .disable()
            .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .antMatchers(PERMITTED_URLS).permitAll()
                .antMatchers(PROTECTED).authenticated()
                .and()
            .oauth2ResourceServer()
                .jwt()
                .jwtAuthenticationConverter(grantedAuthoritiesConverter)
                .and()
                .and()
        ;
        return http.build();
        // @formatter:on
  }

}
