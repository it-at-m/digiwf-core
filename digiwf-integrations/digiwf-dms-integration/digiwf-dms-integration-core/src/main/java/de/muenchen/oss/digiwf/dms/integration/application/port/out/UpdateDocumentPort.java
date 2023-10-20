package de.muenchen.oss.digiwf.dms.integration.application.port.out;

import de.muenchen.oss.digiwf.dms.integration.domain.DocumentType;
import de.muenchen.oss.digiwf.dms.integration.domain.File;

import java.util.List;

public interface UpdateDocumentPort {

    void updateDocument(String documentCOO, DocumentType type, List<File> contents, String user);

}
