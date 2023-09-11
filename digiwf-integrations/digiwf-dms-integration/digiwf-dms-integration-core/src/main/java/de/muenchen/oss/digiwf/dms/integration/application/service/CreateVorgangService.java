package de.muenchen.oss.digiwf.dms.integration.application.service;

import de.muenchen.oss.digiwf.dms.integration.application.port.in.CreateVorgangUseCase;
import de.muenchen.oss.digiwf.dms.integration.application.port.out.VorgangRepository;
import de.muenchen.oss.digiwf.dms.integration.domain.Procedure;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Validated
@RequiredArgsConstructor
public class CreateVorgangService implements CreateVorgangUseCase {

    private final VorgangRepository vorgangRepository;

    @Override
    public Procedure createVorgang(
            @NotBlank final String titel,
            @NotBlank final String fileCOO,
            @NotBlank final String user) {

        final Procedure procedure = new Procedure(fileCOO, titel);

        return vorgangRepository.createVorgang(procedure, user);
    }
}
