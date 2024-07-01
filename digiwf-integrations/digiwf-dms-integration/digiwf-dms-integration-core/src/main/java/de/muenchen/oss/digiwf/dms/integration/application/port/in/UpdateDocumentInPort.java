package de.muenchen.oss.digiwf.dms.integration.application.port.in;

import de.muenchen.oss.digiwf.dms.integration.domain.DocumentResponse;
import de.muenchen.oss.digiwf.dms.integration.domain.DocumentType;

import java.util.List;

public interface UpdateDocumentInPort {

    DocumentResponse updateDocument(String documentCOO, String user, DocumentType type, List<String> filepaths, String fileContext, String processDefinition);

}
