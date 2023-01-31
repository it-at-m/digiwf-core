package io.muenchendigital.digiwf.deployment.domain.service;

import io.muenchendigital.digiwf.deployment.api.enums.DeploymentStatus;
import io.muenchendigital.digiwf.deployment.domain.model.DeploymentModel;
import io.muenchendigital.digiwf.deployment.domain.model.DeploymentStatusModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.repository.Deployment;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.dmn.Dmn;
import org.camunda.bpm.model.dmn.DmnModelInstance;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class ModelDeploymentService {

    private final RepositoryService repositoryService;

    public DeploymentStatusModel deploy(final DeploymentModel deploymentModel) {
        try {
            final Deployment deployment;

            final String deploymentName = deploymentModel.getDeploymentId() + "-" + deploymentModel.getVersionId();
            final String resourceName = deploymentModel.getVersionId() + "." + deploymentModel.getArtifactType().toLowerCase();

            if (deploymentModel.getArtifactType().equalsIgnoreCase("bpmn")) {
                final BpmnModelInstance model = Bpmn.readModelFromStream(new ByteArrayInputStream(deploymentModel.getFile().getBytes()));
                Bpmn.validateModel(model);
                deployment = this.deploy(model, resourceName, deploymentName);
            } else if (deploymentModel.getArtifactType().equalsIgnoreCase("dmn")) {
                final DmnModelInstance model = Dmn.readModelFromStream(new ByteArrayInputStream(deploymentModel.getFile().getBytes()));
                Dmn.validateModel(model);
                deployment = this.deploy(model, resourceName, deploymentName);
            } else {
                throw new RuntimeException(String.format("ArtifactType %s is not support. Provide bpmn or dmn", deploymentModel.getArtifactType()));
            }

            log.info("Deployed {} - {} successfully", deployment.getId(), deployment.getName());
            return new DeploymentStatusModel(DeploymentStatus.SUCCESSFUL.getValue(), deploymentModel.getDeploymentId(), "Deployment was successful!");
        } catch (final RuntimeException exception) {
            log.error(exception.getMessage(), exception);
            return new DeploymentStatusModel(DeploymentStatus.FAILURE.getValue(), deploymentModel.getDeploymentId(), exception.getMessage());
        }
    }

    private Deployment deploy(final BpmnModelInstance modelInstance, final String resourceName, final String deploymentName) {
        return this.repositoryService.createDeployment()
                .addModelInstance(resourceName, modelInstance)
                .enableDuplicateFiltering(true)
                .name(deploymentName)
                .deploy();
    }

    private Deployment deploy(final DmnModelInstance modelInstance, final String resourceName, final String deploymentName) {
        return this.repositoryService.createDeployment()
                .addModelInstance(resourceName, modelInstance)
                .enableDuplicateFiltering(true)
                .name(deploymentName)
                .deploy();
    }
}
