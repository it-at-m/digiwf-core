package io.muenchendigital.digiwf.s3.integration.infrastructure.repository;

import io.minio.BucketExistsArgs;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.ListObjectsArgs;
import io.minio.MinioClient;
import io.minio.RemoveObjectArgs;
import io.minio.Result;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.MinioException;
import io.minio.errors.ServerException;
import io.minio.errors.XmlParserException;
import io.minio.http.Method;
import io.minio.messages.Item;
import io.muenchendigital.digiwf.s3.integration.infrastructure.exception.S3AccessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.IteratorUtils;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
public class S3Repository {

    private final String bucketName;

    private final MinioClient client;

    /**
     * Ctor.
     *
     * @param bucketName to which this Repository should connect.
     * @param client to communicate with the s3 storage.
     * @param s3InitialConnectionTest to enable initial connection test to the s3 storage when true.
     * @throws S3AccessException if the initial connection test fails.
     */
    public S3Repository(final String bucketName,
                        final MinioClient client,
                        final boolean s3InitialConnectionTest) throws S3AccessException {
        this.bucketName = bucketName;
        this.client = client;
        if (s3InitialConnectionTest) {
            this.initialConnectionTest(bucketName, client);
        }
    }

    /**
     * Returns the paths to the files in a given folder.
     *
     * @param folder The folder.
     *               The path must be absolute and without specifying the bucket.
     *               Example 1:
     *               Folder in bucket: "BUCKET/folder"
     *               Specification in parameter: "folder"
     *               Example 2:
     *               Folder in bucket: "BUCKET/folder/subfolder"
     *               Specification in parameter: "folder/subfolder"
     * @return the paths to the files in a given folder. Also returns the paths to the files in subfolders.
     * @throws S3AccessException if the paths cannot be downloaded.
     */
    public Set<String> getFilepathesFromFolder(final String folder) throws S3AccessException {
        try {
            final ListObjectsArgs listObjectsArgs = ListObjectsArgs.builder()
                    .bucket(this.bucketName)
                    .prefix(folder)
                    .recursive(true)
                    .build();
            final List<Result<Item>> resultItemList = IteratorUtils.toList(this.client.listObjects(listObjectsArgs).iterator());
            final Set<String> filepathesFromFolder = new HashSet<>();
            for (final Result<Item> resultItem : resultItemList) {
                filepathesFromFolder.add(resultItem.get().objectName());
            }
            return filepathesFromFolder;
        } catch (final MinioException | InvalidKeyException | NoSuchAlgorithmException | IllegalArgumentException | IOException exception) {
            final String message = String.format("Failed to extract file pathes from folder %s.", folder);
            log.error(message, exception);
            throw new S3AccessException(message, exception);
        }
    }

    /**
     * Deletes the file given in the parameter.
     *
     * @param pathToFile The path to the file.
     *                   The path must be absolute and without specifying the bucket.
     *                   Example:
     *                   File in bucket: "BUCKET/outerFolder/innerFolder/thefile.csv"
     *                   Specification in parameter: "outerFolder/innerFolder/thefile.csv"
     * @throws S3AccessException if the file cannot be deleted.
     */
    public void deleteFile(final String pathToFile) throws S3AccessException {
        try {
            final RemoveObjectArgs removeObjectArgs = RemoveObjectArgs.builder()
                    .bucket(this.bucketName)
                    .object(pathToFile)
                    .build();
            this.client.removeObject(removeObjectArgs);
        } catch (final InvalidKeyException | ErrorResponseException | InsufficientDataException | InternalException
                | InvalidResponseException | NoSuchAlgorithmException | ServerException | XmlParserException
                | IllegalArgumentException | IOException exception) {
            final String message = String.format("Failed to delete file %s.", pathToFile);
            log.error(message, exception);
            throw new S3AccessException(message, exception);
        }
    }

