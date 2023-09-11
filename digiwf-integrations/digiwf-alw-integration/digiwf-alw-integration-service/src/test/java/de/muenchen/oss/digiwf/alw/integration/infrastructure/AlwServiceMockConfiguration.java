package de.muenchen.oss.digiwf.alw.integration.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import de.muenchen.oss.digiwf.alw.integration.configuration.AlwPersoneninfoProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.HashMap;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static de.muenchen.oss.digiwf.alw.integration.adapter.out.alw.AlwResponsibilityRestAdapter.FIELD_SACHBEARBEITER;


@Configuration
@Profile("mocked-alw-service")
@RequiredArgsConstructor
@Slf4j
@EnableConfigurationProperties(AlwPersoneninfoProperties.class)
public class AlwServiceMockConfiguration {

    private final AlwPersoneninfoProperties properties;
    private final ObjectMapper objectMapper;

    @PostConstruct
    public void report() {
      log.warn("Started wiremock with " + properties);
    }

    @Bean(initMethod = "start", destroyMethod = "stop")
    public WireMockServer mockAlwServer() throws IOException {

        int port = Integer.parseInt(properties.getBaseurl().substring(properties.getBaseurl().lastIndexOf(":") + 1));

        val server = new WireMockServer(port);

        val result = new HashMap<String, String>();
        result.put(FIELD_SACHBEARBEITER, "SB1");

        server.stubFor(WireMock.get(properties.getRestEndpoint() + "123456789012")
                .willReturn(aResponse()
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(
                            objectMapper.writeValueAsString(result)
                        )));
        server.stubFor(WireMock.get(properties.getRestEndpoint() + "098765432109")
                .willReturn(aResponse().withStatus(404)));

        return server;
    }
}



