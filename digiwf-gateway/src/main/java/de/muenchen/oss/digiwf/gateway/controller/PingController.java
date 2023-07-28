/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik
 * der Landeshauptstadt München, 2022
 */
package de.muenchen.oss.digiwf.gateway.controller;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.linkTo;
import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.methodOn;


/**
 * Endpoint for pinging with an authorized request
 * to check for available or expired security sessions.
 */
@RestController
@Slf4j
public class PingController {

    private static final String PING_PATH = "/api";

    /**
     * The hateoas response for the ping controller.
     */
    @EqualsAndHashCode(callSuper = true)
    @ToString(callSuper = true)
    private static class HateoasResponse extends RepresentationModel {

        public HateoasResponse(Link initialLink) {
            super(initialLink);
        }

        public HateoasResponse(List<Link> initialLinks) {
            super(initialLinks);
        }

    }

    /**
     * Endpoint returns {@link HttpStatus#OK} with self
     * link as a hateoas response.
     *
     * @return a response with {@link HttpStatus#OK}.
     */
    @GetMapping(value = PING_PATH)
    public Mono<HateoasResponse> ping() {
        log.debug("GET request on endpoint \"{}\".", PING_PATH);
        return linkTo(methodOn(PingController.class).ping()).withSelfRel()
                .toMono()
                .map(HateoasResponse::new);
    }

}
