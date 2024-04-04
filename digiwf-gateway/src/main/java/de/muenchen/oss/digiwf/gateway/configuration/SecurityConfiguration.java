/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik
 * der Landeshauptstadt München, 2022
 */
package de.muenchen.oss.digiwf.gateway.configuration;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationSuccessHandler;
import org.springframework.security.web.server.authentication.logout.HttpStatusReturningServerLogoutSuccessHandler;
import org.springframework.security.web.server.csrf.CookieServerCsrfTokenRepository;
import org.springframework.security.web.server.util.matcher.PathPatternParserServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


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
    @Order(0)
    public SecurityWebFilterChain clientAccessFilterChain(ServerHttpSecurity http) {
        http
            .securityMatcher(ServerWebExchangeMatchers.pathMatchers("/clients/**"))
            .authorizeExchange(authorizeExchangeSpec -> {
                authorizeExchangeSpec.pathMatchers(HttpMethod.OPTIONS, "/clients/**").permitAll()
                    .anyExchange().authenticated();
            })
            .cors(corsSpec -> {
            })
            .oauth2ResourceServer(oauth2 ->
                oauth2.jwt(Customizer.withDefaults())
            );
        return http.build();
    }

    @Bean
    @Order(1)
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
            .logout(logoutSpec -> {
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
                    .pathMatchers(HttpMethod.OPTIONS, "/public/**").permitAll()
                    .pathMatchers(HttpMethod.GET, "/public/**").permitAll()
                    // only authenticated
                    .anyExchange().authenticated();
            })
            .cors(corsSpec -> {
            })
            .csrf(csrfSpec -> {
                /*
                 * Custom csrf request handler for spa and BREACH attack protection.
                 * https://docs.spring.io/spring-security/reference/6.1-SNAPSHOT/servlet/exploits/csrf.html#csrf-integration-javascript-spa
                 */
                csrfSpec.csrfTokenRequestHandler(new SpaServerCsrfTokenRequestHandler());
                /*
                 * The necessary subscription for csrf token attachment to {@link ServerHttpResponse}
                 * is done in class {@link CsrfTokenAppendingHelperFilter}.
                 */
                csrfSpec.csrfTokenRepository(CookieServerCsrfTokenRepository.withHttpOnlyFalse());
                csrfSpec.requireCsrfProtectionMatcher(
                    new IgnoringOptimizeRequireCsrfProtectionMatcher()
                );
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


    /**
     * Check if this CSRF hole is ok.
     */
    private static class IgnoringOptimizeRequireCsrfProtectionMatcher implements ServerWebExchangeMatcher {

        private static final Set<HttpMethod> ALLOWED_METHODS = new HashSet<>(
            Arrays.asList(HttpMethod.GET, HttpMethod.HEAD, HttpMethod.TRACE, HttpMethod.OPTIONS));

        // FIXME -> parametrize this matcher via properties
        private static final String OPTIMIZE_PATH_PREFIX = "/optimize/";

        @Override
        public Mono<MatchResult> matches(ServerWebExchange exchange) {
            return Mono.just(exchange.getRequest())
                .flatMap((r) -> Mono.justOrEmpty(new MethodAndPath(r.getMethod(), r.getPath().toString())))
                .filter((mp) -> ALLOWED_METHODS.contains(mp.method) || mp.path.startsWith(OPTIMIZE_PATH_PREFIX))
                .flatMap((m) -> MatchResult.notMatch())
                .switchIfEmpty(MatchResult.match());
        }
    }

    @RequiredArgsConstructor
    @Data
    private static class MethodAndPath {
        private final HttpMethod method;
        private final String path;
    }


}
