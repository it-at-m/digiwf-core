package io.muenchendigital.digiwf.s3.integration.infrastructure.repository;

import io.muenchendigital.digiwf.s3.integration.infrastructure.exception.S3AccessException;

import java.util.Set;

public interface S3RepositoryInterface {
    Set<String> getFilepathesFromFolder(String folder) throws S3AccessException;

    void deleteFile(String pathToFile) throws S3AccessException;

    String getPresignedUrlForFileDownload(String pathToFile, int expiresInMinutes) throws S3AccessException;
}
