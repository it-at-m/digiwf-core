package de.muenchen.oss.digiwf.dms.integration.application.usecase;

import de.muenchen.oss.digiwf.dms.integration.application.port.in.DepositObjectInPort;
import de.muenchen.oss.digiwf.dms.integration.application.port.out.DepositObjectOutPort;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Validated
@RequiredArgsConstructor
public class DepositObjectUseCase implements DepositObjectInPort {

    private final DepositObjectOutPort depositObjectOutPort;

    @Override
    public void depositObject(@NotBlank String objectCoo, @NotBlank String user) {
        depositObjectOutPort.depositObject(objectCoo, user);
    }
}
