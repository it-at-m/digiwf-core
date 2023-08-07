package de.muenchen.oss.digiwf.jsonschema.deployment.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
@AllArgsConstructor
public class SchemaDeploymentModel {
    private String deploymentId;
    private String versionId;
    private String target;
    private String file;
    private String artifactType;
}
