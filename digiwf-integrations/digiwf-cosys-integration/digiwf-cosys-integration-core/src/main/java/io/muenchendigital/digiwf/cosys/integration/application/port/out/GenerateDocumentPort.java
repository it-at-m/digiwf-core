package io.muenchendigital.digiwf.cosys.integration.application.port.out;

import io.muenchendigital.digiwf.cosys.integration.model.GenerateDocument;
import reactor.core.publisher.Mono;

public interface GenerateDocumentPort {

    Mono<byte[]> generateCosysDocument(final GenerateDocument generateDocument);

}
