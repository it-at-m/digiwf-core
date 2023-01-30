package io.muenchendigital.digiwf.task.service.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.util.StringUtils;

import java.util.Collection;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final GrantedAuthoritiesConverter grantedAuthoritiesExtractor;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.logout().disable()
            .formLogin().disable()
            .csrf().disable()
            // authorization block
            .authorizeRequests()
            .antMatchers(HttpMethod.OPTIONS).permitAll()
            .antMatchers("/actuator/**").permitAll()
            .anyRequest().authenticated()
            // end of authorization block
            .and()
            .oauth2ResourceServer()
            .jwt()
            .jwtAuthenticationConverter(grantedAuthoritiesExtractor)
            .and()
            .and();
    }
}
