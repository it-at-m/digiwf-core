/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik
 * der Landeshauptstadt München, 2022
 */
package de.muenchen.oss.digiwf.gateway.controller;

import de.muenchen.oss.digiwf.gateway.annotations.ApiGatewayTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;


@ApiGatewayTest
class PingControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @WithMockUser
    void ping() {
        webTestClient.get().uri("/api").exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void pingNotAuthenticated() {
        webTestClient.get().uri("/api").exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.FOUND);
    }

}
