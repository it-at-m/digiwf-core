package de.muenchen.digitalwf.engine.deployment.api.mapper;

import de.muenchen.digitalwf.engine.deployment.api.streaming.event.DeploymentEvent;
import de.muenchen.digitalwf.engine.deployment.api.transport.DeploymentDto;
import de.muenchen.digitalwf.engine.deployment.domain.model.DeploymentModel;
import org.mapstruct.Mapper;

@Mapper
public interface DeploymentMapper {

    DeploymentModel mapToDeploymentModel(final DeploymentEvent event);

    DeploymentModel mapToDeploymentModel(final DeploymentDto dto);

}
