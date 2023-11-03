/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik
 * der Landeshauptstadt München, 2022
 */
package de.muenchen.oss.digiwf.gateway.controller;

import de.muenchen.oss.digiwf.gateway.annotations.ApiGatewayTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;


@ApiGatewayTest
public class ActuatorInfoEndpointTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void actuatorInfoProvidesAppswitcherUrl() {
        webTestClient.get().uri("/actuator/info").exchange()
                .expectStatus().isOk()
                .expectBody()
                    .jsonPath("$.appswitcher.url").isEqualTo("https://test-url-appswitcher.muenchen.de");
    }

}