    /**
     * Creates the presigned URL for downloading a file from a given file path.
     *
     * @param pathToFile       The path to the file.
     *                         The path must be absolute and without specifying the bucket.
     *                         Example:
     *                         File in bucket: "BUCKET/outerFolder/innerFolder/thefile.csv"
     *                         Specification in parameter: "outerFolder/innerFolder/thefile.csv"
     * @param expiresInMinutes to define the validity period of the presigned URL.
     * @return the presigned URL to fetch a file.
     * @throws S3AccessException if the presigned URL cannot be created.
     */
    public String getPresignedUrlForFileDownload(final String pathToFile, final int expiresInMinutes) throws S3AccessException {
        try {
            final GetPresignedObjectUrlArgs downloadArgs = GetPresignedObjectUrlArgs.builder()
                    .method(Method.GET)
                    .bucket(this.bucketName)
                    .object(pathToFile)
                    .expiry(expiresInMinutes, TimeUnit.MINUTES)
                    .build();
            return this.client.getPresignedObjectUrl(downloadArgs);
        } catch (final InvalidKeyException | ErrorResponseException | InsufficientDataException | InternalException
                | InvalidResponseException | NoSuchAlgorithmException | ServerException | XmlParserException
                | IllegalArgumentException | IOException exception) {
            final String message = String.format("Failed to create a download presigned url for file %s.", pathToFile);
            log.error(message, exception);
            throw new S3AccessException(message, exception);
        }
    }

    /**
     * Creates the presigned URL to delete a file given by file path.
     *
     * @param pathToFile       The path to the file.
     *                         The path must be absolute and without specifying the bucket.
     *                         Example:
     *                         File in bucket: "BUCKET/outerFolder/innerFolder/thefile.csv"
     *                         Specification in parameter: "outerFolder/innerFolder/thefile.csv"
     * @param expiresInMinutes to define the validity period of the presigned URL.
     * @return the presigned URL to delete a file.
     * @throws S3AccessException if the presigned URL cannot be created.
     */
    public String getPresignedUrlForFileDeletion(final String pathToFile, final int expiresInMinutes) throws S3AccessException {
        try {
            final GetPresignedObjectUrlArgs deletionArgs = GetPresignedObjectUrlArgs.builder()
                    .method(Method.DELETE)
                    .bucket(this.bucketName)
                    .object(pathToFile)
                    .expiry(expiresInMinutes, TimeUnit.MINUTES)
                    .build();
            return this.client.getPresignedObjectUrl(deletionArgs);
        } catch (final InvalidKeyException | ErrorResponseException | InsufficientDataException | InternalException
                | InvalidResponseException | NoSuchAlgorithmException | ServerException | XmlParserException
                | IllegalArgumentException | IOException exception) {
            final String message = String.format("Failed to create a deletion presigned url for file %s.", pathToFile);
            log.error(message, exception);
            throw new S3AccessException(message, exception);
        }
    }

    /**
     * Creates the presigned URL for uploading a file to the given file path.
     *
     * @param pathToFile       The path to the file.
     *                         The path must be absolute and without specifying the bucket.
     *                         Example:
     *                         File in bucket: "BUCKET/outerFolder/innerFolder/thefile.csv"
     *                         Specification in parameter: "outerFolder/innerFolder/thefile.csv"
     * @param expiresInMinutes to define the validity period of the presigned URL.
     * @return the presigned URL to upload a file.
     * @throws S3AccessException if the presigned URL cannot be created.
     */
    public String getPresignedUrlForFileUpload(final String pathToFile, final int expiresInMinutes) throws S3AccessException {
        try {
            final GetPresignedObjectUrlArgs uploadArgs = GetPresignedObjectUrlArgs.builder()
                    .method(Method.PUT)
                    .bucket(this.bucketName)
                    .object(pathToFile)
                    .expiry(expiresInMinutes, TimeUnit.MINUTES)
                    .build();
            return this.client.getPresignedObjectUrl(uploadArgs);
        } catch (final InvalidKeyException | ErrorResponseException | InsufficientDataException | InternalException
                | InvalidResponseException | NoSuchAlgorithmException | ServerException | XmlParserException
                | IllegalArgumentException | IOException exception) {
            final String message = String.format("Failed to create a upload presigned url for file %s.", pathToFile);
            log.error(message, exception);
            throw new S3AccessException(message, exception);
        }
    }

    /**
     * Performs an initial connection test against the S3 storage.
     *
     * @param bucketName to which this Repository should connect.
     * @param client to communicate with the s3 storage.
     * @throws S3AccessException if the initial connection test fails.
     */
    private void initialConnectionTest(final String bucketName, final MinioClient client) throws S3AccessException {
        try {
            final boolean bucketExists = client.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!bucketExists) {
                final String message = "S3 bucket does not exist.";
                log.error(message);
                throw new S3AccessException(message);
            }
        } catch (final MinioException | InvalidKeyException | NoSuchAlgorithmException | IllegalArgumentException | IOException exception) {
            final String message = "S3 initialization failed.";
            log.error(message, exception);
            throw new S3AccessException(message, exception);
        }
    }

}
