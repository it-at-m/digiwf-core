package de.muenchen.oss.digiwf.cosys.integration.application.port.out;

import de.muenchen.oss.digiwf.cosys.integration.domain.model.GenerateDocument;
import reactor.core.publisher.Mono;

public interface GenerateDocumentOutPort {

    Mono<byte[]> generateCosysDocument(final GenerateDocument generateDocument);

}
