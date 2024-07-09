package de.muenchen.oss.digiwf.s3.integration.client.repository;

import de.muenchen.oss.digiwf.s3.integration.client.api.FileApiApi;
import de.muenchen.oss.digiwf.s3.integration.client.exception.DocumentStorageClientErrorException;
import de.muenchen.oss.digiwf.s3.integration.client.exception.DocumentStorageException;
import de.muenchen.oss.digiwf.s3.integration.client.exception.DocumentStorageServerErrorException;
import de.muenchen.oss.digiwf.s3.integration.client.model.FileSizeDto;
import de.muenchen.oss.digiwf.s3.integration.client.repository.presignedurl.PresignedUrlRepository;
import de.muenchen.oss.digiwf.s3.integration.client.repository.transfer.S3FileTransferRepository;
import de.muenchen.oss.digiwf.s3.integration.client.service.ApiClientFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import reactor.core.publisher.Mono;

import java.io.InputStream;

@Slf4j
@Repository
@RequiredArgsConstructor
public class DocumentStorageFileRepository {

    private final PresignedUrlRepository presignedUrlRepository;

    private final S3FileTransferRepository s3FileTransferRepository;

    private final ApiClientFactory apiClientFactory;

    /**
     * Gets the file specified in the parameter from the document storage.
     *
     * @param pathToFile         defines the path to the file.
     * @param expireInMinutes    the expiration time of the presignedURL in minutes.
     * @param documentStorageUrl to define to which document storage the request goes.
     * @return the file.
     * @throws DocumentStorageClientErrorException if the problem is with the client.
     * @throws DocumentStorageServerErrorException if the problem is with the S3 storage or document storage.
     * @throws DocumentStorageException            if the problem cannot be assigned to either the client or the S3 storage or the document storage.
     */
    public byte[] getFile(final String pathToFile, final int expireInMinutes, final String documentStorageUrl)
            throws DocumentStorageException, DocumentStorageClientErrorException, DocumentStorageServerErrorException {
        final Mono<String> presignedUrl = this.presignedUrlRepository.getPresignedUrlGetFile(pathToFile, expireInMinutes, documentStorageUrl);
        return this.s3FileTransferRepository.getFile(presignedUrl.block());
    }

    /**
     * Retrieves the file size of a file specified in the parameter from the document storage.
     *
     * @param pathToFile         defines the path to the file.
     * @param documentStorageUrl defines to which document storage the request goes.
     * @return the file size.
     * @throws DocumentStorageClientErrorException if the problem is with the client.
     * @throws DocumentStorageServerErrorException if the problem is with the S3 storage or document storage.
     * @throws DocumentStorageException            if the problem cannot be assigned to either the client or the S3 storage or the document storage.
     */
    public Mono<Long> getFileSize(final String pathToFile, final String documentStorageUrl)
            throws DocumentStorageClientErrorException, DocumentStorageServerErrorException, DocumentStorageException {
        try {
            final FileApiApi fileApi = this.apiClientFactory.getFileApiForDocumentStorageUrl(documentStorageUrl);
            return fileApi.getFileSize(pathToFile).mapNotNull(FileSizeDto::getFileSize);
        } catch (final HttpClientErrorException exception) {
            final String message = String.format("The request to get file size failed %s.", exception.getStatusCode());
            log.error(message);
            throw new DocumentStorageClientErrorException(message, exception);
        } catch (final HttpServerErrorException exception) {
            final String message = String.format("The request to get file size failed %s.", exception.getStatusCode());
            log.error(message);
            throw new DocumentStorageServerErrorException(message, exception);
        } catch (final RestClientException exception) {
            final String message = "The request to get a file size failed.";
            log.error(message);
            throw new DocumentStorageException(message, exception);
        }
    }

    /**
     * Gets an InputStream for the file specified in the parameter from the document storage.
     *
     * @param pathToFile         defines the path to the file.
     * @param expireInMinutes    the expiration time of the presignedURL in minutes.
     * @param documentStorageUrl to define to which document storage the request goes.
     * @return the InputStream for the file.
     * @throws DocumentStorageClientErrorException if the problem is with the client.
     * @throws DocumentStorageServerErrorException if the problem is with the S3 storage or document storage.
     * @throws DocumentStorageException            if the problem cannot be assigned to either the client or the S3 storage or the document storage.
     */
    public InputStream getFileInputStream(final String pathToFile, final int expireInMinutes, final String documentStorageUrl)
            throws DocumentStorageException, DocumentStorageClientErrorException, DocumentStorageServerErrorException {
        final Mono<String> presignedUrl = this.presignedUrlRepository.getPresignedUrlGetFile(pathToFile, expireInMinutes, documentStorageUrl);
        return this.s3FileTransferRepository.getFileInputStream(presignedUrl.block());
    }

