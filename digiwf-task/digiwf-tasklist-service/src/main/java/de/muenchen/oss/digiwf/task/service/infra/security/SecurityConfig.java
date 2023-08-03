package de.muenchen.oss.digiwf.task.service.infra.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final GrantedAuthoritiesConverter grantedAuthoritiesExtractor;


    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
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
            .oauth2ResourceServer()
                .jwt()
                .jwtAuthenticationConverter(grantedAuthoritiesExtractor)
                .and()
                .and()
                .build()
        ;
        // @formatter:on
    }
}
