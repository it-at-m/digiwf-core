package de.muenchen.oss.digiwf.dms.integration.application.usecase;

import de.muenchen.oss.digiwf.dms.integration.application.port.in.UpdateDocumentInPort;
import de.muenchen.oss.digiwf.dms.integration.application.port.out.ListContentOutPort;
import de.muenchen.oss.digiwf.dms.integration.application.port.out.LoadFileOutPort;
import de.muenchen.oss.digiwf.dms.integration.application.port.out.UpdateDocumentOutPort;
import de.muenchen.oss.digiwf.dms.integration.domain.Content;
import de.muenchen.oss.digiwf.dms.integration.domain.DocumentResponse;
import de.muenchen.oss.digiwf.dms.integration.domain.DocumentType;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
@RequiredArgsConstructor
public class UpdateDocumentUseCase implements UpdateDocumentInPort {

    private final UpdateDocumentOutPort updateDocumentOutPort;
    private final ListContentOutPort listContentOutPort;

    private final LoadFileOutPort loadFileOutPort;

    @Override
    public DocumentResponse updateDocument(
            final String documentCOO,
            final String user,
            final DocumentType type,
            final List<String> filepaths,
            final String fileContext,
            final String processDefinition
    ) {

        final List<Content> contents = loadFileOutPort.loadFiles(filepaths, fileContext, processDefinition);

        updateDocumentOutPort.updateDocument(documentCOO, type, contents, user);
        List<String> contentCoos = listContentOutPort.listContentCoos(documentCOO, user);
        return new DocumentResponse(documentCOO, contentCoos);
    }


}
