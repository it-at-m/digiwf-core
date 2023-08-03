package de.muenchen.oss.digiwf.cosys.integration.application.port.out;

import de.muenchen.oss.digiwf.cosys.integration.model.GenerateDocument;
import reactor.core.publisher.Mono;

public interface GenerateDocumentPort {

    Mono<byte[]> generateCosysDocument(final GenerateDocument generateDocument);

}
