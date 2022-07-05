package io.muenchendigital.digiwf.s3.integration.client.repository;

import io.muenchendigital.digiwf.s3.integration.client.exception.DocumentStorageClientErrorException;
import io.muenchendigital.digiwf.s3.integration.client.exception.DocumentStorageException;
import io.muenchendigital.digiwf.s3.integration.client.exception.DocumentStorageServerErrorException;
import io.muenchendigital.digiwf.s3.integration.client.exception.PropertyNotSetException;
import io.muenchendigital.digiwf.s3.integration.client.service.ApiClientFactory;
import io.muenchendigital.digiwf.s3.integration.gen.api.FolderApiApi;
import io.muenchendigital.digiwf.s3.integration.gen.model.FilesInFolderDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class DocumentStorageFolderRepository {

    private final ApiClientFactory apiClientFactory;

    /**
     * Deletes the folder with all containing files on document storage.
     *
     * @param pathToFolder    which defines the folder in the document storage.
     * @throws DocumentStorageClientErrorException if the problem is with the client.
     * @throws DocumentStorageServerErrorException if the problem is with the document storage.
     * @throws DocumentStorageException            if the problem cannot be assigned directly to the document storage.
     * @throws PropertyNotSetException             if the property "io.muenchendigital.digiwf.s3.client.defaultDocumentStorageUrl" is not set.
     */
    public void deleteFolder(final String pathToFolder) throws DocumentStorageClientErrorException, DocumentStorageServerErrorException, DocumentStorageException, PropertyNotSetException {
        this.deleteFolder(
                pathToFolder,
                this.apiClientFactory.getDefaultDocumentStorageUrl()
        );
    }

    /**
     * Deletes the folder with all containing files on document storage.
     *
     * @param pathToFolder    which defines the folder in the document storage.
     * @param documentStorageUrl to define to which document storage the request goes.
     * @throws DocumentStorageClientErrorException if the problem is with the client.
     * @throws DocumentStorageServerErrorException if the problem is with the document storage.
     * @throws DocumentStorageException            if the problem cannot be assigned directly to the document storage.
     */
    public void deleteFolder(final String pathToFolder, final String documentStorageUrl) throws DocumentStorageClientErrorException, DocumentStorageServerErrorException, DocumentStorageException {
        try {
            final FolderApiApi folderApi = this.apiClientFactory.getFolderApiForDocumentStorageUrl(documentStorageUrl);
            folderApi.delete(pathToFolder);
        } catch (final HttpClientErrorException exception) {
            final String message = String.format("The request to delete a folder failed %s.", exception.getStatusCode());
            log.error(message);
            throw new DocumentStorageClientErrorException(message, exception);
        } catch (final HttpServerErrorException exception) {
            final String message = String.format("The request to delete a folder failed %s.", exception.getStatusCode());
            log.error(message);
            throw new DocumentStorageServerErrorException(message, exception);
        } catch (final RestClientException exception) {
            final String message = String.format("The request to delete a folder failed.");
            log.error(message);
            throw new DocumentStorageException(message, exception);
        }
    }

    /**
     * Returns all files within a folder given in the parameter from document storage.
     *
     * @param pathToFolder    which defines the folder in the document storage.
     * @throws DocumentStorageClientErrorException if the problem is with the client.
     * @throws DocumentStorageServerErrorException if the problem is with the document storage.
     * @throws DocumentStorageException            if the problem cannot be assigned directly to the document storage.
     * @throws PropertyNotSetException             if the property "io.muenchendigital.digiwf.s3.client.defaultDocumentStorageUrl" is not set.
     */
    public List<String> getAllFilesInFolderRecursively(final String pathToFolder) throws DocumentStorageClientErrorException, DocumentStorageServerErrorException, DocumentStorageException, PropertyNotSetException {
        return this.getAllFilesInFolderRecursively(
                pathToFolder,
                this.apiClientFactory.getDefaultDocumentStorageUrl()
        );
    }

    /**
     * Returns all files within a folder given in the parameter from document storage.
     *
     * @param pathToFolder    which defines the folder in the document storage.
     * @param documentStorageUrl to define to which document storage the request goes.
     * @throws DocumentStorageClientErrorException if the problem is with the client.
     * @throws DocumentStorageServerErrorException if the problem is with the document storage.
     * @throws DocumentStorageException            if the problem cannot be assigned directly to the document storage.
     */
    public List<String> getAllFilesInFolderRecursively(final String pathToFolder, final String documentStorageUrl) throws DocumentStorageClientErrorException, DocumentStorageServerErrorException, DocumentStorageException {
        try {
            final FolderApiApi folderApi = this.apiClientFactory.getFolderApiForDocumentStorageUrl(documentStorageUrl);
            final FilesInFolderDto filesInFolderDto = folderApi.getAllFilesInFolderRecursively(pathToFolder);
            return filesInFolderDto.getPathToFiles();
        } catch (final HttpClientErrorException exception) {
            final String message = String.format("The request to get all files within a folder failed %s.", exception.getStatusCode());
            log.error(message);
            throw new DocumentStorageClientErrorException(message, exception);
        } catch (final HttpServerErrorException exception) {
            final String message = String.format("The request to get all files within a folder failed %s.", exception.getStatusCode());
            log.error(message);
            throw new DocumentStorageServerErrorException(message, exception);
        } catch (final RestClientException exception) {
            final String message = String.format("The request to get all files within a folder failed.");
            log.error(message);
            throw new DocumentStorageException(message, exception);
        }
    }

}
