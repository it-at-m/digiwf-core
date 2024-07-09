package de.muenchen.oss.digiwf.cosys.integration.application.port.out;

import de.muenchen.oss.digiwf.cosys.integration.domain.model.DocumentStorageUrl;

import java.util.List;

public interface SaveFileToStorageOutPort {
    @Deprecated
    void saveDocumentInStorage(
            final List<DocumentStorageUrl> documentStorageUrls,
            final byte[] data
    );

    void saveDocumentInStorage(
            final String fileContext,
            final String filePath,
            final byte[] data
    );

}
