package de.muenchen.oss.digiwf.integration.e2e.test;

import com.github.tomakehurst.wiremock.client.WireMock;

import static com.github.tomakehurst.wiremock.client.WireMock.equalToJson;

public class DigiwfWiremockUtility {

    private DigiwfWiremockUtility() {
        // Utility class
    }

    public static void setupGET(final String url, final String expectedResponse) {
        WireMock.stubFor(WireMock
                .get(url)
                .willReturn(WireMock
                        .aResponse()
                        .withBody(expectedResponse)
                        .withHeader("Content-Type", "application/json")
                        .withStatus(200)));
    }

    public static void setupGETWithBasicAuth(final String url, final String username, final String password, final String expectedResponse) {
        WireMock.stubFor(WireMock
                .get(url)
                .withBasicAuth(username, password)
                .willReturn(WireMock
                        .aResponse()
                        .withBody(expectedResponse)
                        .withHeader("Content-Type", "application/json")
                        .withStatus(200)));
    }

    public static void setupPOST(final String url, final String requestBody, final String expectedResponse) {
        WireMock.stubFor(WireMock
                .post(url)
                .withRequestBody(equalToJson(requestBody))
                .willReturn(WireMock
                        .aResponse()
                        .withBody(expectedResponse)
                        .withHeader("Content-Type", "application/json")
                        .withStatus(200)));
    }

    public static void setupPOSTWithBasicAuth(final String url, final String requestBody, final String username, final String password, final String expectedResponse) {
        WireMock.stubFor(WireMock
                .post(url)
                .withRequestBody(equalToJson(requestBody))
                .withBasicAuth(username, password)
                .willReturn(WireMock
                        .aResponse()
                        .withBody(expectedResponse)
                        .withHeader("Content-Type", "application/json")
                        .withStatus(200)));
    }

}
