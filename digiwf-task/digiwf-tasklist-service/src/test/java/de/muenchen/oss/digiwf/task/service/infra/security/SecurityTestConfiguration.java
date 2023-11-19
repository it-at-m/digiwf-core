package de.muenchen.oss.digiwf.task.service.infra.security;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration;
import org.springframework.boot.autoconfigure.security.oauth2.resource.servlet.OAuth2ResourceServerAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

@Profile("itest")
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableGlobalMethodSecurity(jsr250Enabled = true, prePostEnabled = true)
@EnableWebSecurity
@EnableAutoConfiguration(exclude = {
    OAuth2ResourceServerAutoConfiguration.class,
    OAuth2ClientAutoConfiguration.class,
    SecurityAutoConfiguration.class
})
@Order(1)
public class SecurityTestConfiguration {

  static {
    {
      SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
    }
  }

  // needed because the auto config is excluded
  @MockBean
  private JwtDecoder jwtDecoder;

  // needed because the auto config is excluded
  @MockBean
  private OAuth2AuthorizedClientManager oAuth2AuthorizedClientManager;

  /**
   * Sets test security. 1:1 copy of {@link TaskServiceSecurityConfiguration}, but without OAuth configured.
   *
   * @param http http security fluent builder.
   * @return filter chain
   * @throws Exception on any error.
   */
  @Bean
  public SecurityFilterChain configure(HttpSecurity http) throws Exception {
    // @formatter:off
    return http
        .logout(
            AbstractHttpConfigurer::disable
        )
        .formLogin(
            AbstractHttpConfigurer::disable
        )
        .csrf(
            AbstractHttpConfigurer::disable
        )
        .authorizeHttpRequests(requests -> requests
            .requestMatchers(HttpMethod.OPTIONS).permitAll()
            .requestMatchers("/actuator/**").permitAll()
            .anyRequest().authenticated()
        )
        .build();
    // @formatter:on
  }

}
