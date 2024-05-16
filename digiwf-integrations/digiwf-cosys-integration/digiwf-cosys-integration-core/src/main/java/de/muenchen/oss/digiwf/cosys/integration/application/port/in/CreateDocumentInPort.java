package de.muenchen.oss.digiwf.cosys.integration.application.port.in;

import de.muenchen.oss.digiwf.cosys.integration.model.GenerateDocument;
import jakarta.validation.Valid;

public interface CreateDocumentInPort {

    void createDocument(final String processInstanceId, final String type, final String integrationName, @Valid final GenerateDocument generateDocument);

}
