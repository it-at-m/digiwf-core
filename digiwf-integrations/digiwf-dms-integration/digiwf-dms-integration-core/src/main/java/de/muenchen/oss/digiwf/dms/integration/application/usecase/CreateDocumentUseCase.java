package de.muenchen.oss.digiwf.dms.integration.application.usecase;

import de.muenchen.oss.digiwf.dms.integration.application.port.in.CreateDocumentInPort;
import de.muenchen.oss.digiwf.dms.integration.application.port.out.CreateDocumentOutPort;
import de.muenchen.oss.digiwf.dms.integration.application.port.out.ListContentOutPort;
import de.muenchen.oss.digiwf.dms.integration.application.port.out.LoadFileOutPort;
import de.muenchen.oss.digiwf.dms.integration.domain.Content;
import de.muenchen.oss.digiwf.dms.integration.domain.Document;
import de.muenchen.oss.digiwf.dms.integration.domain.DocumentResponse;
import de.muenchen.oss.digiwf.dms.integration.domain.DocumentType;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.util.List;

@Validated
@RequiredArgsConstructor
public class CreateDocumentUseCase implements CreateDocumentInPort {

    private final CreateDocumentOutPort createDocumentOutPort;

    private final LoadFileOutPort loadFileOutPort;
    private final ListContentOutPort listContentOutPort;

    @Override
    public DocumentResponse createDocument(
            final String procedureCOO,
            final String title,
            final LocalDate date,
            final String user,
            final DocumentType type,
            final List<String> filepaths,
            final String fileContext,
            final String processDefinition
    ) {

        final List<Content> contents = loadFileOutPort.loadFiles(filepaths, fileContext, processDefinition);

        final Document document = new Document(procedureCOO, title, date, type, contents);

        String documentCoo = createDocumentOutPort.createDocument(document, user);
        List<String> contentCoos = listContentOutPort.listContentCoos(documentCoo, user);

        return new DocumentResponse(documentCoo, contentCoos);
    }
}
