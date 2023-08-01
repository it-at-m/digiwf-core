/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */
package io.muenchendigital.digiwf.shared.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.web.SecurityFilterChain;

/**
 * The central class for configuration of all security aspects.
 */
@Configuration
@Profile("!no-security")
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
@RequiredArgsConstructor
public class SecurityConfiguration {

  private static final String[] PERMITTED_URLS = {
      "/error", // allow the error page
      "/actuator/info", // allow access to /actuator/info
      "/actuator/health", // allow access to /actuator/health for OpenShift Health Check
      "/actuator/metrics", // allow access to /actuator/metrics for Prometheus monitoring in OpenShift
      "/swagger-ui/index.html", // allow access to swagger
      "/swagger-ui*/*swagger-initializer.js", // allow access to swagger
      "/swagger-ui*/**", // allow access to swagger
      "/v3/api-docs/*", // allow access to swagger
      "/v3/api-docs", // allow access to swagger
      "/camunda/**" // allow access to camunda cockpit
  };

  private final RestTemplateBuilder restTemplateBuilder;

  @Value("${spring.security.oauth2.client.provider.keycloak.user-info-uri}")
  private String userInfoUri;

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
                    .anyRequest().authenticated()
                .and()
            .oauth2ResourceServer()
                .jwt()
                    // This custom converter lazily fetches UserInfo Endpoint and reads the "authorities" configured in the
                    // SSO. Also includes user_roles.
                    .jwtAuthenticationConverter(customCachingUserServiceConverter())
            .and();
        return http.build();
        // @formatter:on
  }

  /**
   * Creates a converter from JWT to AbstractAuthenticationToken.
   *
   * @return custom converter.
   * FIXME: this implementation is taken from the reference architecture
   * It is required to map the information from the "authorities" field of the response from UserInfo endpoint
   * to the Spring Granted Authorities. This implementation should remain <b>AS-IS</b>, until mid-term refactoring of security.
   * Weak points:
   * - the converter should only convert instead of accessing the REST endpoints.
   * - the value of the endpoint URL is hard-coded based on config key-name and injected via @Value instead of usage of OAuth2ClientProperties
   * - The Cache is configured internally in the service (check CacheConfiguration for other caches)
   * - Consider this https://docs.spring.io/spring-security/reference/servlet/oauth2/login/advanced.html which states,
   * that the configuration of three independent facilities:
   * <pre>
   * .userInfoEndpoint(userInfo -> userInfo
   * .userAuthoritiesMapper(this.userAuthoritiesMapper())
   * .userService(this.oauth2UserService())
   * .oidcUserService(this.oidcUserService())
   * )
   * </pre>
   * <p>
   * Better implementation would be:
   * - provide a clear authorities mapper (stateless)
   * - provide extension of the DEFAULT OAUth2 User Service see https://docs.spring.io/spring-security/reference/servlet/oauth2/login/advanced.html#oauth2login-advanced-oauth2-user-service
   */
  private Converter<Jwt, AbstractAuthenticationToken> customCachingUserServiceConverter() {
    return new JwtUserInfoAuthenticationConverter(new UserInfoAuthoritiesService(this.userInfoUri, this.restTemplateBuilder));
  }
}
