package io.muenchendigital.digiwf.engine.deployment.api.mapper;

import io.muenchendigital.digiwf.engine.deployment.api.streaming.event.DeploymentEvent;
import io.muenchendigital.digiwf.engine.deployment.api.transport.DeploymentDto;
import io.muenchendigital.digiwf.engine.deployment.domain.model.DeploymentModel;
import org.mapstruct.Mapper;

@Mapper
public interface DeploymentMapper {

    DeploymentModel mapToDeploymentModel(final DeploymentEvent event);

    DeploymentModel mapToDeploymentModel(final DeploymentDto dto);

}
