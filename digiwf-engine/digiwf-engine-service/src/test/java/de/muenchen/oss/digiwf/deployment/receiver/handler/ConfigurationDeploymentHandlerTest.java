package de.muenchen.oss.digiwf.deployment.receiver.handler;


import de.muenchen.oss.digiwf.process.config.domain.service.ProcessConfigService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Component;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

@Component
@RequiredArgsConstructor
class ConfigurationDeploymentHandlerTest {

    private final ProcessConfigService processConfigService = mock(ProcessConfigService.class);

    private final ConfigurationDeploymentHandler handler = new ConfigurationDeploymentHandler(processConfigService);

    @Test
    void test_DeployIsResponsibleForBpmn() {
        assertThat(handler.isResponsibleFor("config")).isTrue();
        assertThat(handler.isResponsibleFor("somethingElse")).isFalse();
    }

}
