package de.muenchen.oss.digiwf.schema.registry.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DeploymentStatusModel {

    private String status;

    private String deploymentId;

    private String message;
}
