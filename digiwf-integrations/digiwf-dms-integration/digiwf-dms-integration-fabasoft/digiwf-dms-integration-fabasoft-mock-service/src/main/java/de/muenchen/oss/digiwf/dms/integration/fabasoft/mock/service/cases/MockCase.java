package de.muenchen.oss.digiwf.dms.integration.fabasoft.mock.service.cases;

import com.github.tomakehurst.wiremock.WireMockServer;

public interface MockCase {
    void initCase(WireMockServer server);
}
