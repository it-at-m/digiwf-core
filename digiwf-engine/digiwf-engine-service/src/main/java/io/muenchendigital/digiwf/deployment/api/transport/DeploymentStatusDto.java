package io.muenchendigital.digiwf.deployment.api.transport;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class DeploymentStatusDto {
    private boolean success;
    private String message;
}
