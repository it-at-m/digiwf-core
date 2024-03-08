package de.muenchen.oss.digiwf.deployment.receiver;

import io.miragon.miranum.deploymentreceiver.application.dto.DeploymentDto;
import io.miragon.miranum.deploymentreceiver.application.ports.in.DeployFile;
import io.miragon.miranum.deploymentreceiver.domain.DeploymentStatus;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


// Note: Deployment Server ist not deployed and this controller is only needed for local development at the moment
@Profile("local")
@RestController
@RequestMapping("/rest/deployment/v2")
@RequiredArgsConstructor
@Tag(name = "DeploymentReceiverController", description = "API to deploy process artifacts (e.g. bpmn and dmn)")
public class DeploymentReceiverController {

    private final DeployFile deployFile;

    @PostMapping
    public DeploymentStatus deploy(@Valid @RequestBody final DeploymentDto deploymentDto) {
        return this.deployFile.deploy(deploymentDto);
    }

}
