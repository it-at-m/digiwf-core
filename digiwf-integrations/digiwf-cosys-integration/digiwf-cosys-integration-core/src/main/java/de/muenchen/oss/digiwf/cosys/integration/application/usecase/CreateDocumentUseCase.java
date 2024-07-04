package de.muenchen.oss.digiwf.cosys.integration.application.usecase;

import de.muenchen.oss.digiwf.cosys.integration.application.port.in.CreateDocumentInPort;
import de.muenchen.oss.digiwf.cosys.integration.application.port.out.GenerateDocumentOutPort;
import de.muenchen.oss.digiwf.cosys.integration.application.port.out.SaveFileToStorageOutPort;
import de.muenchen.oss.digiwf.cosys.integration.domain.model.GenerateDocument;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RequiredArgsConstructor
public class CreateDocumentUseCase implements CreateDocumentInPort {

    private final SaveFileToStorageOutPort saveFileToStorageOutPort;
    private final GenerateDocumentOutPort generateDocumentOutPort;


    /**
     * Generate a Document in Cosys and save it in S3 using given presigned urls.
     *
     * @param generateDocument Data for generating documents
     */
    @Override
    public void createDocument(@Valid final GenerateDocument generateDocument) {
        final byte[] data = this.generateDocumentOutPort.generateCosysDocument(generateDocument).block();
        this.saveFileToStorageOutPort.saveDocumentInStorage(generateDocument, data);
    }


}
