package de.muenchen.oss.digiwf.dms.integration.fabasoft.mock.service.cases;

import com.github.tomakehurst.wiremock.WireMockServer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MockInitializer {

    private final List<MockCase> mockCases;

    @Value("${mock.port:9070}")
    private int port;

    @PostConstruct
    public void init() {
        WireMockServer server = new WireMockServer(port);
        server.start();
        mockCases.forEach(mock -> mock.initCase(server));
    }
}
