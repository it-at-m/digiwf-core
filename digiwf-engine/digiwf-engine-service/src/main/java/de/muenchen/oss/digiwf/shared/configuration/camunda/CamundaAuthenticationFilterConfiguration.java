/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.shared.configuration.camunda;

import de.muenchen.oss.digiwf.legacy.user.domain.service.UserService;
import de.muenchen.oss.digiwf.spring.security.authentication.UserAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.IdentityService;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Camunda Security configuration.
 * Adds the filter retrieving currently logged-in user and setting Camunda authorization to it for all REST requests.
 */
@Configuration
@RequiredArgsConstructor
@Slf4j
public class CamundaAuthenticationFilterConfiguration {

  @Bean
  public FilterRegistrationBean<?> statelessUserAuthenticationFilter(
      IdentityService identityService,
      UserAuthenticationProvider userProvider,
      UserService userService

  ) {
    final FilterRegistrationBean<CamundaUserAuthenticationFilter> filterRegistration = new FilterRegistrationBean<>();
    filterRegistration.setFilter(new CamundaUserAuthenticationFilter(
        identityService,
        userProvider,
        userService
    ));
    filterRegistration.setOrder(102); // make sure the filter is registered after the Spring Security Filter Chain
    // install the filter on all protected URLs to propagate the identity from the token to Camunda and Identity Service.
    filterRegistration.addUrlPatterns(
        "/rest/*", // custom rest api
        "/engine-rest/*" // camunda rest api should be protected
    );
    return filterRegistration;
  }
}
