package io.muenchendigital.digiwf.task.service.infra.security;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration;
import org.springframework.boot.autoconfigure.security.oauth2.resource.servlet.OAuth2ResourceServerAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.function.Supplier;

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

  @MockBean
  private JwtDecoder jwtDecoder;

  @MockBean
  private OAuth2AuthorizedClientManager oAuth2AuthorizedClientManager;

  @Bean
  @Primary
  public Supplier<OAuth2AccessToken> mockServiceAccessTokenSupplier() {
    return new SingleTokenTestSupplier(
        ControllerAuthorizationHelper.createServiceAccessToken()
    );
  }


  @Bean
  public SecurityFilterChain configure(HttpSecurity http) throws Exception {

    SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);

    // @formatter:off
    return http
        .logout().disable()
        .formLogin().disable()
        .csrf().disable()
        // authorization block
        .authorizeRequests()
        .antMatchers(HttpMethod.OPTIONS).permitAll()
        .antMatchers("/actuator/**").permitAll()
        .anyRequest().authenticated()
        .and()
        // end of authorization block
        .build()
        ;
    // @formatter:on
  }

}
