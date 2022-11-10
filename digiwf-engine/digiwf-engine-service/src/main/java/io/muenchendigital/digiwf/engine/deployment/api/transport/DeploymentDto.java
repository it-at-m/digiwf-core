package io.muenchendigital.digiwf.engine.deployment.api.transport;

import lombok.*;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DeploymentDto {
    private String deploymentId;
    private String versionId;
    private String target;
    private String file;
    private String artifactType;
}
