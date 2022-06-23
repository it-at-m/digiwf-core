package io.muenchendigital.digiwf.enginestreaming.jsonschema;

import de.muenchen.digitalwf.engine.deployment.api.enums.DeploymentStatus;
import de.muenchen.digitalwf.engine.deployment.domain.model.DeploymentStatusModel;
import de.muenchen.digitalwf.jsonschema.deployment.domain.model.SchemaDeploymentModel;
import de.muenchen.digitalwf.jsonschema.domain.model.JsonSchema;
import de.muenchen.digitalwf.jsonschema.domain.service.JsonSchemaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SchemaDeploymentService {

    private final JsonSchemaService jsonSchemaService;

    public DeploymentStatusModel deploy(final SchemaDeploymentModel deploymentModel) {
        try {
            final JsonSchema jsonSchema = JsonSchema.builder()
                    .key(deploymentModel.getVersionId())
                    .schema(deploymentModel.getFile())
                    .build();

            this.jsonSchemaService.createJsonSchema(jsonSchema);

            log.info("Deployed {} successfully", jsonSchema.getKey());
            return new DeploymentStatusModel(DeploymentStatus.SUCCESSFUL.getValue(), deploymentModel.getDeploymentId(), "Deployment was successful!");
        } catch (final RuntimeException exception) {
            log.error(exception.getMessage(), exception);
            return new DeploymentStatusModel(DeploymentStatus.FAILURE.getValue(), deploymentModel.getDeploymentId(), "Deployment failed with error " + exception.getMessage());
        }
    }

}
