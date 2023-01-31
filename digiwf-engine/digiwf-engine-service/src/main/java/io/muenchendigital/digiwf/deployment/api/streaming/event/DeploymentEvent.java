package io.muenchendigital.digiwf.deployment.api.streaming.event;

import lombok.*;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DeploymentEvent {
    private String deploymentId;
    private String versionId;
    private String target;
    private String file;
    private String artifactType;
}
