/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik
 * der Landeshauptstadt München, 2022
 */
package io.muenchendigital.digiwf.gateway.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.logout.RedirectServerLogoutSuccessHandler;
import org.springframework.security.web.server.authentication.logout.ServerLogoutSuccessHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.nio.charset.StandardCharsets;


/**
 * Utility methods and constants which are used in multiple
 * locations throughout the service.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class GatewayUtils {

    public static final int ORDER_GLOBAL_FILTER = -3;

    public static final String EMPTY_JSON_OBJECT = "{}";

    /**
     * The method is used in {@link GlobalFilter}s to add the response body given in the
     * parameter when the {@link HttpStatus} given in the parameter is met.
     *
     * If the {@link HttpStatus} given in the parameter is the same as in {@link ServerHttpResponse}
     * the body within the parameter will be added otherwise the body received from upstream stays the same.
     *
     * @param exchange Contains the response.
     * @param chain The filter chain for delegation to the next filter.
     * @param httpStatus Status of the http {@link ServerHttpResponse}.
     * @param newResponseBody The UTF8 conform message to add into the body of the {@link ServerHttpResponse}.
     * @return An empty mono. The results are processed within the {@link GatewayFilterChain}.
     */
    public static Mono<Void> responseBodyManipulatorForServerWebExchange(final ServerWebExchange exchange,
                                                                         final GatewayFilterChain chain,
                                                                         final HttpStatus httpStatus,
                                                                         final String newResponseBody) {
        final ServerHttpResponse response = exchange.getResponse();

        final ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(response) {

            /**
             * This overridden method adds the response body given in the parameter of
             * the surrounding method when the http status given in the parameter of
             * the surrounding method is met otherwise the already appended body will be used.
             *
             * @param body The body received by the upstream response.
             * @return Either the body received by the upstream response or
             * the body given by the parameter.
             */
            @Override
            public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                final var responseHttpStatus = getDelegate().getStatusCode();
                if (body instanceof Flux && responseHttpStatus.equals(httpStatus)) {
                    final var dataBufferFactory = response.bufferFactory();
                    final DataBuffer newDataBuffer = dataBufferFactory.wrap(
                            ObjectUtils.defaultIfNull(newResponseBody, EMPTY_JSON_OBJECT)
                                    .getBytes(StandardCharsets.UTF_8)
                    );

                    log.debug("Response from upstream {} get new response body: {}", httpStatus, newResponseBody);
                    getDelegate().getHeaders().setContentLength(newDataBuffer.readableByteCount());
                    getDelegate().getHeaders().setContentType(MediaType.APPLICATION_JSON);
                    Flux<? extends DataBuffer> flux = (Flux<? extends DataBuffer>) body;

                    return super.writeWith(flux.buffer().map(
                            // replace old body represented by dataBuffer by the new one
                            dataBuffer -> newDataBuffer
                    ));
                }
                return super.writeWith(body);
            }

        };

        final ServerWebExchange swe = exchange.mutate().response(decoratedResponse).build();
        return chain.filter(swe);
    }

    /**
     * This method creates the {@link ServerLogoutSuccessHandler} for handling a successful logout.
     * The usage is necessary in {@link SecurityWebFilterChain}.
     *
     * @param uri to forward after an successful logout.
     * @return The handler for forwarding after an succesful logout.
     */
    public static ServerLogoutSuccessHandler createLogoutSuccessHandler(final String uri) {
        final var successHandler = new RedirectServerLogoutSuccessHandler();
        successHandler.setLogoutSuccessUrl(URI.create(uri));
        return successHandler;
    }

}
