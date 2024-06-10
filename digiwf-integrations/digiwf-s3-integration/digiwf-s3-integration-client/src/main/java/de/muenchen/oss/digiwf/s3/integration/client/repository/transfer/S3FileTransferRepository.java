package de.muenchen.oss.digiwf.s3.integration.client.repository.transfer;

import de.muenchen.oss.digiwf.s3.integration.client.exception.DocumentStorageClientErrorException;
import de.muenchen.oss.digiwf.s3.integration.client.exception.DocumentStorageException;
import de.muenchen.oss.digiwf.s3.integration.client.exception.DocumentStorageServerErrorException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

@Slf4j
@Repository
@RequiredArgsConstructor
public class S3FileTransferRepository {

    private static final String REQUEST_FAILED_WITH_STATUS_CODE = "The presigned url request failed with http status %s.";
    private static final String REQUEST_FAILED = "The presigned url request failed.";

    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * Gets a file from document storage using the presignedURL.
     *
     * @param presignedUrl to get the file.
     * @return the file.
     * @throws DocumentStorageClientErrorException if the problem is with the client.
     * @throws DocumentStorageServerErrorException if the problem is with the S3 storage.
     * @throws DocumentStorageException            if the problem cannot be assigned to either the client or the S3 storage.
     */
    public byte[] getFile(final String presignedUrl) throws DocumentStorageClientErrorException, DocumentStorageServerErrorException, DocumentStorageException {
        try {
            val headers = new HttpHeaders();
            final HttpEntity<Void> httpEntity = new HttpEntity<>(headers);
            /*
             * Using the RestTemplate without any authorization.
             * The presigned URL contains any authorization against the S3 storage.
             */
            final ResponseEntity<byte[]> responseEntity = restTemplate.exchange(
                    URI.create(presignedUrl),
                    HttpMethod.GET,
                    httpEntity,
                    byte[].class
            );
            return responseEntity.getBody();
        } catch (final HttpClientErrorException exception) {
            final String message = String.format(REQUEST_FAILED_WITH_STATUS_CODE, exception.getStatusCode());
            log.error(message);
            throw new DocumentStorageClientErrorException(message, exception);
        } catch (final HttpServerErrorException exception) {
            final String message = String.format(REQUEST_FAILED_WITH_STATUS_CODE, exception.getStatusCode());
            log.error(message);
            throw new DocumentStorageServerErrorException(message, exception);
        } catch (final RestClientException exception) {
            final String message = REQUEST_FAILED;
            log.error(message);
            throw new DocumentStorageException(message, exception);
        }
    }

    /**
     * Gets an InputStream for file from document storage using the presignedURL.
     *
     * @param presignedUrl to get the file.
     * @return the InputStream for the file.
     * @throws DocumentStorageException if the problem cannot be assigned to either the client or the S3 storage.
     */
    public InputStream getFileInputStream(final String presignedUrl) throws DocumentStorageException {
        try {
            val urlResource = new UrlResource(presignedUrl);
            return urlResource.getInputStream();
        } catch (final IOException exception) {
            final String message = REQUEST_FAILED;
            log.error(message);
            throw new DocumentStorageException(message, exception);
        }
    }

    /**
     * Saves the file to document storage using the presignedURL.
     *
     * @param presignedUrl to save the file.
     * @param file         to save.
     * @throws DocumentStorageClientErrorException if the problem is with the client.
     * @throws DocumentStorageServerErrorException if the problem is with the S3 storage.
     * @throws DocumentStorageException            if the problem cannot be assigned to either the client or the S3 storage.
     */
    public void saveFile(final String presignedUrl, final byte[] file)
            throws DocumentStorageClientErrorException, DocumentStorageServerErrorException, DocumentStorageException {
        try {
            val headers = new HttpHeaders();
            final HttpEntity<byte[]> fileHttpEntity = new HttpEntity<>(file, headers);
            /*
             * Using the RestTemplate without any authorization.
             * The presigned URL contains any authorization against the S3 storage.
             */
            restTemplate.exchange(
                    URI.create(presignedUrl),
                    HttpMethod.PUT,
                    fileHttpEntity,
                    Void.class
            );
        } catch (final HttpClientErrorException exception) {
            final String message = String.format(REQUEST_FAILED_WITH_STATUS_CODE, exception.getStatusCode());
            log.error(message);
            throw new DocumentStorageClientErrorException(message, exception);
        } catch (final HttpServerErrorException exception) {
            final String message = String.format(REQUEST_FAILED_WITH_STATUS_CODE, exception.getStatusCode());
            log.error(message);
            throw new DocumentStorageServerErrorException(message, exception);
        } catch (final RestClientException exception) {
            final String message = REQUEST_FAILED;
            log.error(message);
            throw new DocumentStorageException(message, exception);
        }
    }

