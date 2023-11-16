package de.muenchen.oss.digiwf.alw.integration.application.port.in;

import de.muenchen.oss.digiwf.alw.integration.domain.exception.AlwException;
import de.muenchen.oss.digiwf.alw.integration.domain.model.Responsibility;
import de.muenchen.oss.digiwf.alw.integration.domain.model.ResponsibilityRequest;
import org.springframework.lang.NonNull;

/**
 * Retrieves the responsibility in Ausländerwesen (ALW).
 */
public interface GetResponsibilityInPort {

  /**
   * Retrieves the responsibility for given parameters.
   * @param request responsibility request.
   * @return optional responsibility.
   */
  @NonNull
  Responsibility getResponsibility(@NonNull ResponsibilityRequest request) throws AlwException;
}
