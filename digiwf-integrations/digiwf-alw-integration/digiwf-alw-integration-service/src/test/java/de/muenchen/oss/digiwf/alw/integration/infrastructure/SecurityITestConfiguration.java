package de.muenchen.oss.digiwf.alw.integration.infrastructure;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration;
import org.springframework.boot.autoconfigure.security.oauth2.resource.servlet.OAuth2ResourceServerAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

@Profile("itest")
@Configuration
@EnableWebSecurity
@EnableAutoConfiguration(exclude = {
    OAuth2ResourceServerAutoConfiguration.class,
    OAuth2ClientAutoConfiguration.class,
    SecurityAutoConfiguration.class
})
@Order(1)
public class SecurityITestConfiguration {

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
   * Sets test security.
   *
   * @param http http security fluent builder.
   * @return filter chain
   * @throws Exception on any error.
   */
  @Bean
  public SecurityFilterChain configure(HttpSecurity http) throws Exception {
    // @formatter:off
    return http
        .csrf(AbstractHttpConfigurer::disable)
        .logout(AbstractHttpConfigurer::disable)
        .formLogin(FormLoginConfigurer::disable)
        .authorizeHttpRequests(authorizedRequests -> {
          authorizedRequests.dispatcherTypeMatchers(HttpMethod.OPTIONS).permitAll();
          authorizedRequests.dispatcherTypeMatchers(HttpMethod.valueOf("/actuator/**")).permitAll();
          authorizedRequests.anyRequest().authenticated();
        })
        .build();
    // @formatter:on
  }

}
