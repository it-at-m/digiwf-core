package io.muenchendigital.digiwf.jsonschema.deployment.api.mapper;

import io.muenchendigital.digiwf.jsonschema.deployment.api.streaming.event.SchemaDeploymentEvent;
import io.muenchendigital.digiwf.jsonschema.deployment.domain.model.SchemaDeploymentModel;
import org.mapstruct.Mapper;

@Mapper
public interface SchemaDeploymentMapper {

    SchemaDeploymentModel mapTo(final SchemaDeploymentEvent event);

}
