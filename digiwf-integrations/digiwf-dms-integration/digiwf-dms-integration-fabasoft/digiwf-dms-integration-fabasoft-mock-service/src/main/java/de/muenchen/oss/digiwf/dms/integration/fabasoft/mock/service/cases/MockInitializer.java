package de.muenchen.oss.digiwf.dms.integration.fabasoft.mock.service.cases;

import com.github.tomakehurst.wiremock.WireMockServer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MockInitializer {

    private final List<MockCase> mockCases;

    @PostConstruct
    public void init() {
        WireMockServer server = new WireMockServer(8080);
        server.start();
        mockCases.forEach(mock -> mock.initCase(server));
    }
}
