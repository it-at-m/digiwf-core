package de.muenchen.oss.digiwf.jsonschema.deployment.api.mapper;

import de.muenchen.oss.digiwf.jsonschema.deployment.api.streaming.event.SchemaDeploymentEvent;
import de.muenchen.oss.digiwf.jsonschema.deployment.domain.model.SchemaDeploymentModel;
import org.mapstruct.Mapper;

@Deprecated
@Mapper
public interface SchemaDeploymentMapper {

    SchemaDeploymentModel mapTo(final SchemaDeploymentEvent event);

}
