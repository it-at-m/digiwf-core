package de.muenchen.oss.digiwf.cockpit;

import de.muenchen.oss.digiwf.cockpit.security.camunda.CamundaApiAdminTokenBasedAuthenticationFilter;
import de.muenchen.oss.digiwf.cockpit.security.camunda.OAuthContainerBasedAuthenticationProvider;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.webapp.impl.security.auth.ContainerBasedAuthenticationFilter;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.web.filter.ForwardedHeaderFilter;
import de.muenchen.oss.digiwf.spring.security.SecurityConfiguration;

import java.util.Collections;

import static de.muenchen.oss.digiwf.spring.security.SecurityConfiguration.SECURITY;
import static org.camunda.bpm.engine.rest.security.auth.ProcessEngineAuthenticationFilter.AUTHENTICATION_PROVIDER_PARAM;


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
  public FilterRegistrationBean<ContainerBasedAuthenticationFilter> containerBasedAuthenticationFilter() {
    log.trace("CamundaWebAppsSecurityConfiguration.containerBasedAuthenticationFilter()....");
    FilterRegistrationBean<ContainerBasedAuthenticationFilter> filterRegistrationBean = new FilterRegistrationBean<>();
    filterRegistrationBean.setFilter(new ContainerBasedAuthenticationFilter());
    filterRegistrationBean.setInitParameters(Collections.singletonMap(AUTHENTICATION_PROVIDER_PARAM, OAuthContainerBasedAuthenticationProvider.class.getName()));
    filterRegistrationBean.setOrder(101); // make sure the filter is registered after the Spring Security Filter Chain
    filterRegistrationBean.addUrlPatterns("/camunda/app/*");
    filterRegistrationBean.addUrlPatterns("/camunda/lib/*");
    filterRegistrationBean.addUrlPatterns("/camunda/api/engine/*"); // api engine - yes, api admin - no, see below...
    filterRegistrationBean.addUrlPatterns("/camunda/api/cockpit/plugin/*"); // api cockpit plugins - yes, api admin - no, see below...
    return filterRegistrationBean;
  }

  /**
   * Registriert den Filter für die Camunda Admin Token Based Authentifizierung statt der Session-basierten.
   */
  @Bean
  public FilterRegistrationBean<CamundaApiAdminTokenBasedAuthenticationFilter> camundaApiAdminTokenBasedAuthenticationFilter() {
    log.trace("CamundaWebAppsSecurityConfiguration.camundaApiAdminTokenBasedAuthenticationFilter()....");
    FilterRegistrationBean<CamundaApiAdminTokenBasedAuthenticationFilter> filterRegistrationBean = new FilterRegistrationBean<>();
    filterRegistrationBean.setFilter(new CamundaApiAdminTokenBasedAuthenticationFilter());
    filterRegistrationBean.setInitParameters(Collections.singletonMap(AUTHENTICATION_PROVIDER_PARAM, OAuthContainerBasedAuthenticationProvider.class.getName()));
    filterRegistrationBean.setOrder(101); // make sure the filter is registered after the Spring Security Filter Chain
    filterRegistrationBean.addUrlPatterns("/camunda/api/admin/auth/user/default");
    return filterRegistrationBean;
  }

}
