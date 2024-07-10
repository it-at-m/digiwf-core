package de.muenchen.oss.digiwf.email.integration.adapter.out.s3;

import de.muenchen.oss.digiwf.email.integration.domain.model.PresignedUrl;
import de.muenchen.oss.digiwf.email.model.FileAttachment;
import de.muenchen.oss.digiwf.message.process.api.error.BpmnError;
import de.muenchen.oss.digiwf.s3.integration.client.exception.DocumentStorageClientErrorException;
import de.muenchen.oss.digiwf.s3.integration.client.exception.DocumentStorageException;
import de.muenchen.oss.digiwf.s3.integration.client.exception.DocumentStorageServerErrorException;
import de.muenchen.oss.digiwf.s3.integration.client.repository.DocumentStorageFileRepository;
import de.muenchen.oss.digiwf.s3.integration.client.repository.DocumentStorageFolderRepository;
import de.muenchen.oss.digiwf.s3.integration.client.repository.transfer.S3FileTransferRepository;
import de.muenchen.oss.digiwf.s3.integration.client.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.unit.DataSize;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Slf4j
class S3AdapterTest {

    private final S3FileTransferRepository s3FileTransferRepository = mock(S3FileTransferRepository.class);
    private final DocumentStorageFileRepository documentStorageFileRepository = mock(DocumentStorageFileRepository.class);
    private final DocumentStorageFolderRepository documentStorageFolderRepository = mock(DocumentStorageFolderRepository.class);
    private final FileService fileService = new FileService(null, DataSize.ofMegabytes(50), DataSize.ofMegabytes(110));

    private S3Adapter s3Adapter;

    @BeforeEach
    void setup() {
        s3Adapter = new S3Adapter(s3FileTransferRepository, documentStorageFileRepository, documentStorageFolderRepository, fileService);
    }

    @Test
    void testLoadAttachment_DocumentStorageException() throws DocumentStorageException, DocumentStorageClientErrorException, DocumentStorageServerErrorException {
        final String url = "http://localhost:3000/some-file.txt";
        final PresignedUrl presignedUrl = new PresignedUrl(url, "/path/to/some-file.txt", "GET");

        // DocumentStorageException
        when(s3FileTransferRepository.getFile(url))
                .thenThrow(new DocumentStorageException("Some error", new RuntimeException("Some error")));
        assertThatThrownBy(() -> s3Adapter.loadAttachment(presignedUrl)).isInstanceOf(BpmnError.class);
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

                assertThat(Arrays.equals(testFile, fileAttachment.getFile().getInputStream().readAllBytes())).isTrue();
                assertThat(file.getKey()).isEqualTo(fileAttachment.getFileName());
                assertThat(file.getValue()).isEqualTo(fileAttachment.getFile().getContentType());
            } catch (final IOException e) {
                log.warn("Could not read file: {}", file);
                fail(e.getMessage());
            }
        }
    }

}
