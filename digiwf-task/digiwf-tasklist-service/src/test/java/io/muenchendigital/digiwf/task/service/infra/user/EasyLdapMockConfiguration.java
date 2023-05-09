package io.muenchendigital.digiwf.task.service.infra.user;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;

import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;


@Configuration
@Profile("mocked-ldap-service")
public class EasyLdapMockConfiguration {
    @Value("/v1/ldap/user")
    private String requestPath;
    @Value("${easyldap.client.port}")
    private int port;
    @Bean(initMethod = "start", destroyMethod = "stop")
    public WireMockServer mockEasyLdapServer() throws IOException {
        val server = new WireMockServer(port);

        val responseBody = getString("files/easy-ldap-response.json");
        server.stubFor(WireMock.get(requestPath + "/1234")
                .willReturn(aResponse()
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(responseBody)));

        // only for logging issue, can be removed later
        server.addMockServiceRequestListener((request, response) -> {
            System.out.println(request);
            System.out.println(response);
        });

        server.stubFor(WireMock.get(requestPath + "/0")
                .willReturn(aResponse()
                        .withStatus(404)));

        return server;
    }

    private String getString (String path) throws IOException {
        return new String(this.getClass().getClassLoader().getResourceAsStream(path).readAllBytes());
    }
}



