package de.muenchen.oss.digiwf.dms.integration.application.service;

import de.muenchen.oss.digiwf.dms.integration.application.port.in.CreateDocumentUseCase;
import de.muenchen.oss.digiwf.dms.integration.application.port.out.LoadFilePort;
import de.muenchen.oss.digiwf.dms.integration.application.port.out.ProcedureRepository;
import de.muenchen.oss.digiwf.dms.integration.domain.Document;
import de.muenchen.oss.digiwf.dms.integration.domain.DocumentType;
import de.muenchen.oss.digiwf.dms.integration.domain.File;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Validated
@RequiredArgsConstructor
public class CreateDocumentService implements CreateDocumentUseCase {

    private final ProcedureRepository procedureRepository;

    private final LoadFilePort loadFilePort;

    @Override
    public Document createDocument(
            @NotBlank final String procedureCOO,
            @NotBlank final String title,
            @NotBlank final String user,
            @NotNull final DocumentType type,
            @NotBlank final List<String> filepaths,
            @NotBlank final String fileContext
            ) {

        final List<File> files = loadFilePort.loadFiles(filepaths, fileContext);

        //final Document document = new Document(procedureCOO, title, type, files);

        //return procedureRepository.createDocument(document, user);
        return null;

    }

}
