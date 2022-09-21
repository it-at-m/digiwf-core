/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik
 * der Landeshauptstadt München, 2022
 */
package io.muenchendigital.digiwf.gateway.controller;

import io.muenchendigital.digiwf.gateway.ApiGatewayApplication;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import static io.muenchendigital.digiwf.gateway.TestConstants.SPRING_TEST_PROFILE;


@ExtendWith(SpringExtension.class)
@SpringBootTest(
        classes = {ApiGatewayApplication.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ActiveProfiles(SPRING_TEST_PROFILE)
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
