package de.muenchen.oss.digiwf.dms.integration.application.service;

import de.muenchen.oss.digiwf.dms.integration.application.port.in.CancelObjectUseCase;
import de.muenchen.oss.digiwf.dms.integration.application.port.out.CancelObjectPort;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotBlank;


@Validated
@RequiredArgsConstructor
public class CancelObjectService implements CancelObjectUseCase {

    private final CancelObjectPort cancelObjectPort;

    @Override
    public void cancelObject(@NotBlank final String objectCoo, @NotBlank final String user) {
        cancelObjectPort.cancelObject(objectCoo, user);
    }
}
