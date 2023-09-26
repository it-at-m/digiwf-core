package de.muenchen.oss.digiwf.dms.integration.application.service;

import de.muenchen.oss.digiwf.dms.integration.application.port.in.CreateDocumentUseCase;
import de.muenchen.oss.digiwf.dms.integration.application.port.out.LoadFilePort;
import de.muenchen.oss.digiwf.dms.integration.application.port.out.ProcedureRepository;
import de.muenchen.oss.digiwf.dms.integration.domain.Content;
import de.muenchen.oss.digiwf.dms.integration.domain.Document;
import de.muenchen.oss.digiwf.dms.integration.domain.DocumentType;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
@RequiredArgsConstructor
public class CreateDocumentService implements CreateDocumentUseCase {

    private final ProcedureRepository procedureRepository;

    private final LoadFilePort loadFilePort;

    @Override
    public String createDocument(
            final String procedureCOO,
            final String title,
            final String user,
            final DocumentType type,
            final List<String> filepaths,
            final String fileContext
            ) {

        final List<Content> contents = loadFilePort.loadFiles(filepaths, fileContext);

        final Document document = new Document(procedureCOO, title, type, contents);

        return procedureRepository.createDocument(document, user);

    }

}
