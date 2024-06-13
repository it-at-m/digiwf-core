package de.muenchen.oss.digiwf.dms.integration.application.port.in;

import de.muenchen.oss.digiwf.dms.integration.domain.DocumentResponse;
import de.muenchen.oss.digiwf.dms.integration.domain.DocumentType;

import java.time.LocalDate;
import java.util.List;

public interface CreateDocumentInPort {

    DocumentResponse createDocument(final String procedureCOO, final String title, final LocalDate date, final String user, DocumentType type,
                                    final List<String> filepaths, final String fileContext, final String processDefinition);

}
