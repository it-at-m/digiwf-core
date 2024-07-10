package de.muenchen.oss.digiwf.cosys.integration.adapter.out.s3;

import de.muenchen.oss.digiwf.cosys.integration.domain.model.DocumentStorageUrl;
import de.muenchen.oss.digiwf.message.process.api.error.BpmnError;
import de.muenchen.oss.digiwf.s3.integration.client.exception.DocumentStorageClientErrorException;
import de.muenchen.oss.digiwf.s3.integration.client.exception.DocumentStorageException;
import de.muenchen.oss.digiwf.s3.integration.client.exception.DocumentStorageServerErrorException;
import de.muenchen.oss.digiwf.s3.integration.client.repository.DocumentStorageFileRepository;
import de.muenchen.oss.digiwf.s3.integration.client.repository.transfer.S3FileTransferRepository;
import de.muenchen.oss.digiwf.s3.integration.client.service.FileService;
import de.muenchen.oss.digiwf.s3.integration.client.service.S3StorageUrlProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.util.unit.DataSize;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class S3AdapterTest {
    private static final String DATA = "In CoSys generiertes Dokument";
    private static final byte[] DATA_AS_BYTE_ARRAY = DATA.getBytes();
    private static final DataSize ALLOWED_FILE_SIZE = DataSize.ofBytes(DATA_AS_BYTE_ARRAY.length);
    private static final DataSize ALLOWED_BATCH_SIZE = DataSize.ofMegabytes(110);
    private static final byte[] TOO_LARGE_FILE = (DATA + "!").getBytes(); // 1 Mbyte over allowed

    private final S3FileTransferRepository s3FileTransferRepository = mock(S3FileTransferRepository.class);
    private final DocumentStorageFileRepository documentStorageFileRepository = mock(DocumentStorageFileRepository.class);
    private final S3StorageUrlProvider s3DomainService = mock(S3StorageUrlProvider.class);
    private final FileService fileService = new FileService(null, ALLOWED_FILE_SIZE, ALLOWED_BATCH_SIZE);
    private S3Adapter s3Adapter;

    @BeforeEach
    void setup() {
        s3Adapter = new S3Adapter(s3FileTransferRepository, documentStorageFileRepository, fileService, s3DomainService);
    }

    @Test
    void saveDocumentInStorage() throws DocumentStorageException, DocumentStorageClientErrorException, DocumentStorageServerErrorException {
        // deprecated
        final DocumentStorageUrl documentStorageUrl = new DocumentStorageUrl("URL", "Path", "POST");
        List<DocumentStorageUrl> listOfURls = List.of(documentStorageUrl);
        s3Adapter.saveDocumentInStorage(listOfURls, DATA_AS_BYTE_ARRAY);
        verify(s3FileTransferRepository).saveFile("URL", DATA_AS_BYTE_ARRAY);
        // v2
        s3Adapter.saveDocumentInStorage("fileContext", "filePath.txt", DATA_AS_BYTE_ARRAY);
        verify(documentStorageFileRepository).saveFile("fileContext/filePath.txt", DATA_AS_BYTE_ARRAY, 1, null);
        verifyNoMoreInteractions(s3FileTransferRepository);

    }

    @Deprecated
    @Test
    void updateDocumentInStorage() throws DocumentStorageException, DocumentStorageClientErrorException, DocumentStorageServerErrorException {
        // deprecated
        final DocumentStorageUrl documentStorageUrl = new DocumentStorageUrl("URL", "Path", "PUT");
        List<DocumentStorageUrl> listOfURls = List.of(documentStorageUrl);
        s3Adapter.saveDocumentInStorage(listOfURls, DATA_AS_BYTE_ARRAY);
        verify(s3FileTransferRepository).updateFile("URL", DATA_AS_BYTE_ARRAY);
        verifyNoMoreInteractions(s3FileTransferRepository);

    }

    @Deprecated
    @Test
    void saveDocumentInStorageWithGetRequest() {

        final DocumentStorageUrl documentStorageUrl = new DocumentStorageUrl("URL", "Path", "GET");
        List<DocumentStorageUrl> listOfURls = List.of(documentStorageUrl);

        BpmnError bpmnError = assertThrows(BpmnError.class, () -> s3Adapter.saveDocumentInStorage(listOfURls, DATA_AS_BYTE_ARRAY));

        String expectedMessage = "Document storage action GET is not supported.";
        String actualMessage = bpmnError.getErrorMessage();

        assertEquals(expectedMessage, actualMessage);
        assertEquals("S3_FILE_SAVE_ERROR", bpmnError.getErrorCode());

    }

    @Test
    void saveDocumentInStorageWithThrowsDocumentStorageException()
            throws DocumentStorageException, DocumentStorageClientErrorException, DocumentStorageServerErrorException {
        // deprecated
        doThrow(new DocumentStorageException("DocumentStorageClientErrorException", new Exception())).when(s3FileTransferRepository)
                .saveFile(anyString(), any());

        final DocumentStorageUrl documentStorageUrl = new DocumentStorageUrl("URL", "Path", "POST");
        List<DocumentStorageUrl> listOfURls = List.of(documentStorageUrl);

        BpmnError bpmnError = assertThrows(BpmnError.class, () -> s3Adapter.saveDocumentInStorage(listOfURls, DATA_AS_BYTE_ARRAY));

        String expectedMessage = "DocumentStorageClientErrorException";
        String actualMessage = bpmnError.getErrorMessage();

        assertEquals(expectedMessage, actualMessage);

        assertEquals("S3_FILE_SAVE_ERROR", bpmnError.getErrorCode());
        // v2
        doThrow(new DocumentStorageException("DocumentStorageClientErrorException", new Exception())).when(documentStorageFileRepository)
                .saveFile(anyString(), any(), eq(1), isNull());
        bpmnError = assertThrows(BpmnError.class, () -> s3Adapter.saveDocumentInStorage("fileContext", "filePath.txt", DATA_AS_BYTE_ARRAY));

        expectedMessage = "DocumentStorageClientErrorException";
        actualMessage = bpmnError.getErrorMessage();

        assertEquals(expectedMessage, actualMessage);

        assertEquals("S3_FILE_SAVE_ERROR", bpmnError.getErrorCode());
    }

    @Test
    void testSaveDocumentInStorageThrowsBpmnErrorForInvalidFileSize() {
        // deprecated
        final DocumentStorageUrl documentStorageUrl = new DocumentStorageUrl("URL", "Path", "POST");
        final List<DocumentStorageUrl> listOfURls = List.of(documentStorageUrl);

        String expectedMessage = String.format("Invalid file size %d MB. Allowed are %d MB.", DataSize.ofBytes(TOO_LARGE_FILE.length).toMegabytes(),
                ALLOWED_FILE_SIZE.toMegabytes());

        assertThatThrownBy(() -> s3Adapter.saveDocumentInStorage(listOfURls, TOO_LARGE_FILE))
                .isInstanceOf(BpmnError.class)
                .extracting("errorCode", "errorMessage")
                .containsExactly("S3_FILE_SIZE_ERROR", expectedMessage);
        // v2
        expectedMessage = String.format("Invalid file size %d MB. Allowed are %d MB.", DataSize.ofBytes(TOO_LARGE_FILE.length).toMegabytes(),
                ALLOWED_FILE_SIZE.toMegabytes());

        assertThatThrownBy(() -> s3Adapter.saveDocumentInStorage("fileContext", "filePath.txt", TOO_LARGE_FILE))
                .isInstanceOf(BpmnError.class)
                .extracting("errorCode", "errorMessage")
                .containsExactly("S3_FILE_SIZE_ERROR", expectedMessage);
    }

}
