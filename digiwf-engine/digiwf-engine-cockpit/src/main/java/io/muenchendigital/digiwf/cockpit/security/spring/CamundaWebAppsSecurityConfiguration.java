package io.muenchendigital.digiwf.cockpit.security.spring;

import io.muenchendigital.digiwf.cockpit.CamundaWebappsProperties;
import io.muenchendigital.digiwf.spring.security.SpringSecurityProperties;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import static io.muenchendigital.digiwf.spring.security.SecurityConfiguration.DEFAULT_SECURITY_ORDER;
import static org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter.DEFAULT_AUTHORIZATION_REQUEST_BASE_URI;
import static org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter.DEFAULT_FILTER_PROCESSES_URI;

@Configuration
@Slf4j
public class CamundaWebAppsSecurityConfiguration {

  private static final String PREFIX_CAMUNDA_APP = "/camunda/app";
  private static final String[] CAMUNDA_APP_PATHS = {
      PREFIX_CAMUNDA_APP + "/**",
      "/camunda/api/**",
      "/camunda/lib/**",
  };

  private final CamundaWebappsProperties camundaWebappsProperties;

  public CamundaWebAppsSecurityConfiguration(
      CamundaWebappsProperties camundaWebappsProperties
  ) {
    this.camundaWebappsProperties = camundaWebappsProperties;
  }

  @Bean
  @Order(DEFAULT_SECURITY_ORDER - 100)
  public SecurityFilterChain camundaWebAppFilterChain(
      final HttpSecurity http,
      final JwtAuthenticationConverter jwtAuthenticationConverter,
      final SpringSecurityProperties springSecurityProperties
  ) throws Exception {

    val oAuth2UserService = new TokenParsingOAuth2UserService(jwtAuthenticationConverter);

    // @formatter:off
    http
        .requestMatchers()
          .antMatchers(CAMUNDA_APP_PATHS)
          .and()
        // Disable CSRF for these paths
        .csrf()
          .disable()
        // Any requests on these paths require the configured role
        .authorizeRequests()
          .anyRequest().hasRole(camundaWebappsProperties.getWebAppRole())
          .and()
        .oauth2ResourceServer()
          .jwt()
            .jwtAuthenticationConverter(jwtAuthenticationConverter)
            .and()
          .and()
        .oauth2Login()
          .authorizationEndpoint()
            // put the authorization endpoint under the prefix so that it is covered by this HttpSecurity
            .baseUri(PREFIX_CAMUNDA_APP + DEFAULT_AUTHORIZATION_REQUEST_BASE_URI)
            .and()
          .userInfoEndpoint()
            // Configure specialized user services - see javadoc of TokenParsingOAuth2UserService for an explanation
            .userService(oAuth2UserService)
            .oidcUserService(new TokenParsingOidcUserService(oAuth2UserService))
            .and()
          // put the login processing endpoint under the  prefix so that is covered by this HttpSecurity. If you change this, remember to also change `spring.security.oauth2.client.registration.<client-registration>.redirect-uri`.
          .loginProcessingUrl(PREFIX_CAMUNDA_APP + DEFAULT_FILTER_PROCESSES_URI)
          // Set the authorization endpoint for the my-client-registration clientRegistration as the login page because that's the only one we want to use.
          .loginPage(PREFIX_CAMUNDA_APP + DEFAULT_AUTHORIZATION_REQUEST_BASE_URI + "/" + springSecurityProperties.getClientRegistration())
        .and()
    ;
    // @formatter:on
    return http.build();

  }
}
