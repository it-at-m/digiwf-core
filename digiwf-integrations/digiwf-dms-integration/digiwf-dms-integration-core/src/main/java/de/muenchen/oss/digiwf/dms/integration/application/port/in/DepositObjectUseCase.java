package de.muenchen.oss.digiwf.dms.integration.application.port.in;

import jakarta.validation.constraints.NotBlank;

public interface DepositObjectUseCase {

    void depositObject(@NotBlank final String objectCoo, @NotBlank final String user);

}
