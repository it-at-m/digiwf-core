package de.muenchen.oss.digiwf.alw.integration.domain.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class AlwPingConfig {
  private final boolean pingEnabled;
  private final String pingAzrNumber;
}
