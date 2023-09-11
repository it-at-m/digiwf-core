package de.muenchen.oss.digiwf.dms.integration.application.service;

import de.muenchen.oss.digiwf.dms.integration.application.port.in.CreateDocumentUseCase;
import de.muenchen.oss.digiwf.dms.integration.application.port.out.LoadFilePort;
import de.muenchen.oss.digiwf.dms.integration.application.port.out.VorgangRepository;
import de.muenchen.oss.digiwf.dms.integration.domain.Document;
import de.muenchen.oss.digiwf.dms.integration.domain.DocumentType;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Validated
@RequiredArgsConstructor
public class CreateDocumentService implements CreateDocumentUseCase {

    private final VorgangRepository vorgangRepository;

    private final LoadFilePort loadFilePort;

    @Override
    public Document createDocument(
            @NotBlank final String vorgangCOO,
            @NotBlank final String titel,
            @NotBlank final String user,
            @NotNull final DocumentType type,
            @NotBlank final List<String> filepaths,
            @NotBlank final String fileContext
            ) {

//        final Vorgang vorgang = new Vorgang(sachakteCOO, titel, art);
//
//        return vorgangRepository.createVorgang(vorgang, user);
        return null;
    }

}
