package de.muenchen.oss.digiwf.s3.integration.client.repository;

import de.muenchen.oss.digiwf.s3.integration.client.api.FolderApiApi;
import de.muenchen.oss.digiwf.s3.integration.client.exception.DocumentStorageClientErrorException;
import de.muenchen.oss.digiwf.s3.integration.client.exception.DocumentStorageException;
import de.muenchen.oss.digiwf.s3.integration.client.exception.DocumentStorageServerErrorException;
import de.muenchen.oss.digiwf.s3.integration.client.model.FileSizesInFolderDto;
import de.muenchen.oss.digiwf.s3.integration.client.model.FilesInFolderDto;
import de.muenchen.oss.digiwf.s3.integration.client.service.ApiClientFactory;
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

import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class DocumentStorageFolderRepositoryTest {

    @Mock
    private ApiClientFactory apiClientFactory;

    @Mock
    private FolderApiApi folderApi;

    private DocumentStorageFolderRepository documentStorageFolderRepository;

    @BeforeEach
    public void beforeEach() {
        this.documentStorageFolderRepository = new DocumentStorageFolderRepository(this.apiClientFactory);
    }

    @Test
    void deleteFolder() throws DocumentStorageException, DocumentStorageClientErrorException, DocumentStorageServerErrorException {
        final String pathToFolder = "folder";

        Mockito.reset(this.folderApi, this.apiClientFactory);
        when(this.apiClientFactory.getFolderApiForDocumentStorageUrl("url")).thenReturn(this.folderApi);
        when(this.folderApi.delete(pathToFolder)).thenReturn(Mono.empty());
        this.documentStorageFolderRepository.deleteFolder(pathToFolder, "url");
        verify(this.folderApi, times(1)).delete(pathToFolder);

        Mockito.reset(this.folderApi, this.apiClientFactory);
        when(this.apiClientFactory.getFolderApiForDocumentStorageUrl("url")).thenReturn(this.folderApi);
        Mockito.doThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST)).when(this.folderApi).delete(pathToFolder);
        assertThrows(DocumentStorageClientErrorException.class, () -> this.documentStorageFolderRepository.deleteFolder(pathToFolder, "url"));
        verify(this.folderApi, times(1)).delete(pathToFolder);

        Mockito.reset(this.folderApi, this.apiClientFactory);
        when(this.apiClientFactory.getFolderApiForDocumentStorageUrl("url")).thenReturn(this.folderApi);
        Mockito.doThrow(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR)).when(this.folderApi).delete(pathToFolder);
        assertThrows(DocumentStorageServerErrorException.class, () -> this.documentStorageFolderRepository.deleteFolder(pathToFolder, "url"));
        verify(this.folderApi, times(1)).delete(pathToFolder);

        Mockito.reset(this.folderApi, this.apiClientFactory);
        when(this.apiClientFactory.getFolderApiForDocumentStorageUrl("url")).thenReturn(this.folderApi);
        Mockito.doThrow(new RestClientException("Something happened")).when(this.folderApi).delete(pathToFolder);
        assertThrows(DocumentStorageException.class, () -> this.documentStorageFolderRepository.deleteFolder(pathToFolder, "url"));
        verify(this.folderApi, times(1)).delete(pathToFolder);
    }

    @Test
    void getAllFilesInFolderRecursively() throws DocumentStorageException, DocumentStorageClientErrorException, DocumentStorageServerErrorException {
        final String pathToFolder = "folder";

        final FilesInFolderDto filesInFolderDto = new FilesInFolderDto();
        filesInFolderDto.setPathToFiles(Set.of("folder/file.txt"));

        Mockito.reset(this.folderApi, this.apiClientFactory);
        when(this.apiClientFactory.getFolderApiForDocumentStorageUrl("url")).thenReturn(this.folderApi);
        when(this.folderApi.getAllFilesInFolderRecursively(pathToFolder)).thenReturn(Mono.just(filesInFolderDto));
        this.documentStorageFolderRepository.getAllFilesInFolderRecursively(pathToFolder, "url");
        verify(this.folderApi, times(1)).getAllFilesInFolderRecursively(pathToFolder);

        Mockito.reset(this.folderApi, this.apiClientFactory);
        when(this.apiClientFactory.getFolderApiForDocumentStorageUrl("url")).thenReturn(this.folderApi);
        Mockito.doThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST)).when(this.folderApi).getAllFilesInFolderRecursively(pathToFolder);
        assertThrows(DocumentStorageClientErrorException.class, () -> this.documentStorageFolderRepository.getAllFilesInFolderRecursively(pathToFolder, "url"));
        verify(this.folderApi, times(1)).getAllFilesInFolderRecursively(pathToFolder);

        Mockito.reset(this.folderApi, this.apiClientFactory);
        when(this.apiClientFactory.getFolderApiForDocumentStorageUrl("url")).thenReturn(this.folderApi);
        Mockito.doThrow(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR)).when(this.folderApi).getAllFilesInFolderRecursively(pathToFolder);
        assertThrows(DocumentStorageServerErrorException.class, () -> this.documentStorageFolderRepository.getAllFilesInFolderRecursively(pathToFolder, "url"));
        verify(this.folderApi, times(1)).getAllFilesInFolderRecursively(pathToFolder);

        Mockito.reset(this.folderApi, this.apiClientFactory);
        when(this.apiClientFactory.getFolderApiForDocumentStorageUrl("url")).thenReturn(this.folderApi);
        Mockito.doThrow(new RestClientException("Something happened")).when(this.folderApi).getAllFilesInFolderRecursively(pathToFolder);
        assertThrows(DocumentStorageException.class, () -> this.documentStorageFolderRepository.getAllFilesInFolderRecursively(pathToFolder, "url"));
        verify(this.folderApi, times(1)).getAllFilesInFolderRecursively(pathToFolder);
    }

    @Test
    void testGetAllFileSizesInFolderRecursively()
            throws DocumentStorageException, DocumentStorageClientErrorException, DocumentStorageServerErrorException {
        final String pathToFolder = "path/to/folder";
        FileSizesInFolderDto fileSizesInFolderDto = new FileSizesInFolderDto();
        fileSizesInFolderDto.setFileSizes(Map.of("file1", 100L, "file2", 200L));
        when(apiClientFactory.getFolderApiForDocumentStorageUrl(anyString())).thenReturn(folderApi);
        when(folderApi.getAllFileSizesInFolderRecursively(anyString())).thenReturn(Mono.just(fileSizesInFolderDto));

        Mono<Map<String, Long>> result = documentStorageFolderRepository.getAllFileSizesInFolderRecursively(pathToFolder, "url");
        assertEquals(Map.of("file1", 100L, "file2", 200L), result.block());
        verify(this.folderApi, times(1)).getAllFileSizesInFolderRecursively(pathToFolder);

        Mockito.reset(this.folderApi, this.apiClientFactory);
        when(apiClientFactory.getFolderApiForDocumentStorageUrl(anyString())).thenReturn(folderApi);
        when(folderApi.getAllFileSizesInFolderRecursively(anyString())).thenThrow(HttpClientErrorException.class);
        assertThrows(DocumentStorageClientErrorException.class, () -> documentStorageFolderRepository.getAllFileSizesInFolderRecursively(pathToFolder, "url"));
        verify(this.folderApi, times(1)).getAllFileSizesInFolderRecursively(pathToFolder);

        Mockito.reset(this.folderApi, this.apiClientFactory);
        when(apiClientFactory.getFolderApiForDocumentStorageUrl(anyString())).thenReturn(folderApi);
        when(folderApi.getAllFileSizesInFolderRecursively(anyString())).thenThrow(HttpServerErrorException.class);
        assertThrows(DocumentStorageServerErrorException.class, () -> documentStorageFolderRepository.getAllFileSizesInFolderRecursively(pathToFolder, "url"));
        verify(this.folderApi, times(1)).getAllFileSizesInFolderRecursively(pathToFolder);

        Mockito.reset(this.folderApi, this.apiClientFactory);
        when(apiClientFactory.getFolderApiForDocumentStorageUrl(anyString())).thenReturn(folderApi);
        when(folderApi.getAllFileSizesInFolderRecursively(anyString())).thenThrow(RestClientException.class);
        assertThrows(DocumentStorageException.class, () -> documentStorageFolderRepository.getAllFileSizesInFolderRecursively(pathToFolder, "url"));
        verify(this.folderApi, times(1)).getAllFileSizesInFolderRecursively(pathToFolder);
    }

}
