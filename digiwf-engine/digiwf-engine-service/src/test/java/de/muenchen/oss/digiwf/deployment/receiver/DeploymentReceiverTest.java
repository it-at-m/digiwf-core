package de.muenchen.oss.digiwf.deployment.receiver;

import de.muenchen.oss.digiwf.deployment.receiver.handler.DeploymentHandler;
import io.miragon.miranum.deploymentreceiver.application.DeploymentFailedException;
import io.miragon.miranum.deploymentreceiver.domain.Deployment;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class DeploymentReceiverTest {

    private final DeploymentHandler deploymentHandler = mock(DeploymentHandler.class);
    private final List<DeploymentHandler> deploymentHandlers = List.of(deploymentHandler);
    private final DeploymentReceiver receiver = new DeploymentReceiver(deploymentHandlers);

    @Test
    void test_deploy() {
        when(deploymentHandler.isResponsibleFor("bpmn")).thenReturn(true);

        final Deployment artifact = Deployment.builder()
                .file("file")
                .type("bpmn")
                .filename("filename.bpmn")
                .namespace("my-awesome-project")
                .tags(List.of("LATEST"))
                .build();

        receiver.deploy(artifact);

        verify(deploymentHandler, times(1)).isResponsibleFor("bpmn");
        verify(deploymentHandler, times(1)).deployArtifact(artifact);
    }

    @Test
    void test_DeployRaisesExceptionIfNoHandlerIsFound() {
        when(deploymentHandler.isResponsibleFor("bpmn")).thenReturn(true);
        when(deploymentHandler.isResponsibleFor("somethingElse")).thenReturn(false);

        // Check if the handler is called twice
        assertThatThrownBy(() -> receiver.deploy(Deployment.builder()
                .file("file")
                .type("somethingElse")
                .filename("filename")
                .namespace("my-awesome-project")
                .tags(List.of("LATEST"))
                .build()))
            .isInstanceOf(DeploymentFailedException.class)
            .hasMessageContaining("No handler found for deployment type somethingElse");

        verify(deploymentHandler, times(1)).isResponsibleFor(anyString());
        verify(deploymentHandler, times(0)).deployArtifact(any());
    }

}
