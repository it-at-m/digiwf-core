/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik
 * der Landeshauptstadt München, 2022
 */
package io.muenchendigital.digiwf.gateway.configuration;

import io.muenchendigital.digiwf.gateway.util.GatewayUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationSuccessHandler;
import org.springframework.security.web.server.csrf.CookieServerCsrfTokenRepository;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;
import reactor.core.publisher.Mono;

import java.time.Duration;


@Configuration
@Profile("!no-security")
public class SecurityConfiguration {

    private static final String LOGOUT_URL = "/logout";

    private static final String LOGOUT_SUCCESS_URL = "/loggedout.html";

    /**
     * Same lifetime as SSO Session (e.g. 10 hours).
     */
    @Value("${spring.session.timeout:36000}")
    private long springSessionTimeoutSeconds;

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
                .logout()
                    .logoutSuccessHandler(GatewayUtils.createLogoutSuccessHandler(LOGOUT_SUCCESS_URL))
                    .logoutUrl(LOGOUT_URL)
                    .requiresLogout(ServerWebExchangeMatchers.pathMatchers(HttpMethod.POST, LOGOUT_URL))
                .and()
                    .authorizeExchange()
                        // permitAll
                        .pathMatchers(HttpMethod.OPTIONS, "/api/**").permitAll()
                        .pathMatchers(LOGOUT_SUCCESS_URL).permitAll()
                        .pathMatchers("/api/*/info",
                                "/actuator/health",
                                "/actuator/info",
                                "/actuator/metrics").permitAll()
                        // only authenticated
                        .anyExchange().authenticated()
                .and()
                    /*
                     * The necessary subscription for csrf token attachment to {@link ServerHttpResponse}
                     * is done in class {@link CsrfTokenAppendingHelperFilter}.
                     */
                    .csrf().csrfTokenRepository(CookieServerCsrfTokenRepository.withHttpOnlyFalse())
                .and()
                    .cors()
                .and()
                    .oauth2Login()
                    /*
                     * Set security session timeout.
                     */
                    .authenticationSuccessHandler(new RedirectServerAuthenticationSuccessHandler() {
                        @Override
                        public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
                            webFilterExchange.getExchange().getSession().subscribe(
                                    webSession -> webSession.setMaxIdleTime(Duration.ofSeconds(springSessionTimeoutSeconds))
                            );
                            return super.onAuthenticationSuccess(webFilterExchange, authentication);
                        }
                    });
        return http.build();
    }

}
