package de.muenchen.oss.digiwf.cosys.integration.application.port.in;

import de.muenchen.oss.digiwf.cosys.integration.domain.model.DocumentStorageUrl;
import de.muenchen.oss.digiwf.cosys.integration.domain.model.GenerateDocument;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public interface CreateDocumentInPort {

    @Deprecated
    void createDocument(
            @Valid final GenerateDocument generateDocument,
            @Valid @Size(min = 1, max = 1) final List<DocumentStorageUrl> documentStorageUrls
    );

    void createDocument(
            @Valid final GenerateDocument generateDocument,
            @NotBlank final String fileContext,
            @NotBlank final String filePath
    );
}
