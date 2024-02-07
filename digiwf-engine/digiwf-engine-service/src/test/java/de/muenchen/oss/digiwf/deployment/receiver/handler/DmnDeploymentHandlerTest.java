package de.muenchen.oss.digiwf.deployment.receiver.handler;


import io.miragon.miranum.deploymentreceiver.application.DeploymentFailedException;
import io.miragon.miranum.deploymentreceiver.domain.Deployment;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.repository.DeploymentBuilder;
import org.camunda.bpm.model.dmn.DmnModelInstance;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@Component
@RequiredArgsConstructor
class DmnDeploymentHandlerTest {

    private final RepositoryService repositoryService = mock(RepositoryService.class);

    private final DmnDeploymentHandler handler = new DmnDeploymentHandler(repositoryService);

    private final String dmn = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<definitions xmlns=\"https://www.omg.org/spec/DMN/20191111/MODEL/\" xmlns:dmndi=\"https://www.omg.org/spec/DMN/20191111/DMNDI/\" xmlns:dc=\"http://www.omg.org/spec/DMN/20180521/DC/\" id=\"Definitions_1ctj8mz\" name=\"DRD\" namespace=\"http://camunda.org/schema/1.0/dmn\" xmlns:modeler=\"http://camunda.org/schema/modeler/1.0\" exporter=\"Camunda Modeler\" exporterVersion=\"5.9.0\" modeler:executionPlatform=\"Camunda Platform\" modeler:executionPlatformVersion=\"7.18.0\">\n" +
            "  <decision id=\"Decision_01tzw4j\" name=\"Decision 1\">\n" +
            "    <decisionTable id=\"DecisionTable_0xmtvco\">\n" +
            "      <input id=\"Input_1\">\n" +
            "        <inputExpression id=\"InputExpression_1\" typeRef=\"string\">\n" +
            "          <text></text>\n" +
            "        </inputExpression>\n" +
            "      </input>\n" +
            "      <output id=\"Output_1\" typeRef=\"string\" />\n" +
            "    </decisionTable>\n" +
            "  </decision>\n" +
            "  <dmndi:DMNDI>\n" +
            "    <dmndi:DMNDiagram>\n" +
            "      <dmndi:DMNShape dmnElementRef=\"Decision_01tzw4j\">\n" +
            "        <dc:Bounds height=\"80\" width=\"180\" x=\"160\" y=\"100\" />\n" +
            "      </dmndi:DMNShape>\n" +
            "    </dmndi:DMNDiagram>\n" +
            "  </dmndi:DMNDI>\n" +
            "</definitions>\n";

    @Test
    void test_DeployIsResponsibleForDmn() {
        assertThat(handler.isResponsibleFor("dmn")).isTrue();
        assertThat(handler.isResponsibleFor("somethingElse")).isFalse();
    }

    @Test
    void test_DeployArtifact() {
        final DeploymentBuilder deploymentBuilder = mock(DeploymentBuilder.class);
        when(this.repositoryService.createDeployment()).thenReturn(deploymentBuilder);
        when(deploymentBuilder.addModelInstance(anyString(), any(DmnModelInstance.class))).thenReturn(deploymentBuilder);
        when(deploymentBuilder.enableDuplicateFiltering(true)).thenReturn(deploymentBuilder);
        when(deploymentBuilder.name(anyString())).thenReturn(deploymentBuilder);
        // it does not matter what deploy() returns, as long as it does not throw an exception
        when(deploymentBuilder.deploy()).thenReturn(null);

        final Deployment deployment = new Deployment(dmn, "dmn", "filename", "namespace", List.of("LATEST"));

        handler.deployArtifact(deployment);

        verify(this.repositoryService, times(1)).createDeployment();
        verify(deploymentBuilder, times(1)).addModelInstance(eq(deployment.getFilename()), any(DmnModelInstance.class));
        verify(deploymentBuilder, times(1)).enableDuplicateFiltering(true);
        verify(deploymentBuilder, times(1)).name("namespace-filename:LATEST");
        verify(deploymentBuilder, times(1)).deploy();
    }

    @Test
    void test_DeployArtifactThrowsDeploymentFailedExceptionIfDmnIsInvalid() {
        final DeploymentBuilder deploymentBuilder = mock(DeploymentBuilder.class);
        when(this.repositoryService.createDeployment()).thenReturn(deploymentBuilder);
        when(deploymentBuilder.addModelInstance(anyString(), any(DmnModelInstance.class))).thenReturn(deploymentBuilder);
        when(deploymentBuilder.enableDuplicateFiltering(true)).thenReturn(deploymentBuilder);
        when(deploymentBuilder.name(anyString())).thenReturn(deploymentBuilder);
        // it does not matter what deploy() returns, as long as it does not throw an exception
        when(deploymentBuilder.deploy()).thenReturn(null);

        final Deployment deployment = new Deployment("invalid-dmn", "dmn", "filename", "namespace", List.of("LATEST"));

        assertThatThrownBy(() -> handler.deployArtifact(deployment))
                .isInstanceOf(DeploymentFailedException.class);
    }

}