    /**
     * Saves the file specified in the parameter to the document storage.
     *
     * @param pathToFile         defines the path to the file.
     * @param file               to save.
     * @param expireInMinutes    the expiration time of the presignedURL in minutes.
     * @param documentStorageUrl to define to which document storage the request goes.
     * @throws DocumentStorageClientErrorException if the problem is with the client.
     * @throws DocumentStorageServerErrorException if the problem is with the S3 storage or document storage.
     * @throws DocumentStorageException            if the problem cannot be assigned to either the client or the S3 storage or the document storage.
     */
    public void saveFile(final String pathToFile, final byte[] file, final int expireInMinutes,
                         final String documentStorageUrl) throws DocumentStorageException, DocumentStorageClientErrorException, DocumentStorageServerErrorException {
        final String presignedUrl = this.presignedUrlRepository.getPresignedUrlSaveFile(pathToFile, expireInMinutes, documentStorageUrl);
        this.s3FileTransferRepository.saveFile(presignedUrl, file);
    }

    /**
     * Saves the file specified in the parameter to the document storage.
     *
     * @param pathToFile         defines the path to the file.
     * @param file               to save.
     * @param expireInMinutes    the expiration time of the presignedURL in minutes.
     * @param documentStorageUrl to define to which document storage the request goes.
     * @throws DocumentStorageClientErrorException if the problem is with the client.
     * @throws DocumentStorageServerErrorException if the problem is with the S3 storage or document storage.
     * @throws DocumentStorageException            if the problem cannot be assigned to either the client or the S3 storage or the document storage.
     */
    public void saveFileInputStream(final String pathToFile, final InputStream file, final int expireInMinutes,
                                    final String documentStorageUrl) throws DocumentStorageException, DocumentStorageClientErrorException, DocumentStorageServerErrorException {
        final String presignedUrl = this.presignedUrlRepository.getPresignedUrlSaveFile(pathToFile, expireInMinutes, documentStorageUrl);
        this.s3FileTransferRepository.saveFileInputStream(presignedUrl, file);
    }

    /**
     * Updates the file specified in the parameter to the document storage.
     *
     * @param pathToFile         defines the path to the file.
     * @param file               which overwrites the file in the document storage.
     * @param expireInMinutes    the expiration time of the presignedURL in minutes.
     * @param documentStorageUrl to define to which document storage the request goes.
     * @throws DocumentStorageClientErrorException if the problem is with the client.
     * @throws DocumentStorageServerErrorException if the problem is with the S3 storage or document storage.
     * @throws DocumentStorageException            if the problem cannot be assigned to either the client or the S3 storage or the document storage.
     */
    public void updateFile(final String pathToFile, final byte[] file, final int expireInMinutes,
                           final String documentStorageUrl) throws DocumentStorageException, DocumentStorageClientErrorException, DocumentStorageServerErrorException {
        final String presignedUrl = this.presignedUrlRepository.getPresignedUrlUpdateFile(pathToFile, expireInMinutes, documentStorageUrl);
        this.s3FileTransferRepository.updateFile(presignedUrl, file);
    }

    /**
     * Updates the file specified in the parameter withinq the document storage.
     *
     * @param pathToFile         defines the path to the file.
     * @param file               which overwrites the file in the document storage.
     * @param expireInMinutes    the expiration time of the presignedURL in minutes.
     * @param documentStorageUrl to define to which document storage the request goes.
     * @throws DocumentStorageClientErrorException if the problem is with the client.
     * @throws DocumentStorageServerErrorException if the problem is with the S3 storage or document storage.
     * @throws DocumentStorageException            if the problem cannot be assigned to either the client or the S3 storage or the document storage.
     */
    public void updateFileInputStream(final String pathToFile, final InputStream file, final int expireInMinutes,
                                      final String documentStorageUrl) throws DocumentStorageException, DocumentStorageClientErrorException, DocumentStorageServerErrorException {
        final String presignedUrl = this.presignedUrlRepository.getPresignedUrlUpdateFile(pathToFile, expireInMinutes, documentStorageUrl);
        this.s3FileTransferRepository.updateFileInputStream(presignedUrl, file);
    }

    /**
     * Deletes the file specified in the parameter from the document storage.
     *
     * @param pathToFile         defines the path to the file.
     * @param expireInMinutes    the expiration time of the presignedURL in minutes.
     * @param documentStorageUrl to define to which document storage the request goes.
     * @throws DocumentStorageClientErrorException if the problem is with the client.
     * @throws DocumentStorageServerErrorException if the problem is with the S3 storage or document storage.
     * @throws DocumentStorageException            if the problem cannot be assigned to either the client or the S3 storage or the document storage.
     */
    public void deleteFile(final String pathToFile, final int expireInMinutes, final String documentStorageUrl)
            throws DocumentStorageException, DocumentStorageClientErrorException, DocumentStorageServerErrorException {
        final String presignedUrl = this.presignedUrlRepository.getPresignedUrlDeleteFile(pathToFile, expireInMinutes, documentStorageUrl);
        this.s3FileTransferRepository.deleteFile(presignedUrl);
    }

}
