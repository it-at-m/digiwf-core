package de.muenchen.oss.digiwf.alw.integration.domain.model;

import de.muenchen.oss.digiwf.alw.integration.domain.model.validation.AzrNumber;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * Request for responsibility.
 */
@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class ResponsibilityRequest {
  /**
   * The AZR number.
   */
  @AzrNumber // custom validation
  private String azrNummer;
}
