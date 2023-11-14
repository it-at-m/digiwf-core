package de.muenchen.oss.digiwf.cosys.integration.application.port.in;

import de.muenchen.oss.digiwf.cosys.integration.model.GenerateDocument;

import jakarta.validation.Valid;

public interface CreateDocument {

    void createDocument(final String processInstanceIde, final String messageName, @Valid final GenerateDocument generateDocument);

}