    /**
     * Saves the file InputStream to document storage using the presignedURL.
     *
     * @param presignedUrl to save the file.
     * @param file         to save.
     * @throws DocumentStorageClientErrorException if the problem is with the client.
     * @throws DocumentStorageServerErrorException if the problem is with the S3 storage.
     * @throws DocumentStorageException            if the problem cannot be assigned to either the client or the S3 storage.
     */
    public void saveFileInputStream(final String presignedUrl, final InputStream file)
            throws DocumentStorageClientErrorException, DocumentStorageServerErrorException, DocumentStorageException {
        try {
            val headers = new HttpHeaders();
            final HttpEntity<Resource> fileHttpEntity = new HttpEntity<>(new InputStreamResource(file), headers);
            /*
             * Using the RestTemplate without any authorization.
             * The presigned URL contains any authorization against the S3 storage.
             */
            restTemplate.exchange(
                    URI.create(presignedUrl),
                    HttpMethod.PUT,
                    fileHttpEntity,
                    Void.class
            );
        } catch (final HttpClientErrorException exception) {
            final String message = String.format(REQUEST_FAILED_WITH_STATUS_CODE, exception.getStatusCode());
            log.error(message);
            throw new DocumentStorageClientErrorException(message, exception);
        } catch (final HttpServerErrorException exception) {
            final String message = String.format(REQUEST_FAILED_WITH_STATUS_CODE, exception.getStatusCode());
            log.error(message);
            throw new DocumentStorageServerErrorException(message, exception);
        } catch (final RestClientException exception) {
            final String message = REQUEST_FAILED;
            log.error(message);
            throw new DocumentStorageException(message, exception);
        }
    }

    /**
     * Updates the file in the document storage using the presignedURL.
     *
     * @param presignedUrl to update the file.
     * @param file         which overwrites the file in the document storage.
     * @throws DocumentStorageClientErrorException if the problem is with the client.
     * @throws DocumentStorageServerErrorException if the problem is with the S3 storage.
     * @throws DocumentStorageException            if the problem cannot be assigned to either the client or the S3 storage.
     */
    public void updateFile(final String presignedUrl, final byte[] file)
            throws DocumentStorageClientErrorException, DocumentStorageServerErrorException, DocumentStorageException {
        saveFile(presignedUrl, file);
    }

    /**
     * Updates the file InputStream in the document storage using the presignedURL.
     *
     * @param presignedUrl to update the file.
     * @param file         which overwrites the file in the document storage.
     * @throws DocumentStorageClientErrorException if the problem is with the client.
     * @throws DocumentStorageServerErrorException if the problem is with the S3 storage.
     * @throws DocumentStorageException            if the problem cannot be assigned to either the client or the S3 storage.
     */
    public void updateFileInputStream(final String presignedUrl, final InputStream file)
            throws DocumentStorageClientErrorException, DocumentStorageServerErrorException, DocumentStorageException {
        saveFileInputStream(presignedUrl, file);
    }

    /**
     * Deletes a file from document storage using the presignedURL.
     *
     * @param presignedUrl to delete the file.
     * @throws DocumentStorageClientErrorException if the problem is with the client.
     * @throws DocumentStorageServerErrorException if the problem is with the S3 storage.
     * @throws DocumentStorageException            if the problem cannot be assigned to either the client or the S3 storage.
     */
    public void deleteFile(final String presignedUrl)
            throws DocumentStorageClientErrorException, DocumentStorageServerErrorException, DocumentStorageException {
        try {
            val headers = new HttpHeaders();
            final HttpEntity<Void> fileHttpEntity = new HttpEntity<>(headers);
            /*
             * Using the RestTemplate without any authorization.
             * The presigned URL contains any authorization against the S3 storage.
             */
            restTemplate.exchange(
                    URI.create(presignedUrl),
                    HttpMethod.DELETE,
                    fileHttpEntity,
                    Void.class
            );
        } catch (final HttpClientErrorException exception) {
            final String message = String.format(REQUEST_FAILED_WITH_STATUS_CODE, exception.getStatusCode());
            log.error(message);
            throw new DocumentStorageClientErrorException(message, exception);
        } catch (final HttpServerErrorException exception) {
            final String message = String.format(REQUEST_FAILED_WITH_STATUS_CODE, exception.getStatusCode());
            log.error(message);
            throw new DocumentStorageServerErrorException(message, exception);
        } catch (final RestClientException exception) {
            final String message = REQUEST_FAILED;
            log.error(message);
            throw new DocumentStorageException(message, exception);
        }
    }

}
