package de.muenchen.oss.digiwf.dms.integration.application.service;

import de.muenchen.oss.digiwf.dms.integration.application.port.in.DepositObjectUseCase;
import de.muenchen.oss.digiwf.dms.integration.application.port.out.DepositObjectPort;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotBlank;

@Validated
@RequiredArgsConstructor
public class DepositObjectService implements DepositObjectUseCase {

    private final DepositObjectPort depositObjectPort;

    @Override
    public void depositObject(@NotBlank String objectCoo, @NotBlank String user) {
        depositObjectPort.depositObject(objectCoo, user);
    }
}
