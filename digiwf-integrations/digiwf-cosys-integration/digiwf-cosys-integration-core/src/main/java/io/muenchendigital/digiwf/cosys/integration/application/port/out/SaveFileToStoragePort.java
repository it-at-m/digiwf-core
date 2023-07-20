package io.muenchendigital.digiwf.cosys.integration.application.port.out;

import io.muenchendigital.digiwf.cosys.integration.model.GenerateDocument;

public interface SaveFileToStoragePort {

    void saveDocumentInStorage(final GenerateDocument generateDocument, final byte[] data);

}
