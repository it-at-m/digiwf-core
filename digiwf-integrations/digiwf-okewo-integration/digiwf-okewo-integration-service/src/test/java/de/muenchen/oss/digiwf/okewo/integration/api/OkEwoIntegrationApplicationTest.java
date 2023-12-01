package de.muenchen.oss.digiwf.okewo.integration.api;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(
        classes = OkEwoIntegrationApplication.class
)
@ActiveProfiles({"local", "itest"})
class OkEwoIntegrationApplicationTest {

    @Test
    public void shouldStart() {
        // test fails if application context can not start
    }
}
