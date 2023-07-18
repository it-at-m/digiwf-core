package io.muenchendigital.digiwf.cockpit;

import io.muenchendigital.digiwf.cockpit.security.camunda.OAuthContainerBasedAuthenticationProvider;
import io.muenchendigital.digiwf.spring.security.SecurityConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.rest.exception.RestExceptionHandler;
import org.camunda.bpm.engine.rest.impl.CamundaRestResources;
import org.camunda.bpm.webapp.impl.security.auth.ContainerBasedAuthenticationFilter;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.web.filter.ForwardedHeaderFilter;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.Set;

import static io.muenchendigital.digiwf.spring.security.SecurityConfiguration.SECURITY;


@ComponentScan
@EnableConfigurationProperties({
    CamundaWebappsProperties.class
})
@AutoConfigureAfter(
    SecurityConfiguration.class
)
@Profile(SECURITY)
@Slf4j
public class CamundaWebappsAutoConfiguration {

  // The ForwardedHeaderFilter is required to correctly assemble the redirect URL for OAUth2 login. Without the filter, Spring generates an http URL even though the OpenShift
  // route is accessed through https.
  @Bean
  public FilterRegistrationBean<ForwardedHeaderFilter> forwardedHeaderFilter() {
    FilterRegistrationBean<ForwardedHeaderFilter> filterRegistrationBean = new FilterRegistrationBean<>();
    filterRegistrationBean.setFilter(new ForwardedHeaderFilter());
    filterRegistrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
    return filterRegistrationBean;
  }


  /**
   * Registriert den Filter für die Camunda-Authentifizierung.
   */
  @Bean
  // @Order(SecurityProperties.BASIC_AUTH_ORDER - 15)
  public FilterRegistrationBean<ContainerBasedAuthenticationFilter> containerBasedAuthenticationFilter() {
    log.trace("CamundaWebAppsSecurityConfiguration.containerBasedAuthenticationFilter()....");
    FilterRegistrationBean<ContainerBasedAuthenticationFilter> filterRegistrationBean = new FilterRegistrationBean<>();
    filterRegistrationBean.setFilter(new ContainerBasedAuthenticationFilter());
    filterRegistrationBean.setInitParameters(Collections.singletonMap("authentication-provider", OAuthContainerBasedAuthenticationProvider.class.getName()));
    filterRegistrationBean.setOrder(101); // make sure the filter is registered after the Spring Security Filter Chain
    filterRegistrationBean.addUrlPatterns("/*");
    return filterRegistrationBean;
  }

}
