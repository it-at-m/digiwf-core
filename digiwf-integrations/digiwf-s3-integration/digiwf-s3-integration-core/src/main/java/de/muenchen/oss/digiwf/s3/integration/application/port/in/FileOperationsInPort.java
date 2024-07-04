package de.muenchen.oss.digiwf.s3.integration.application.port.in;

import de.muenchen.oss.digiwf.s3.integration.domain.exception.FileSystemAccessException;

public interface FileOperationsInPort {
    boolean fileExists(final String path) throws FileSystemAccessException;

    void deleteFile(final String pathToFile) throws FileSystemAccessException;
}
