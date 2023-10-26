package de.muenchen.oss.digiwf.email.integration.adapter.out;

import de.muenchen.oss.digiwf.email.integration.model.FileAttachment;
import de.muenchen.oss.digiwf.email.integration.model.PresignedUrl;
import de.muenchen.oss.digiwf.message.process.api.error.BpmnError;
import de.muenchen.oss.digiwf.s3.integration.client.exception.DocumentStorageClientErrorException;
import de.muenchen.oss.digiwf.s3.integration.client.exception.DocumentStorageException;
import de.muenchen.oss.digiwf.s3.integration.client.exception.DocumentStorageServerErrorException;
import de.muenchen.oss.digiwf.s3.integration.client.repository.transfer.S3FileTransferRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Slf4j
class S3AdapterTest {

    private final S3FileTransferRepository s3FileTransferRepository = mock(S3FileTransferRepository.class);

    private S3Adapter s3Adapter;

    @BeforeEach
    void setup() {
        s3Adapter = new S3Adapter(s3FileTransferRepository);
    }

    @Test
    void testLoadAttachment_DocumentStorageException() throws DocumentStorageException, DocumentStorageClientErrorException, DocumentStorageServerErrorException {
        final String url = "http://localhost:3000/some-file.txt";
        final PresignedUrl presignedUrl = new PresignedUrl(url, "/path/to/some-file.txt", "GET");

        // DocumentStorageException
        when(s3FileTransferRepository.getFile(url))
                .thenThrow(new DocumentStorageException("Some error", new RuntimeException("Some error")));
        Assertions.assertThrows(BpmnError.class, () -> s3Adapter.loadAttachment(presignedUrl));
    }

    @Test
    void testLoadAttachment_Success() throws DocumentStorageException, DocumentStorageClientErrorException, DocumentStorageServerErrorException {
        final Map<String, String> files = Map.of(
          "digiwf_logo.png", "image/png",
          "test-pdf.pdf", "application/pdf",
          "test-word.docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
        );

        for (final Map.Entry<String, String> file : files.entrySet()) {
            try {
                final String path = "files/" + file.getKey();
                final byte[] testFile = new ClassPathResource(path).getInputStream().readAllBytes();
                when(s3FileTransferRepository.getFile(anyString())).thenReturn(testFile);

                final FileAttachment fileAttachment = this.s3Adapter.loadAttachment(
                        new PresignedUrl("http://localhost:9000/" + file, path, "GET")
                );

                Assertions.assertTrue(Arrays.equals(testFile, fileAttachment.getFile().getInputStream().readAllBytes()));
                Assertions.assertEquals(file.getKey(), fileAttachment.getFileName());
                Assertions.assertEquals(file.getValue(), fileAttachment.getFile().getContentType());
            } catch (final IOException e) {
                log.warn("Could not read file: {}", file);
                Assertions.fail(e);
            }
        }
    }

}
