package de.muenchen.oss.digiwf.gateway.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.server.csrf.CsrfWebFilter;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Configuration
@Profile("!no-security")
@RequiredArgsConstructor
public class CsrfProtectionMatcher implements ServerWebExchangeMatcher {

    /**
     * Copied from {@link CsrfWebFilter}.DefaultRequireCsrfProtectionMatcher
     */
    private static final Set<HttpMethod> ALLOWED_METHODS = new HashSet<>(
            Arrays.asList(HttpMethod.GET, HttpMethod.HEAD, HttpMethod.TRACE, HttpMethod.OPTIONS));

    private final SecurityProperties securityProperties;

    @Override
    public Mono<MatchResult> matches(ServerWebExchange exchange) {
        return Mono.just(exchange.getRequest())
                .flatMap((r) -> Mono.justOrEmpty(new MethodAndPath(r.getMethod(), r.getPath().toString())))
                .filter((mp) -> ALLOWED_METHODS.contains(mp.method) || isWhitelisted(mp.path))
                .flatMap((m) -> MatchResult.notMatch())
                .switchIfEmpty(MatchResult.match());
    }

    private boolean isWhitelisted(String path) {
        for (String whitelisted : securityProperties.getCsrfWhitelisted()) {
            if (new AntPathMatcher().match(whitelisted, path)) {
                return true;
            }
        }
        return false;
    }


    private record MethodAndPath(HttpMethod method, String path) {
    }
}