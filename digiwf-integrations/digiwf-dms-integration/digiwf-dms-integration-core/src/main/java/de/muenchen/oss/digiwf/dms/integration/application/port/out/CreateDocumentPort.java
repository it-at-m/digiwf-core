package de.muenchen.oss.digiwf.dms.integration.application.port.out;

import de.muenchen.oss.digiwf.dms.integration.domain.Document;

public interface CreateDocumentPort {

    String createDocument(Document document, String user);

}
