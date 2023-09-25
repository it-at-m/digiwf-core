package de.muenchen.oss.digiwf.alw.integration.application.port.out;


import de.muenchen.oss.digiwf.alw.integration.domain.model.validation.AzrNumber;

import java.util.Optional;

public interface AlwResponsibilityOutPort {
  Optional<String> getResponsibleSachbearbeiter(@AzrNumber String azrNumber);
}
