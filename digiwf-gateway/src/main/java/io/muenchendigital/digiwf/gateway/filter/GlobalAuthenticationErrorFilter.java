/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik
 * der Landeshauptstadt München, 2022
 */
package io.muenchendigital.digiwf.gateway.filter;

import io.muenchendigital.digiwf.gateway.util.GatewayUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


/**
 * This {@link GlobalFilter} replaces the body by a generic authentication error body,
 * when a server responses with a {@link HttpStatus#UNAUTHORIZED}.
 * <p>
 * The header {@link HttpHeaders#WWW_AUTHENTICATE} containing the access token is removed
 * by the property 'RemoveResponseHeader' in the corresponding route within 'application.yml'.
 */
@Component
@Slf4j
public class GlobalAuthenticationErrorFilter implements GlobalFilter, Ordered {

    private static final String GENERIC_AUTHENTICATION_ERROR = "{ \"status\":401, \"error\":\"Authentication Error\" }";

    @Override
    public int getOrder() {
        return GatewayUtils.ORDER_GLOBAL_FILTER;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.debug("Check for authentication errors");
        return GatewayUtils.responseBodyManipulatorForServerWebExchange(
                exchange,
                chain,
                HttpStatus.UNAUTHORIZED,
                GENERIC_AUTHENTICATION_ERROR
        );
    }

}
