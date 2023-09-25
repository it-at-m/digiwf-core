/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik
 * der Landeshauptstadt München, 2022
 */
package de.muenchen.oss.digiwf.gateway.filter;

import de.muenchen.oss.digiwf.gateway.util.GatewayUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


/**
 * This {@link GlobalFilter} replaces the body by a generic error body, when a server responses
 * with a {@link HttpStatus#INTERNAL_SERVER_ERROR}.
 */
@Component
@Slf4j
public class GlobalBackendErrorFilter implements GlobalFilter, Ordered {

    private static final String GENERIC_ERROR = "{ \"status\":500, \"error\":\"Internal Server Error\" }";

    @Override
    public int getOrder() {
        return GatewayUtils.ORDER_GLOBAL_FILTER;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.debug("Check for backend errors");
        return GatewayUtils.responseBodyManipulatorForServerWebExchange(
                exchange,
                chain,
                HttpStatus.INTERNAL_SERVER_ERROR,
                GENERIC_ERROR
        );
    }

}
