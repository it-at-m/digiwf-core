package de.muenchen.oss.digiwf.deployment.receiver;

import de.muenchen.oss.digiwf.deployment.receiver.handler.DeploymentHandler;
import io.miragon.miranum.deploymentreceiver.application.DeploymentFailedException;
import io.miragon.miranum.deploymentreceiver.application.ports.out.MiranumDeploymentReceiver;
import io.miragon.miranum.deploymentreceiver.domain.Deployment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeploymentReceiver implements MiranumDeploymentReceiver {

    private final List<DeploymentHandler> deploymentHandlers;

    @Override
    public void deploy(Deployment deployment) {
        this.deploymentHandlers.stream()
                .filter(handler -> handler.isResponsibleFor(deployment.getType()))
                .findFirst()
                .ifPresentOrElse(handler -> handler.deployArtifact(deployment), () -> {
                    throw new DeploymentFailedException("No handler found for deployment type " + deployment.getType());
                });
    }
}
