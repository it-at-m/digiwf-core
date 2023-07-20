package io.muenchendigital.digiwf.cosys.integration.adapter.out;

import io.muenchendigital.digiwf.cosys.integration.model.DocumentStorageUrl;
import io.muenchendigital.digiwf.cosys.integration.model.GenerateDocument;
import io.muenchendigital.digiwf.message.process.api.error.BpmnError;
import io.muenchendigital.digiwf.s3.integration.client.exception.DocumentStorageClientErrorException;
import io.muenchendigital.digiwf.s3.integration.client.exception.DocumentStorageException;
import io.muenchendigital.digiwf.s3.integration.client.exception.DocumentStorageServerErrorException;
import io.muenchendigital.digiwf.s3.integration.client.repository.transfer.S3FileTransferRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;


class S3AdapterTest {

    private final S3FileTransferRepository s3FileTransferRepository = mock(S3FileTransferRepository.class);
    private S3Adapter s3Adapter;

    private final String data = "In Cosys generiertes Dokument";
    private final byte[] dataAsByteArray = data.getBytes();

    @BeforeEach
    void setup() {
        s3Adapter = new S3Adapter(s3FileTransferRepository);
    }

    @Test
    void saveDocumentInStorage() throws DocumentStorageException, DocumentStorageClientErrorException, DocumentStorageServerErrorException {

        final DocumentStorageUrl documentStorageUrl = new DocumentStorageUrl("URL", "Path", "POST");
        List<DocumentStorageUrl> listOfURls = List.of(documentStorageUrl);

        final GenerateDocument generateDocument = new GenerateDocument("Client", "Role", "guid", null, listOfURls);

        s3Adapter.saveDocumentInStorage(generateDocument, dataAsByteArray);

        verify(s3FileTransferRepository).saveFile("URL", dataAsByteArray);
        verifyNoMoreInteractions(s3FileTransferRepository);

    }

    @Test
    void updateDocumentInStorage() throws DocumentStorageException, DocumentStorageClientErrorException, DocumentStorageServerErrorException {

        final DocumentStorageUrl documentStorageUrl = new DocumentStorageUrl("URL", "Path", "PUT");
        List<DocumentStorageUrl> listOfURls = List.of(documentStorageUrl);

        final GenerateDocument generateDocument = new GenerateDocument("Client", "Role", "guid", null, listOfURls);

        s3Adapter.saveDocumentInStorage(generateDocument, dataAsByteArray);

        verify(s3FileTransferRepository).updateFile("URL", dataAsByteArray);
        verifyNoMoreInteractions(s3FileTransferRepository);

    }

    @Test
    void saveDocumentInStorageWithGetRequest() {

        final DocumentStorageUrl documentStorageUrl = new DocumentStorageUrl("URL", "Path", "GET");
        List<DocumentStorageUrl> listOfURls = List.of(documentStorageUrl);

        final GenerateDocument generateDocument = new GenerateDocument("Client", "Role", "guid", null, listOfURls);

        BpmnError bpmnError = assertThrows(BpmnError.class,  () -> { s3Adapter.saveDocumentInStorage(generateDocument, dataAsByteArray);});

        String expectedMessage = "Document storage action GET is not supported.";
        String actualMessage = bpmnError.getErrorMessage();

        assertEquals(expectedMessage, actualMessage);
        assertEquals("S3_FILE_SAVE_ERROR",bpmnError.getErrorCode());


    }

    @Test
    void saveDocumentInStorageWithThrowsDocumentStorageException() throws DocumentStorageException, DocumentStorageClientErrorException, DocumentStorageServerErrorException {
        doThrow(new DocumentStorageException("DocumentStorageClientErrorException", new Exception())).when(s3FileTransferRepository).saveFile(anyString(), any());

        final DocumentStorageUrl documentStorageUrl = new DocumentStorageUrl("URL", "Path", "POST");
        List<DocumentStorageUrl> listOfURls = List.of(documentStorageUrl);

        final GenerateDocument generateDocument = new GenerateDocument("Client", "Role", "guid", null, listOfURls);

        BpmnError bpmnError = assertThrows(BpmnError.class, () -> {
            s3Adapter.saveDocumentInStorage(generateDocument, dataAsByteArray);
        });

        String expectedMessage = "DocumentStorageClientErrorException";
        String actualMessage = bpmnError.getErrorMessage();

        assertEquals(expectedMessage, actualMessage);

        assertEquals("S3_FILE_SAVE_ERROR",bpmnError.getErrorCode());

    }

}