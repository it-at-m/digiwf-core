package de.muenchen.oss.digiwf.alw.integration.domain.model;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * Describes responsibility.
 */
@Data
@RequiredArgsConstructor
@Builder
public class Responsibility {

    /**
     * Organizational unit responsible.
     */
    private final String orgUnit;
}
