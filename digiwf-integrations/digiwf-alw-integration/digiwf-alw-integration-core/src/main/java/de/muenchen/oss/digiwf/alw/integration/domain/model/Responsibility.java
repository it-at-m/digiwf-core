package de.muenchen.oss.digiwf.alw.integration.domain.model;

import lombok.*;

import jakarta.validation.constraints.NotNull;

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
  @NotNull
  private final String orgUnit;
}
