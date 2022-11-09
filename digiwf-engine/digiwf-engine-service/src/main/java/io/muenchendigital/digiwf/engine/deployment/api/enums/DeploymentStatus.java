package io.muenchendigital.digiwf.engine.deployment.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DeploymentStatus {
    SUCCESSFUL("SUCCESSFUL"),
    FAILURE("FAILURE");

    private final String value;
}
