package de.muenchen.oss.digiwf.dms.integration.application.service;

import de.muenchen.oss.digiwf.dms.integration.application.port.in.CreateVorgangUseCase;
import de.muenchen.oss.digiwf.dms.integration.application.port.out.VorgangRepository;
import de.muenchen.oss.digiwf.dms.integration.domain.Vorgang;
import de.muenchen.oss.digiwf.dms.integration.domain.VorgangArt;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Validated
@RequiredArgsConstructor
public class CreateVorgangService implements CreateVorgangUseCase {

    private final VorgangRepository vorgangRepository;

    @Override
    public Vorgang createVorgang(
            @NotBlank final String titel,
            @NotBlank final String sachakteCOO,
            @NotNull final VorgangArt art,
            @NotBlank final String user) {

        final Vorgang vorgang = new Vorgang(sachakteCOO, titel, art);

        return vorgangRepository.createVorgang(vorgang, user);
    }
}
