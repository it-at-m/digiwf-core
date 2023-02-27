package io.muenchendigital.digiwf.deployment.api.resource;

import io.muenchendigital.digiwf.deployment.api.enums.DeploymentStatus;
import io.muenchendigital.digiwf.deployment.api.mapper.DeploymentMapper;
import io.muenchendigital.digiwf.deployment.api.transport.DeploymentDto;
import io.muenchendigital.digiwf.deployment.api.transport.DeploymentStatusDto;
import io.muenchendigital.digiwf.deployment.domain.model.DeploymentStatusModel;
import io.muenchendigital.digiwf.deployment.domain.service.ModelDeploymentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
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
    public DeploymentStatusDto deployArtifacts(@Valid @RequestBody final DeploymentDto deploymentDto) {
        final DeploymentStatusModel status = this.modelDeploymentService.deploy(this.mapper.mapToDeploymentModel(deploymentDto));
        return new DeploymentStatusDto(status.getStatus().equals(DeploymentStatus.SUCCESSFUL.getValue()), status.getMessage());
    }

}
