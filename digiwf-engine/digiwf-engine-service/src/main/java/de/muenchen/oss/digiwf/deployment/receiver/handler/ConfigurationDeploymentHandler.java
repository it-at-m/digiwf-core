package de.muenchen.oss.digiwf.deployment.receiver.handler;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.muenchen.oss.digiwf.process.config.domain.model.ProcessConfig;
import de.muenchen.oss.digiwf.process.config.domain.service.ProcessConfigService;
import io.miragon.miranum.deploymentreceiver.application.DeploymentFailedException;
import io.miragon.miranum.deploymentreceiver.domain.Deployment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConfigurationDeploymentHandler implements DeploymentHandler {

    private final ProcessConfigService processConfigService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean isResponsibleFor(final String artifactType) {
        return artifactType.equalsIgnoreCase("config");
    }

    @Override
    public void deployArtifact(Deployment artifact) throws DeploymentFailedException {
        try {
            final ProcessConfig processConfig = this.objectMapper.readValue(artifact.getFile(), ProcessConfig.class);
            this.processConfigService.saveProcessConfig(processConfig);
        } catch (JsonMappingException e) {
            throw new DeploymentFailedException("Invalid config: " + e.getMessage());
        } catch (JsonProcessingException e) {
            throw new DeploymentFailedException("Invalid config: " + e.getMessage());
        }
    }

}
