package de.muenchen.oss.digiwf.deployment.api.resource;

import de.muenchen.oss.digiwf.deployment.api.enums.DeploymentStatus;
import de.muenchen.oss.digiwf.deployment.api.mapper.DeploymentMapper;
import de.muenchen.oss.digiwf.deployment.api.transport.DeploymentDto;
import de.muenchen.oss.digiwf.deployment.api.transport.DeploymentStatusDto;
import de.muenchen.oss.digiwf.deployment.domain.model.DeploymentStatusModel;
import de.muenchen.oss.digiwf.deployment.domain.service.ModelDeploymentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/rest/deployment")
@RequiredArgsConstructor
@Tag(name = "DeploymentController", description = "API to deploy bpmn and dmn artifacts")
public class DeploymentController {

    private final ModelDeploymentService modelDeploymentService;
    private final DeploymentMapper mapper;

    @PostMapping
    @PreAuthorize("hasAuthority(T(de.muenchen.oss.digiwf.shared.security.AuthoritiesEnum).BACKEND_DEPLOY_RESOURCE.name())")
    public DeploymentStatusDto deployArtifacts(@Valid @RequestBody final DeploymentDto deploymentDto) {
        final DeploymentStatusModel status = this.modelDeploymentService.deploy(this.mapper.mapToDeploymentModel(deploymentDto));
        return new DeploymentStatusDto(status.getStatus().equals(DeploymentStatus.SUCCESSFUL.getValue()), status.getMessage());
    }

}
