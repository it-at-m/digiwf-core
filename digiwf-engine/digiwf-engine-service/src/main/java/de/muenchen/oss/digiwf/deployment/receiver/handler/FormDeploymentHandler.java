package de.muenchen.oss.digiwf.deployment.receiver.handler;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.muenchen.oss.digiwf.jsonschema.domain.model.JsonSchema;
import de.muenchen.oss.digiwf.jsonschema.domain.service.JsonSchemaService;
import io.miragon.miranum.deploymentreceiver.application.DeploymentFailedException;
import io.miragon.miranum.deploymentreceiver.domain.Deployment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class FormDeploymentHandler implements DeploymentHandler {

    private final JsonSchemaService jsonSchemaService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean isResponsibleFor(final String artifactType) {
        return artifactType.equalsIgnoreCase("form");
    }

    @Override
    public void deployArtifact(Deployment artifact) {
        try {
            final JsonNode jsonNode = this.objectMapper.readTree(artifact.getFile());
            if (!jsonNode.has("key")) {
                throw new RuntimeException("Form key is missing");
            }
            if (!jsonNode.has("schema")) {
                throw new RuntimeException("Form schema is missing");
            }

            final String schemaRef = jsonNode.get("key").asText();
            final JsonNode schemaNode = jsonNode.get("schema");

            final String schema = this.objectMapper.writeValueAsString(schemaNode);

            final JsonSchema jsonSchema = JsonSchema.builder()
                    .key(schemaRef)
                    .schema(schema)
                    .build();

            this.jsonSchemaService.createJsonSchema(jsonSchema);
        } catch (final RuntimeException exception) {
            throw new DeploymentFailedException("Model validation failed with message: " + exception.getMessage());
        } catch (final IOException e) {
            throw new DeploymentFailedException("Could not parse schema " + artifact.getFilename());
        }
    }

}
