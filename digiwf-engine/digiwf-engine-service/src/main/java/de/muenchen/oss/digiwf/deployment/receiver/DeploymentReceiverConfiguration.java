package de.muenchen.oss.digiwf.deployment.receiver;

import de.muenchen.oss.digiwf.deployment.receiver.handler.DeploymentHandler;
import io.miragon.miranum.deploymentreceiver.application.ports.in.DeployFile;
import io.miragon.miranum.deploymentreceiver.application.ports.out.MiranumDeploymentReceiver;
import io.miragon.miranum.deploymentreceiver.application.usecase.DeployFileUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ComponentScan(basePackages = "io.miragon.miranum.deploymentreceiver.adapter.in.rest")
public class DeploymentReceiverConfiguration {

    @Bean
    public MiranumDeploymentReceiver miranumDeployment(final List<DeploymentHandler> deploymentHandlers) {
        return new DeploymentReceiver(deploymentHandlers);
    }

    @Bean
    public DeployFile deployFile(final MiranumDeploymentReceiver miranumDeployment) {
        return new DeployFileUseCase(miranumDeployment);
    }

}
