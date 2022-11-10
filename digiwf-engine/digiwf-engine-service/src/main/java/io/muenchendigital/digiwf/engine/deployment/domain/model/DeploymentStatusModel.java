package io.muenchendigital.digiwf.engine.deployment.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DeploymentStatusModel {
    private String status;
    private String deploymentId;
    private String message;
}
