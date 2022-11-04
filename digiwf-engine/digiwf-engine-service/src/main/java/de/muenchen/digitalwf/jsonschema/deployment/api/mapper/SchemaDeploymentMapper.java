package de.muenchen.digitalwf.jsonschema.deployment.api.mapper;

import de.muenchen.digitalwf.jsonschema.deployment.api.streaming.event.SchemaDeploymentEvent;
import de.muenchen.digitalwf.jsonschema.deployment.domain.model.SchemaDeploymentModel;
import org.mapstruct.Mapper;

@Mapper
public interface SchemaDeploymentMapper {

    SchemaDeploymentModel mapTo(final SchemaDeploymentEvent event);

}
