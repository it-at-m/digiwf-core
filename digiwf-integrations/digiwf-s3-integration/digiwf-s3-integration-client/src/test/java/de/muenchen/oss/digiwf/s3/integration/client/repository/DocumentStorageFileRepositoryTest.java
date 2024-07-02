package de.muenchen.oss.digiwf.s3.integration.client.repository;

import de.muenchen.oss.digiwf.s3.integration.client.api.FileApiApi;
import de.muenchen.oss.digiwf.s3.integration.client.exception.DocumentStorageClientErrorException;
import de.muenchen.oss.digiwf.s3.integration.client.exception.DocumentStorageException;
import de.muenchen.oss.digiwf.s3.integration.client.exception.DocumentStorageServerErrorException;
import de.muenchen.oss.digiwf.s3.integration.client.repository.presignedurl.PresignedUrlRepository;
import de.muenchen.oss.digiwf.s3.integration.client.repository.transfer.S3FileTransferRepository;
import de.muenchen.oss.digiwf.s3.integration.client.service.ApiClientFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class DocumentStorageFileRepositoryTest {

    @Mock
    private ApiClientFactory apiClientFactory;

    @Mock
    private PresignedUrlRepository presignedUrlRepository;

    @Mock
    private S3FileTransferRepository s3FileTransferRepository;

    @Mock
    private FileApiApi fileApi;

    private DocumentStorageFileRepository documentStorageFileRepository;

    @BeforeEach
    public void beforeEach() {
        this.documentStorageFileRepository = new DocumentStorageFileRepository(this.presignedUrlRepository, this.s3FileTransferRepository,
                this.apiClientFactory);
        Mockito.reset(this.presignedUrlRepository, this.s3FileTransferRepository, this.fileApi, this.apiClientFactory);
    }

    @Test
    void getFile() throws DocumentStorageException, DocumentStorageClientErrorException, DocumentStorageServerErrorException {
        final String pathToFile = "folder/file.txt";
        final int expireInMinutes = 10;
        final String presignedUrl = "the_presignedUrl";

        Mockito.when(this.presignedUrlRepository.getPresignedUrlGetFile(pathToFile, expireInMinutes, "url")).thenReturn(Mono.just(presignedUrl));
        Mockito.when(this.s3FileTransferRepository.getFile(presignedUrl)).thenReturn(new byte[]{});
        this.documentStorageFileRepository.getFile(pathToFile, expireInMinutes, "url");

        Mockito.verify(this.presignedUrlRepository, Mockito.times(1)).getPresignedUrlGetFile(pathToFile, expireInMinutes, "url");
        Mockito.verify(this.s3FileTransferRepository, Mockito.times(1)).getFile(presignedUrl);
    }

    @Test
    void saveFile() throws DocumentStorageException, DocumentStorageClientErrorException, DocumentStorageServerErrorException {
        final String pathToFile = "folder/file.txt";
        final byte[] file = new byte[]{1, 2, 3, 4, 5, 6, 7};
        final int expireInMinutes = 10;
        final LocalDate endOfLife = LocalDate.now();
        final String presignedUrl = "the_presignedUrl";

        Mockito.when(this.presignedUrlRepository.getPresignedUrlSaveFile(pathToFile, expireInMinutes, endOfLife, "url")).thenReturn(presignedUrl);
        Mockito.doNothing().when(this.s3FileTransferRepository).saveFile(presignedUrl, file);
        this.documentStorageFileRepository.saveFile(pathToFile, file, expireInMinutes, endOfLife, "url");

        Mockito.verify(this.presignedUrlRepository, Mockito.times(1)).getPresignedUrlSaveFile(pathToFile, expireInMinutes, endOfLife, "url");
        Mockito.verify(this.s3FileTransferRepository, Mockito.times(1)).saveFile(presignedUrl, file);
    }

    @Test
    void updateFile() throws DocumentStorageException, DocumentStorageClientErrorException, DocumentStorageServerErrorException {
        final String pathToFile = "folder/file.txt";
        final byte[] file = new byte[]{1, 2, 3, 4, 5, 6, 7};
        final int expireInMinutes = 10;
        final LocalDate endOfLife = LocalDate.now();
        final String presignedUrl = "the_presignedUrl";

        Mockito.when(this.presignedUrlRepository.getPresignedUrlUpdateFile(pathToFile, expireInMinutes, endOfLife, "url")).thenReturn(presignedUrl);
        Mockito.doNothing().when(this.s3FileTransferRepository).updateFile(presignedUrl, file);
        this.documentStorageFileRepository.updateFile(pathToFile, file, expireInMinutes, endOfLife, "url");

        Mockito.verify(this.presignedUrlRepository, Mockito.times(1)).getPresignedUrlUpdateFile(pathToFile, expireInMinutes, endOfLife, "url");
        Mockito.verify(this.s3FileTransferRepository, Mockito.times(1)).updateFile(presignedUrl, file);
    }

    @Test
    void deleteFile() throws DocumentStorageException, DocumentStorageClientErrorException, DocumentStorageServerErrorException {
        final String pathToFile = "folder/file.txt";
        final int expireInMinutes = 10;
        final String presignedUrl = "the_presignedUrl";

        Mockito.when(this.presignedUrlRepository.getPresignedUrlDeleteFile(pathToFile, expireInMinutes, "url")).thenReturn(presignedUrl);
        Mockito.doNothing().when(this.s3FileTransferRepository).deleteFile(presignedUrl);
        this.documentStorageFileRepository.deleteFile(pathToFile, expireInMinutes, "url");

        Mockito.verify(this.presignedUrlRepository, Mockito.times(1)).getPresignedUrlDeleteFile(pathToFile, expireInMinutes, "url");
        Mockito.verify(this.s3FileTransferRepository, Mockito.times(1)).deleteFile(presignedUrl);
    }

}
