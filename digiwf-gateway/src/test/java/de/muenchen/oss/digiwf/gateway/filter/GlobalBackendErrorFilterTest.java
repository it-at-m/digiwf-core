/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik
 * der Landeshauptstadt München, 2022
 */
package de.muenchen.oss.digiwf.gateway.filter;

import com.github.tomakehurst.wiremock.http.HttpHeader;
import com.github.tomakehurst.wiremock.http.HttpHeaders;
import de.muenchen.oss.digiwf.gateway.annotations.ApiGatewayTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.github.tomakehurst.wiremock.client.WireMock.*;


@ApiGatewayTest
@AutoConfigureWireMock
class GlobalBackendErrorFilterTest {

    @Autowired
    private WebTestClient webTestClient;

    @BeforeEach
    void setup() {
        stubFor(get(urlEqualTo("/remote"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .withHeaders(new HttpHeaders(
                                new HttpHeader("Content-Type", "application/json"),
                                new HttpHeader("WWW-Authenticate", "Bearer realm=\"Access to the staging site\", charset=\"UTF-8\""),
                                new HttpHeader("Expires", "Wed, 21 Oct 2099 07:28:06 GMT")
                        ))
                        .withBody("{ \"testkey\" : \"testvalue\" }")));
    }

    @Test
    @WithMockUser
    void backendError() {
        webTestClient.get().uri("/api/frontend-backend-service/remote").exchange()
                .expectStatus().isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR)
                .expectHeader().valueMatches("Content-Type", "application/json")
                .expectHeader().doesNotExist("WWW-Authenticate")
                .expectHeader().valueMatches("Expires", "0")
                .expectBody()
                    .jsonPath("$.status").isEqualTo("500")
                    .jsonPath("$.error").isEqualTo("Internal Server Error");
    }

}
