package de.muenchen.oss.digiwf.dms.integration.application.port.in;

import de.muenchen.oss.digiwf.dms.integration.domain.DocumentType;

import java.util.List;

public interface UpdateDocumentUseCase {

    void updateDocument(final String documentCOO, final String user, DocumentType type, final List<String> filepaths, final String fileContext);

}
