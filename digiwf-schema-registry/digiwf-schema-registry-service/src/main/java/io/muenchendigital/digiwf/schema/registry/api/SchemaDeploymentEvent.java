package io.muenchendigital.digiwf.schema.registry.api;

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
