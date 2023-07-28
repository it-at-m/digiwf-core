/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.shared.configuration;

import io.muenchendigital.digiwf.legacy.user.domain.service.UserService;
import io.muenchendigital.digiwf.shared.security.UserAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.camunda.bpm.engine.IdentityService;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;

import static de.muenchen.oss.digiwf.task.HttpHeaders.HEADER_AUTHORIZED_USERNAME;

/**
 * Camunda Security configuration.
 * Adds the filter retrieving currently logged-in user and setting Camunda authorization to it for all REST requests.
 */
@Configuration
@RequiredArgsConstructor
@Profile("!no-security")
@Slf4j
public class CamundaAuthenticationFilterConfiguration {

  private final IdentityService identityService;
  private final UserAuthenticationProvider userProvider;
  private final UserService userService;

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
      // first try to read the header of HTTP request.
      val delegatedUserName = extractUserNameFromHeader(request);
      // take that or fallback to username from user provider, reading it from the token
      val username = delegatedUserName.orElseGet(userProvider::getLoggedInUser);
      try {
        val user = userService.getUserByUserName(username);
        // could be a service account
        if (user.isPresent()) {
          val groups = userService.getGroups(user.get().getLhmObjectId());
          identityService.setAuthentication(user.get().getLhmObjectId(), groups);
          log.debug("Accessing {} [ {} ]", username, groups);
        } else {
          identityService.setAuthentication(username, null);
          log.debug("Accessing {}", username);
        }
        chain.doFilter(request, response);
      } finally {
        identityService.clearAuthentication();
      }
    }

    @Override
    public void destroy() {
    }

      /**
       * Tries to detect username based on authorization header.
       * @param request http request.
       * @return username or empty.
       */
    private Optional<String> extractUserNameFromHeader(ServletRequest request) {
      final HttpServletRequest httpRequest = (HttpServletRequest) request;
      return Optional.ofNullable(httpRequest.getHeader(HEADER_AUTHORIZED_USERNAME));
    }
  }

}
