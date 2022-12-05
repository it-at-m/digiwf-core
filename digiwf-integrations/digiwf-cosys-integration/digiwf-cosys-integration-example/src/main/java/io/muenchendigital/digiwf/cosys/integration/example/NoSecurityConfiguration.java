package io.muenchendigital.digiwf.cosys.integration.example;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class NoSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(final HttpSecurity http) throws Exception {
        http
                .headers()
                .frameOptions()
                .disable()
                .and().antMatcher("/**")
                .authorizeRequests()
                .anyRequest()
                .permitAll()
                .and().csrf()
                .disable();
    }

}
