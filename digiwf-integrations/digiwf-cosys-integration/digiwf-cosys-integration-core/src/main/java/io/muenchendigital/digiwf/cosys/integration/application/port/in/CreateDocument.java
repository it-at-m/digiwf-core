package io.muenchendigital.digiwf.cosys.integration.application.port.in;

import io.muenchendigital.digiwf.cosys.integration.model.GenerateDocument;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

public interface CreateDocument {

    void createDocument(final String processInstanceIde, final String messageName, @Valid final GenerateDocument generateDocument);

}
