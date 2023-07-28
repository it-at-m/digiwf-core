package io.muenchendigital.digiwf.email.integration.adapter.out;

import io.muenchendigital.digiwf.email.integration.model.FileAttachment;
import io.muenchendigital.digiwf.email.integration.model.PresignedUrl;
import de.muenchen.oss.digiwf.message.process.api.error.BpmnError;
import io.muenchendigital.digiwf.s3.integration.client.exception.DocumentStorageException;
import io.muenchendigital.digiwf.s3.integration.client.repository.transfer.S3FileTransferRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class S3AdapterTest {

    private final S3FileTransferRepository s3FileTransferRepository = mock(S3FileTransferRepository.class);

    private S3Adapter s3Adapter;

    @BeforeEach
    void setup() {
        s3Adapter = new S3Adapter(s3FileTransferRepository);
    }

    @Test
    void testLoadAttachment_Success() throws IOException, BpmnError, DocumentStorageException {
        final String url = "http://localhost:3000/some-file.txt";
        final String fileContent = "This is the content of the file.";
        final PresignedUrl presignedUrl = new PresignedUrl(url, "/path/to/some-file.txt", "GET");

        final InputStream inputStream = new ByteArrayInputStream(fileContent.getBytes(StandardCharsets.UTF_8));
        when(s3FileTransferRepository.getFileInputStream(url)).thenReturn(inputStream);

        final FileAttachment result = s3Adapter.loadAttachment(presignedUrl);

        Assertions.assertEquals("some-file.txt", result.getFileName());
        Assertions.assertEquals(fileContent, new String(result.getFile().getInputStream().readAllBytes(), StandardCharsets.UTF_8));
    }

    @Test
    void testLoadAttachment_DocumentStorageException() throws DocumentStorageException {
        final String url = "http://localhost:3000/some-file.txt";
        final PresignedUrl presignedUrl = new PresignedUrl(url, "/path/to/some-file.txt", "GET");

        when(s3FileTransferRepository.getFileInputStream(url))
                .thenThrow(new DocumentStorageException("Some error", new RuntimeException("Some error")));

        Assertions.assertThrows(BpmnError.class, () -> s3Adapter.loadAttachment(presignedUrl));
    }

}
