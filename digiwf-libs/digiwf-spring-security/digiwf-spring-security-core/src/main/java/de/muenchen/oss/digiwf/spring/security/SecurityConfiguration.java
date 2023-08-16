/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik
 * der Landeshauptstadt München, 2022
 */
package de.muenchen.oss.digiwf.spring.security;

import de.muenchen.oss.digiwf.spring.security.userinfo.UserInfoAuthoritiesConverter;
import io.muenchendigital.digiwf.spring.security.client.ClientParameters;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import javax.annotation.PostConstruct;
import java.util.Collection;

import static de.muenchen.oss.digiwf.spring.security.SecurityConfiguration.SECURITY;

/**
 * The central class for configuration of all security aspects.
 */
@Configuration
@Profile(SECURITY)
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
@RequiredArgsConstructor
public class SecurityConfiguration {

  /**
   * Activates security.
   */
  public static final String SECURITY = "!no-security";
  public static final int DEFAULT_SECURITY_ORDER = 77;
  public static final String SPRING_ROLE_PREFIX = "ROLE_";

  private final RestTemplateBuilder restTemplateBuilder;
  private final SpringSecurityProperties springSecurityProperties;
  private final Environment environment;
  private ClientParameters clientParameters;

  @PostConstruct
  void setupClientRegistration() {
    this.clientParameters = ClientParameters.fromEnvironment(environment, springSecurityProperties.getClientRegistration());
  }


  @Bean
  @Order(DEFAULT_SECURITY_ORDER)
  public SecurityFilterChain mainSecurityFilterChain(
      final HttpSecurity http,
      final JwtAuthenticationConverter jwtAuthenticationConverter
  ) throws Exception {
    // @formatter:off
    http
        .csrf( csrf -> csrf
            .ignoringAntMatchers(springSecurityProperties.getPermittedUrls())
            .disable()
        )
        .authorizeRequests( requests -> requests
            .antMatchers(HttpMethod.OPTIONS).permitAll()
            .antMatchers(springSecurityProperties.getPermittedUrls()).permitAll()
            .anyRequest().authenticated()
        )
        .oauth2ResourceServer( server -> server
            .jwt()
            .jwtAuthenticationConverter(jwtAuthenticationConverter)
        )
        ;
    // @formatter:on
    return http.build();
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
  @Bean
  public JwtAuthenticationConverter customCachingUserServiceConverter(final Converter<Jwt, Collection<GrantedAuthority>> userInfoAuthoritiesConverter) {
    val converter = new JwtAuthenticationConverter();
    converter.setJwtGrantedAuthoritiesConverter(userInfoAuthoritiesConverter);
    return converter;
  }

  @Bean
  public Converter<Jwt, Collection<GrantedAuthority>> userInfoAuthoritiesConverter() {
    return new UserInfoAuthoritiesConverter(clientParameters.getProviderUserInfoUri(), restTemplateBuilder);
  }

}

