package de.muenchen.oss.digiwf.cockpit.security.camunda;

import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Disable CSRF Camunda Filter.
 *
 * <a href="https://forum.camunda.io/t/how-to-disable-csrfpreventionfilter/13095/8">Camunda Forum</a>
 */
@Configuration
public class CsrfDisablingConfiguration {
  private static final String CSRF_PREVENTION_FILTER = "CsrfPreventionFilter";

  /**
   * Overwrite csrf filter from Camunda configured here
   * org.camunda.bpm.spring.boot.starter.webapp.CamundaBpmWebappInitializer
   * org.camunda.bpm.spring.boot.starter.webapp.filter.SpringBootCsrfPreventionFilter
   * Is configured with basically a 'no-op' filter
   */
  @Bean
  public ServletContextInitializer csrfOverwrite() {
    return servletContext -> servletContext.addFilter(CSRF_PREVENTION_FILTER, (request, response, chain) -> chain.doFilter(request, response));
  }
}
