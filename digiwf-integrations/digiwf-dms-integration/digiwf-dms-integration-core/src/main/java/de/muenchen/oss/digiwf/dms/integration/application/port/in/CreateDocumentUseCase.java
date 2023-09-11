package de.muenchen.oss.digiwf.dms.integration.application.port.in;

import de.muenchen.oss.digiwf.dms.integration.domain.Document;
import de.muenchen.oss.digiwf.dms.integration.domain.DocumentType;

import java.util.List;

public interface CreateDocumentUseCase {

    Document createDocument(final String vorgangCOO, final String titel, final String user, DocumentType type, final List<String> filepaths, final String fileContext);

}
