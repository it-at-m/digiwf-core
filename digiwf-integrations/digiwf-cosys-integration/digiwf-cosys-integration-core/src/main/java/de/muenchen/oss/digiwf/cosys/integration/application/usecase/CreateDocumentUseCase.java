package de.muenchen.oss.digiwf.cosys.integration.application.usecase;

import de.muenchen.oss.digiwf.cosys.integration.application.port.in.CreateDocument;
import de.muenchen.oss.digiwf.cosys.integration.model.GenerateDocument;
import de.muenchen.oss.digiwf.cosys.integration.application.port.out.CorrelateMessagePort;
import de.muenchen.oss.digiwf.cosys.integration.application.port.out.GenerateDocumentPort;
import de.muenchen.oss.digiwf.cosys.integration.application.port.out.SaveFileToStoragePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.validation.Valid;

import java.util.HashMap;
import java.util.Map;


@Slf4j
@RequiredArgsConstructor
public class CreateDocumentUseCase implements CreateDocument {

    private final SaveFileToStoragePort saveFileToStoragePort;
    private final CorrelateMessagePort correlateMessagePort;
    private final GenerateDocumentPort generateDocumentPort;


    /**
     * Generate a Document in Cosys and save it in S3 using given presigned urls.
     *
     * @param generateDocument Data for generating documents
     */
    @Override
    public void createDocument(final String processInstanceIde, final String messageName, @Valid final GenerateDocument generateDocument) {
        final byte[] data = this.generateDocumentPort.generateCosysDocument(generateDocument).block();
        this.saveFileToStoragePort.saveDocumentInStorage(generateDocument, data);

        final Map<String, Object> correlatePayload = new HashMap<>();
        correlatePayload.put("status", true);
        this.correlateMessagePort.correlateMessage(processInstanceIde,messageName,correlatePayload);
    }


}
