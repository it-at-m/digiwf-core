package de.muenchen.oss.digiwf.cosys.integration.application.usecase;

import de.muenchen.oss.digiwf.cosys.integration.application.port.in.CreateDocumentInPort;
import de.muenchen.oss.digiwf.cosys.integration.application.port.out.GenerateDocumentOutPort;
import de.muenchen.oss.digiwf.cosys.integration.application.port.out.SaveFileToStorageOutPort;
import de.muenchen.oss.digiwf.cosys.integration.domain.model.DocumentStorageUrl;
import de.muenchen.oss.digiwf.cosys.integration.domain.model.GenerateDocument;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;


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
    @Deprecated
    @Override
    public void createDocument(
            @Valid final GenerateDocument generateDocument,
            @Valid @Size(min = 1, max = 1) final List<DocumentStorageUrl> documentStorageUrls
    ) {
        final byte[] data = this.generateDocumentOutPort.generateCosysDocument(generateDocument).block();
        this.saveFileToStorageOutPort.saveDocumentInStorage(documentStorageUrls, data);
    }

    /**
     * Generate a document in Cosys and save it in S3 using file context and path.
     *
     * @param generateDocument Data for generating documents
     * @param fileContext      File context to save document to
     * @param filePath         Path to save document to
     */
    @Override
    public void createDocument(
            @Valid final GenerateDocument generateDocument,
            @NotBlank final String fileContext,
            @NotBlank final String filePath
    ) {
        final byte[] data = this.generateDocumentOutPort.generateCosysDocument(generateDocument).block();
        this.saveFileToStorageOutPort.saveDocumentInStorage(fileContext, filePath, data);
    }
}
