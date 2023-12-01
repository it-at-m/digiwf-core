/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik
 * der Landeshauptstadt München, 2022
 */
package de.muenchen.oss.digiwf.gateway.filter;

import de.muenchen.oss.digiwf.gateway.annotations.ApiGatewayTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertTrue;


@ApiGatewayTest
class GlobalRequestParameterPollutionFilterTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @WithMockUser
    void parameterPollutionAttack() {
        final StringBuilder jsonResponseBody = new StringBuilder();
        webTestClient.get().uri("/api/frontend-backend-service/testendpoint?parameter1=testdata_1&parameter2=testdata&parameter1=testdata_2").exchange()
                .expectStatus()
                    .isEqualTo(HttpStatus.BAD_REQUEST)
                .expectBody()
                    .consumeWith(responseBody -> jsonResponseBody.append(new String(responseBody.getResponseBody(), StandardCharsets.UTF_8)));
        assertTrue(jsonResponseBody.toString().contains("\"message\" : \"parameter pollution\""));
    }

}
