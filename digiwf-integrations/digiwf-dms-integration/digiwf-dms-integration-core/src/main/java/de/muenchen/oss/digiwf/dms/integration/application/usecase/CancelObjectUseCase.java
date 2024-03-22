package de.muenchen.oss.digiwf.dms.integration.application.usecase;

import de.muenchen.oss.digiwf.dms.integration.application.port.in.CancelObjectInPort;
import de.muenchen.oss.digiwf.dms.integration.application.port.out.CancelObjectOutPort;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;


@Validated
@RequiredArgsConstructor
public class CancelObjectUseCase implements CancelObjectInPort {

    private final CancelObjectOutPort cancelObjectOutPort;

    @Override
    public void cancelObject(@NotBlank final String objectCoo, @NotBlank final String user) {
        cancelObjectOutPort.cancelObject(objectCoo, user);
    }
}
