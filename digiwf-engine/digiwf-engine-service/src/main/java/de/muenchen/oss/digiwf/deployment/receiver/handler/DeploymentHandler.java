package de.muenchen.oss.digiwf.deployment.receiver.handler;

import io.miragon.miranum.deploymentreceiver.application.DeploymentFailedException;
import io.miragon.miranum.deploymentreceiver.domain.Deployment;

/**
 * Handle deployment for specific types
 */
public interface DeploymentHandler {

    boolean isResponsibleFor(String artifactType);

    void deployArtifact(final Deployment artifact) throws DeploymentFailedException;


}
