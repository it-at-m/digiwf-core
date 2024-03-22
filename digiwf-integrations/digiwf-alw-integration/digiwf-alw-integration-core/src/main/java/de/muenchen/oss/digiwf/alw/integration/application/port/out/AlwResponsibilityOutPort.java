package de.muenchen.oss.digiwf.alw.integration.application.port.out;

import java.util.Optional;

public interface AlwResponsibilityOutPort {
    Optional<String> getResponsibleSachbearbeiter(String azrNumber);
}
