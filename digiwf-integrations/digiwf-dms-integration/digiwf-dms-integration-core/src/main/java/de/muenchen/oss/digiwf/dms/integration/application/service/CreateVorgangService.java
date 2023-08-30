package de.muenchen.oss.digiwf.dms.integration.application.service;

import de.muenchen.oss.digiwf.dms.integration.application.port.in.CreateVorgangUseCase;
import de.muenchen.oss.digiwf.dms.integration.application.port.out.VorgangRepository;
import de.muenchen.oss.digiwf.dms.integration.domain.Vorgang;
import de.muenchen.oss.digiwf.dms.integration.domain.VorgangArt;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateVorgangService implements CreateVorgangUseCase {

    private final VorgangRepository vorgangRepository;

    @Override
    public Vorgang createVorgang(String titel, String sachakteCOO, VorgangArt art, String user) {

        final Vorgang vorgang = new Vorgang(titel, sachakteCOO, art);

        return vorgangRepository.createVorgang(vorgang, user);
    }
}
