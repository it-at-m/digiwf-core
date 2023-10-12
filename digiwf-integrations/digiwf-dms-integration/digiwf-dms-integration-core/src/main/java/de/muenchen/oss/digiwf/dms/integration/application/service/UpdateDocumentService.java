package de.muenchen.oss.digiwf.dms.integration.application.service;

import de.muenchen.oss.digiwf.dms.integration.application.port.in.UpdateDocumentUseCase;
import de.muenchen.oss.digiwf.dms.integration.application.port.out.LoadFilePort;
import de.muenchen.oss.digiwf.dms.integration.application.port.out.UpdateDocumentPort;
import de.muenchen.oss.digiwf.dms.integration.domain.Content;
import de.muenchen.oss.digiwf.dms.integration.domain.DocumentType;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
@RequiredArgsConstructor
public class UpdateDocumentService implements UpdateDocumentUseCase {

    private final UpdateDocumentPort updateDocumentPort;

    private final LoadFilePort loadFilePort;
    @Override
    public void updateDocument(
            final String documentCOO,
            final String user,
            final DocumentType type,
            final List<String> filepaths,
            final String fileContext
    ) {

        final List<Content> contents = loadFilePort.loadFiles(filepaths, fileContext);

        updateDocumentPort.updateDocument(documentCOO, type, contents, user);

    }



}
