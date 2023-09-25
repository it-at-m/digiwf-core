package de.muenchen.oss.digiwf.deployment.api.mapper;

import de.muenchen.oss.digiwf.deployment.api.streaming.event.DeploymentEvent;
import de.muenchen.oss.digiwf.deployment.api.transport.DeploymentDto;
import de.muenchen.oss.digiwf.deployment.domain.model.DeploymentModel;
import org.mapstruct.Mapper;

@Mapper
public interface DeploymentMapper {

    DeploymentModel mapToDeploymentModel(final DeploymentEvent event);

    DeploymentModel mapToDeploymentModel(final DeploymentDto dto);

}
