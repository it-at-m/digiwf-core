package de.muenchen.oss.digiwf.dms.integration.application.port.in;

import de.muenchen.oss.digiwf.dms.integration.domain.DocumentType;

import java.util.List;

public interface UpdateDocumentInPort {

    void updateDocument(String documentCOO, String user, DocumentType type, List<String> filepaths, String fileContext, String processDefinition);

}
