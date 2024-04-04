package de.muenchen.oss.digiwf.cosys.integration.application.usecase;

import de.muenchen.oss.digiwf.cosys.integration.application.port.in.CreateDocumentInPort;
import de.muenchen.oss.digiwf.cosys.integration.application.port.out.CorrelateMessageOutPort;
import de.muenchen.oss.digiwf.cosys.integration.application.port.out.GenerateDocumentOutPort;
import de.muenchen.oss.digiwf.cosys.integration.application.port.out.SaveFileToStorageOutPort;
import de.muenchen.oss.digiwf.cosys.integration.model.GenerateDocument;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;


@Slf4j
@RequiredArgsConstructor
public class CreateDocumentUseCase implements CreateDocumentInPort {

    private final SaveFileToStorageOutPort saveFileToStorageOutPort;
    private final CorrelateMessageOutPort correlateMessageOutPort;
    private final GenerateDocumentOutPort generateDocumentOutPort;


    /**
     * Generate a Document in Cosys and save it in S3 using given presigned urls.
     *
     * @param generateDocument Data for generating documents
     */
    @Override
    public void createDocument(final String processInstanceIde, final String type, final String integrationName, @Valid final GenerateDocument generateDocument) {
        final byte[] data = this.generateDocumentOutPort.generateCosysDocument(generateDocument).block();
        this.saveFileToStorageOutPort.saveDocumentInStorage(generateDocument, data);

        final Map<String, Object> correlatePayload = new HashMap<>();
        correlatePayload.put("status", true);
        this.correlateMessageOutPort.correlateMessage(processInstanceIde, type, integrationName, correlatePayload);
    }


}
