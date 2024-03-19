package de.muenchen.oss.digiwf.deployment.api.transport;

import lombok.AllArgsConstructor;
import lombok.Data;

@Deprecated
@AllArgsConstructor
@Data
public class DeploymentStatusDto {
    private boolean success;
    private String message;
}
