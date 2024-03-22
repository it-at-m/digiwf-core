package de.muenchen.oss.digiwf.dms.integration.adapter.out.auth;

import de.muenchen.oss.digiwf.dms.integration.application.port.out.DmsUserOutPort;
import org.springframework.beans.factory.annotation.Value;

public class MockDmsUserAdapter implements DmsUserOutPort {

    @Value("${digiwf.integration.dms.mockuser}")
    private String dmsUsername;

    @Override
    public String getDmsUser() {
        return dmsUsername;
    }
}
