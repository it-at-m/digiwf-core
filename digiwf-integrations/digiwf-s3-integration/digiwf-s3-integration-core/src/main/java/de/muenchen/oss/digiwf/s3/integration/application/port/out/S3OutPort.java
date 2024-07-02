package de.muenchen.oss.digiwf.s3.integration.application.port.out;

import de.muenchen.oss.digiwf.s3.integration.application.port.in.FileSystemAccessException;
import io.minio.http.Method;

import java.util.Set;

public interface S3OutPort {
    boolean fileExists(final String path) throws FileSystemAccessException;

    Set<String> getFilePathsFromFolder(final String folder) throws FileSystemAccessException;

    void deleteFile(final String pathToFile) throws FileSystemAccessException;

    String getPresignedUrl(final String pathToFile, final Method action, final int expiresInMinutes) throws FileSystemAccessException;
}
