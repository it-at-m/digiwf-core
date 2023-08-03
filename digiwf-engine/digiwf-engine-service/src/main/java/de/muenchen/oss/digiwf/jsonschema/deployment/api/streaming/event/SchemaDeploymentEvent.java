package de.muenchen.oss.digiwf.jsonschema.deployment.api.streaming.event;

import lombok.*;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SchemaDeploymentEvent {
    private String deploymentId;
    private String versionId;
    private String target;
    private String file;
    private String artifactType;
}
