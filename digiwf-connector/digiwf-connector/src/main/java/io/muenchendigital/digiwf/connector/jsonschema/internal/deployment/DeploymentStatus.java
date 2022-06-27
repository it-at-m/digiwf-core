package io.muenchendigital.digiwf.connector.jsonschema.internal.deployment;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DeploymentStatus {
    SUCCESSFUL("SUCCESSFUL"),
    FAILURE("FAILURE");

    private final String value;
}
