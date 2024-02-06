package de.muenchen.oss.digiwf.deployment.receiver.handler;


import io.miragon.miranum.deploymentreceiver.application.DeploymentFailedException;
import io.miragon.miranum.deploymentreceiver.domain.Deployment;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.model.dmn.Dmn;
import org.camunda.bpm.model.dmn.DmnModelInstance;
import org.camunda.bpm.model.xml.ModelValidationException;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;

@Component
@RequiredArgsConstructor
public class DmnDeploymentHandler implements DeploymentHandler {

    private final RepositoryService repositoryService;

    @Override
    public boolean isResponsibleFor(final String artifactType) {
        return artifactType.equalsIgnoreCase("dmn");
    }

    @Override
    public void deployArtifact(Deployment artifact) throws DeploymentFailedException {
        // DeploymentName is in the format: <namespace>-<filename>:<tag>
        final String deploymentName = String.format("%s-%s:%s",
                artifact.getNamespace(), artifact.getFilename(), artifact.getTags().stream().findFirst().orElse("LATEST"));

        try {
            final DmnModelInstance model = Dmn.readModelFromStream(new ByteArrayInputStream(artifact.getFile().getBytes()));
            Dmn.validateModel(model);
            this.repositoryService.createDeployment()
                    .addModelInstance(artifact.getFilename(), model)
                    .enableDuplicateFiltering(true)
                    .name(deploymentName)
                    .deploy();
        } catch (final ModelValidationException exception) {
            throw new DeploymentFailedException("Model validation failed with message: " + exception.getMessage());
        } catch (final RuntimeException exception) {
            throw new DeploymentFailedException("Deployment of artifact " + deploymentName + " failed with " + exception.getMessage());
        }
    }

}
