package de.muenchen.oss.digiwf.alw.integration.application.port.in;

import de.muenchen.oss.digiwf.alw.integration.domain.exception.AlwException;
import de.muenchen.oss.digiwf.alw.integration.domain.model.Responsibility;
import de.muenchen.oss.digiwf.alw.integration.domain.model.ResponsibilityRequest;
import jakarta.validation.Valid;
import org.springframework.lang.NonNull;

/**
 * Retrieves the responsibility in Ausländerwesen (ALW).
 */
public interface GetResponsibilityInPort {

    /**
     * Retrieves the responsibility for given parameters.
     *
     * @param request Responsibility request. Must be non-null and valid.
     * @return The determined responsibility. Non-null.
     * @throws AlwException If any issues occur during the retrieval process.
     */
    @NonNull
    Responsibility getResponsibility(@NonNull @Valid ResponsibilityRequest request) throws AlwException;
}
