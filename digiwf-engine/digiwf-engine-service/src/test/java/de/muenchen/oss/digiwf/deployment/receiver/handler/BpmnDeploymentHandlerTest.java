package de.muenchen.oss.digiwf.deployment.receiver.handler;


import io.miragon.miranum.deploymentreceiver.application.DeploymentFailedException;
import io.miragon.miranum.deploymentreceiver.domain.Deployment;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.repository.DeploymentBuilder;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@Component
@RequiredArgsConstructor
class BpmnDeploymentHandlerTest {

    private final RepositoryService repositoryService = mock(RepositoryService.class);

    private final BpmnDeploymentHandler handler = new BpmnDeploymentHandler(repositoryService);


    private final String bpmn = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<bpmn:definitions xmlns:bpmn=\"http://www.omg.org/spec/BPMN/20100524/MODEL\" xmlns:bpmndi=\"http://www.omg.org/spec/BPMN/20100524/DI\" xmlns:dc=\"http://www.omg.org/spec/DD/20100524/DC\" id=\"Definitions_1hwxxsy\" targetNamespace=\"http://bpmn.io/schema/bpmn\" xmlns:modeler=\"http://camunda.org/schema/modeler/1.0\" exporter=\"Camunda Modeler\" exporterVersion=\"5.9.0\" modeler:executionPlatform=\"Camunda Platform\" modeler:executionPlatformVersion=\"7.18.0\">\n" +
            "  <bpmn:process id=\"Process_1nc7sed\" isExecutable=\"true\">\n" +
            "    <bpmn:startEvent id=\"StartEvent_1\" />\n" +
            "  </bpmn:process>\n" +
            "  <bpmndi:BPMNDiagram id=\"BPMNDiagram_1\">\n" +
            "    <bpmndi:BPMNPlane id=\"BPMNPlane_1\" bpmnElement=\"Process_1nc7sed\">\n" +
            "      <bpmndi:BPMNShape id=\"_BPMNShape_StartEvent_2\" bpmnElement=\"StartEvent_1\">\n" +
            "        <dc:Bounds x=\"179\" y=\"159\" width=\"36\" height=\"36\" />\n" +
            "      </bpmndi:BPMNShape>\n" +
            "    </bpmndi:BPMNPlane>\n" +
            "  </bpmndi:BPMNDiagram>\n" +
            "</bpmn:definitions>\n";

    @Test
    void test_DeployIsResponsibleForBpmn() {
        assertThat(handler.isResponsibleFor("bpmn")).isTrue();
        assertThat(handler.isResponsibleFor("somethingElse")).isFalse();
    }

    @Test
    void test_DeployArtifact() {
        final DeploymentBuilder deploymentBuilder = mock(DeploymentBuilder.class);
        when(this.repositoryService.createDeployment()).thenReturn(deploymentBuilder);
        when(deploymentBuilder.addModelInstance(anyString(), any(BpmnModelInstance.class))).thenReturn(deploymentBuilder);
        when(deploymentBuilder.enableDuplicateFiltering(true)).thenReturn(deploymentBuilder);
        when(deploymentBuilder.name(anyString())).thenReturn(deploymentBuilder);
        // it does not matter what deploy() returns, as long as it does not throw an exception
        when(deploymentBuilder.deploy()).thenReturn(null);

        final Deployment deployment = new Deployment(bpmn, "bpmn", "filename", "namespace", List.of("LATEST"));

        handler.deployArtifact(deployment);

        verify(this.repositoryService, times(1)).createDeployment();
        verify(deploymentBuilder, times(1)).addModelInstance(eq(deployment.getFilename()), any(BpmnModelInstance.class));
        verify(deploymentBuilder, times(1)).enableDuplicateFiltering(true);
        verify(deploymentBuilder, times(1)).name("namespace-filename:LATEST");
        verify(deploymentBuilder, times(1)).deploy();
    }

    @Test
    void test_DeployArtifactThrowsDeploymentFailedExceptionIfBpmnIsInvalid() {
        final DeploymentBuilder deploymentBuilder = mock(DeploymentBuilder.class);
        when(this.repositoryService.createDeployment()).thenReturn(deploymentBuilder);
        when(deploymentBuilder.addModelInstance(anyString(), any(BpmnModelInstance.class))).thenReturn(deploymentBuilder);
        when(deploymentBuilder.enableDuplicateFiltering(true)).thenReturn(deploymentBuilder);
        when(deploymentBuilder.name(anyString())).thenReturn(deploymentBuilder);
        // it does not matter what deploy() returns, as long as it does not throw an exception
        when(deploymentBuilder.deploy()).thenReturn(null);

        final Deployment deployment = new Deployment("invalid-bpmn", "bpmn", "filename", "namespace", List.of("LATEST"));

        assertThatThrownBy(() -> handler.deployArtifact(deployment))
                .isInstanceOf(DeploymentFailedException.class);
    }

}
