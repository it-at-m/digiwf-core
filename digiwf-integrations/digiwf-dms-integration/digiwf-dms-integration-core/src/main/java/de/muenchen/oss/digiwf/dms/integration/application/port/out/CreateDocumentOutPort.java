package de.muenchen.oss.digiwf.dms.integration.application.port.out;

import de.muenchen.oss.digiwf.dms.integration.domain.Document;

public interface CreateDocumentOutPort {

    String createDocument(Document document, String user);

}
