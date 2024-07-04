package de.muenchen.oss.digiwf.cosys.integration.application.port.in;

import de.muenchen.oss.digiwf.cosys.integration.domain.model.GenerateDocument;
import jakarta.validation.Valid;

public interface CreateDocumentInPort {

    void createDocument(@Valid final GenerateDocument generateDocument);

}
