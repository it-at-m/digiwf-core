package de.muenchen.oss.digiwf.dms.integration.application.port.out;

import de.muenchen.oss.digiwf.dms.integration.domain.Content;
import de.muenchen.oss.digiwf.dms.integration.domain.DocumentType;

import java.util.List;

public interface UpdateDocumentPort {

    void updateDocument(String documentCOO, DocumentType type, List<Content> contents, String user);

}
