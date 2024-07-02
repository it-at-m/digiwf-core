package de.muenchen.oss.digiwf.s3.integration.client.repository;

import de.muenchen.oss.digiwf.s3.integration.client.exception.DocumentStorageClientErrorException;
import de.muenchen.oss.digiwf.s3.integration.client.exception.DocumentStorageException;
import de.muenchen.oss.digiwf.s3.integration.client.exception.DocumentStorageServerErrorException;
import de.muenchen.oss.digiwf.s3.integration.client.repository.presignedurl.PresignedUrlRepository;
import de.muenchen.oss.digiwf.s3.integration.client.repository.transfer.S3FileTransferRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.io.InputStream;

@Slf4j
@Repository
@RequiredArgsConstructor
public class DocumentStorageFileRepository {

    private final PresignedUrlRepository presignedUrlRepository;

    private final S3FileTransferRepository s3FileTransferRepository;

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
