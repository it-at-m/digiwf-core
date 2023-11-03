/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik
 * der Landeshauptstadt München, 2022
 */
package de.muenchen.oss.digiwf.gateway.configuration;

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
import org.springframework.security.web.server.authentication.logout.HttpStatusReturningServerLogoutSuccessHandler;
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
                .logout(logoutSpec -> {
                    //.logoutSuccessHandler(GatewayUtils.createLogoutSuccessHandler(LOGOUT_SUCCESS_URL))
                    logoutSpec.logoutSuccessHandler(new HttpStatusReturningServerLogoutSuccessHandler())
                            .logoutUrl(LOGOUT_URL)
                            .requiresLogout(ServerWebExchangeMatchers.pathMatchers(HttpMethod.POST, LOGOUT_URL));
                })

                    .authorizeExchange(authorizeExchangeSpec -> {
                        // permitAll
                        authorizeExchangeSpec.pathMatchers(HttpMethod.OPTIONS, "/api/**").permitAll()
                                .pathMatchers(LOGOUT_SUCCESS_URL).permitAll()
                                .pathMatchers("/api/*/info",
                                        "/actuator/health",
                                        "/actuator/info",
                                        "/actuator/metrics").permitAll()
                                // only authenticated
                                .anyExchange().authenticated();
                    })
                .cors(corsSpec -> {})
                .csrf(csrfSpec -> {
                    /*
                     * The necessary subscription for csrf token attachment to {@link ServerHttpResponse}
                     * is done in class {@link CsrfTokenAppendingHelperFilter}.
                     */
                    csrfSpec.csrfTokenRepository(CookieServerCsrfTokenRepository.withHttpOnlyFalse());
                })
                .oauth2Login(oAuth2LoginSpec -> {
                    oAuth2LoginSpec.authenticationSuccessHandler(new RedirectServerAuthenticationSuccessHandler() {
                        @Override
                        public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
                            webFilterExchange.getExchange().getSession().subscribe(
                                    webSession -> webSession.setMaxIdleTime(Duration.ofSeconds(springSessionTimeoutSeconds))
                            );
                            return super.onAuthenticationSuccess(webFilterExchange, authentication);
                        }
                    });
                });

        return http.build();
    }

}
