package de.muenchen.oss.digiwf.dms.integration.application.port.in;

import javax.validation.constraints.NotBlank;

public interface CancelObjectUseCase {

    void cancelObject(@NotBlank final String objectCoo, @NotBlank final String user);

}
