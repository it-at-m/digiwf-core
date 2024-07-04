package de.muenchen.oss.digiwf.s3.integration.client.repository;

import de.muenchen.oss.digiwf.s3.integration.client.api.FileApiApi;
import de.muenchen.oss.digiwf.s3.integration.client.exception.DocumentStorageClientErrorException;
import de.muenchen.oss.digiwf.s3.integration.client.exception.DocumentStorageException;
import de.muenchen.oss.digiwf.s3.integration.client.exception.DocumentStorageServerErrorException;
import de.muenchen.oss.digiwf.s3.integration.client.model.FileSizeDto;
import de.muenchen.oss.digiwf.s3.integration.client.repository.presignedurl.PresignedUrlRepository;
import de.muenchen.oss.digiwf.s3.integration.client.repository.transfer.S3FileTransferRepository;
import de.muenchen.oss.digiwf.s3.integration.client.service.ApiClientFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

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

        when(this.presignedUrlRepository.getPresignedUrlGetFile(pathToFile, expireInMinutes, "url")).thenReturn(Mono.just(presignedUrl));
        when(this.s3FileTransferRepository.getFile(presignedUrl)).thenReturn(new byte[] {});
        this.documentStorageFileRepository.getFile(pathToFile, expireInMinutes, "url");

        verify(this.presignedUrlRepository, Mockito.times(1)).getPresignedUrlGetFile(pathToFile, expireInMinutes, "url");
        verify(this.s3FileTransferRepository, Mockito.times(1)).getFile(presignedUrl);
    }

    @Test
    void getFileSize() throws DocumentStorageException, DocumentStorageClientErrorException, DocumentStorageServerErrorException {
        final FileSizeDto fileSizeDto = new FileSizeDto();
        fileSizeDto.setSize(123L);
        final String pathToFile = "path/to/file";

        when(apiClientFactory.getFileApiForDocumentStorageUrl(anyString())).thenReturn(fileApi);
        when(fileApi.getFileSize(anyString())).thenReturn(Mono.just(fileSizeDto));
        final Mono<Long> result = documentStorageFileRepository.getFileSize(pathToFile, "url");
        assertEquals(123L, result.block());
        verify(fileApi, Mockito.times(1)).getFileSize(pathToFile);

        Mockito.reset(this.fileApi, this.apiClientFactory);
        when(apiClientFactory.getFileApiForDocumentStorageUrl(anyString())).thenReturn(fileApi);
        when(fileApi.getFileSize(anyString())).thenThrow(HttpClientErrorException.class);
        assertThrows(DocumentStorageClientErrorException.class, () -> documentStorageFileRepository.getFileSize(pathToFile, "url"));
        verify(fileApi, Mockito.times(1)).getFileSize(pathToFile);

        Mockito.reset(this.fileApi, this.apiClientFactory);
        when(apiClientFactory.getFileApiForDocumentStorageUrl(anyString())).thenReturn(fileApi);
        when(fileApi.getFileSize(anyString())).thenThrow(HttpServerErrorException.class);
        assertThrows(DocumentStorageServerErrorException.class, () -> documentStorageFileRepository.getFileSize(pathToFile, "url"));
        verify(fileApi, Mockito.times(1)).getFileSize(pathToFile);

        Mockito.reset(this.fileApi, this.apiClientFactory);
        when(apiClientFactory.getFileApiForDocumentStorageUrl(anyString())).thenReturn(fileApi);
        when(fileApi.getFileSize(anyString())).thenThrow(RestClientException.class);
        assertThrows(DocumentStorageException.class, () -> documentStorageFileRepository.getFileSize(pathToFile, "url"));
        verify(fileApi, Mockito.times(1)).getFileSize(pathToFile);
    }

    @Test
    void saveFile() throws DocumentStorageException, DocumentStorageClientErrorException, DocumentStorageServerErrorException {
        final String pathToFile = "folder/file.txt";
        final byte[] file = new byte[] { 1, 2, 3, 4, 5, 6, 7 };
        final int expireInMinutes = 10;
        final LocalDate endOfLife = LocalDate.now();
        final String presignedUrl = "the_presignedUrl";

        when(this.presignedUrlRepository.getPresignedUrlSaveFile(pathToFile, expireInMinutes, endOfLife, "url")).thenReturn(presignedUrl);
        doNothing().when(this.s3FileTransferRepository).saveFile(presignedUrl, file);
        this.documentStorageFileRepository.saveFile(pathToFile, file, expireInMinutes, endOfLife, "url");

        verify(this.presignedUrlRepository, Mockito.times(1)).getPresignedUrlSaveFile(pathToFile, expireInMinutes, endOfLife, "url");
        verify(this.s3FileTransferRepository, Mockito.times(1)).saveFile(presignedUrl, file);
    }

    @Test
    void updateFile() throws DocumentStorageException, DocumentStorageClientErrorException, DocumentStorageServerErrorException {
        final String pathToFile = "folder/file.txt";
        final byte[] file = new byte[] { 1, 2, 3, 4, 5, 6, 7 };
        final int expireInMinutes = 10;
        final LocalDate endOfLife = LocalDate.now();
        final String presignedUrl = "the_presignedUrl";

        when(this.presignedUrlRepository.getPresignedUrlUpdateFile(pathToFile, expireInMinutes, endOfLife, "url")).thenReturn(presignedUrl);
        doNothing().when(this.s3FileTransferRepository).updateFile(presignedUrl, file);
        this.documentStorageFileRepository.updateFile(pathToFile, file, expireInMinutes, endOfLife, "url");

        verify(this.presignedUrlRepository, Mockito.times(1)).getPresignedUrlUpdateFile(pathToFile, expireInMinutes, endOfLife, "url");
        verify(this.s3FileTransferRepository, Mockito.times(1)).updateFile(presignedUrl, file);
    }

    @Test
    void updateEndOfLife() throws DocumentStorageException, DocumentStorageClientErrorException, DocumentStorageServerErrorException {
        final String pathToFile = "folder/file.txt";
        final LocalDate endOfLife = LocalDate.now();

        reset(this.presignedUrlRepository, this.s3FileTransferRepository, this.fileApi, this.apiClientFactory);
        when(this.apiClientFactory.getFileApiForDocumentStorageUrl("url")).thenReturn(this.fileApi);
        this.documentStorageFileRepository.updateEndOfLife(pathToFile, endOfLife, "url");
        verify(this.fileApi, Mockito.times(1)).updateEndOfLife(pathToFile, endOfLife);

        reset(this.presignedUrlRepository, this.s3FileTransferRepository, this.fileApi, this.apiClientFactory);
        when(this.apiClientFactory.getFileApiForDocumentStorageUrl("url")).thenReturn(this.fileApi);
        doThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST)).when(this.fileApi).updateEndOfLife(pathToFile, endOfLife);
        assertThrows(DocumentStorageClientErrorException.class,
                () -> this.documentStorageFileRepository.updateEndOfLife(pathToFile, endOfLife, "url"));
        verify(this.fileApi, Mockito.times(1)).updateEndOfLife(pathToFile, endOfLife);

        reset(this.presignedUrlRepository, this.s3FileTransferRepository, this.fileApi, this.apiClientFactory);
        when(this.apiClientFactory.getFileApiForDocumentStorageUrl("url")).thenReturn(this.fileApi);
        doThrow(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR)).when(this.fileApi).updateEndOfLife(pathToFile, endOfLife);
        assertThrows(DocumentStorageServerErrorException.class,
                () -> this.documentStorageFileRepository.updateEndOfLife(pathToFile, endOfLife, "url"));
        verify(this.fileApi, Mockito.times(1)).updateEndOfLife(pathToFile, endOfLife);

        reset(this.presignedUrlRepository, this.s3FileTransferRepository, this.fileApi, this.apiClientFactory);
        when(this.apiClientFactory.getFileApiForDocumentStorageUrl("url")).thenReturn(this.fileApi);
        doThrow(new RestClientException("Something happened")).when(this.fileApi).updateEndOfLife(pathToFile, endOfLife);
        assertThrows(DocumentStorageException.class, () -> this.documentStorageFileRepository.updateEndOfLife(pathToFile, endOfLife, "url"));
        verify(this.fileApi, Mockito.times(1)).updateEndOfLife(pathToFile, endOfLife);
    }

    @Test
    void deleteFile() throws DocumentStorageException, DocumentStorageClientErrorException, DocumentStorageServerErrorException {
        final String pathToFile = "folder/file.txt";
        final int expireInMinutes = 10;
        final String presignedUrl = "the_presignedUrl";

        when(this.presignedUrlRepository.getPresignedUrlDeleteFile(pathToFile, expireInMinutes, "url")).thenReturn(presignedUrl);
        doNothing().when(this.s3FileTransferRepository).deleteFile(presignedUrl);
        this.documentStorageFileRepository.deleteFile(pathToFile, expireInMinutes, "url");

        verify(this.presignedUrlRepository, Mockito.times(1)).getPresignedUrlDeleteFile(pathToFile, expireInMinutes, "url");
        verify(this.s3FileTransferRepository, Mockito.times(1)).deleteFile(presignedUrl);
    }

}
