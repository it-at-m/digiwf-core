package de.muenchen.oss.digiwf.dms.integration.application.service;

import de.muenchen.oss.digiwf.dms.integration.application.port.in.CreateDokumentUseCase;
import de.muenchen.oss.digiwf.dms.integration.domain.DokumentArt;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CreateDokumentService implements CreateDokumentUseCase {

    @Override
    public void createDocument(
            @NotBlank final String vorgangCOO,
            @NotBlank final String titel,
            @NotBlank final String user,
            @NotNull final DokumentArt art,
            @NotBlank final String dateien
            ) {

//        final Vorgang vorgang = new Vorgang(sachakteCOO, titel, art);
//
//        return vorgangRepository.createVorgang(vorgang, user);
    }

}
