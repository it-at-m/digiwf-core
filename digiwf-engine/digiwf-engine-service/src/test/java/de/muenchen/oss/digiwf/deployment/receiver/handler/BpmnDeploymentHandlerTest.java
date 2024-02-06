package de.muenchen.oss.digiwf.deployment.receiver.handler;


import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.RepositoryService;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Component;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

@Component
@RequiredArgsConstructor
class BpmnDeploymentHandlerTest {

    private final RepositoryService repositoryService = mock(RepositoryService.class);

    private final BpmnDeploymentHandler handler = new BpmnDeploymentHandler(repositoryService);

    @Test
    void test_DeployIsResponsibleForBpmn() {
        assertThat(handler.isResponsibleFor("bpmn")).isTrue();
        assertThat(handler.isResponsibleFor("somethingElse")).isFalse();
    }

}
