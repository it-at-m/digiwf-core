package io.muenchendigital.digiwf.connector.jsonschema.internal.deployment;

import io.muenchendigital.digiwf.connector.jsonschema.api.DeploymentStatusModel;
import io.muenchendigital.digiwf.connector.jsonschema.api.JsonSchema;
import io.muenchendigital.digiwf.connector.jsonschema.api.JsonSchemaService;
import io.muenchendigital.digiwf.connector.jsonschema.internal.impl.model.JsonSchemaImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SchemaDeploymentService {

    private final JsonSchemaService jsonSchemaService;

    public DeploymentStatusModel deploy(final String deploymentId, final String key, final String schema) {
        try {
            final JsonSchema jsonSchema = JsonSchemaImpl.builder()
                    .key(key)
                    .schema(schema)
                    .build();

            this.jsonSchemaService.createJsonSchema(jsonSchema);

            log.info("Deployed {} successfully", jsonSchema.getKey());
            return new DeploymentStatusModel(DeploymentStatus.SUCCESSFUL.getValue(), deploymentId, "Deployment was successful!");
        } catch (final RuntimeException exception) {
            log.error(exception.getMessage(), exception);
            return new DeploymentStatusModel(DeploymentStatus.FAILURE.getValue(), deploymentId, "Deployment failed with error " + exception.getMessage());
        }
    }

}
