package de.muenchen.oss.digiwf.s3.integration.application.port.in;

import de.muenchen.oss.digiwf.s3.integration.domain.exception.FileSystemAccessException;
import de.muenchen.oss.digiwf.s3.integration.domain.model.FileSize;

public interface FileOperationsInPort {
    boolean fileExists(final String path) throws FileSystemAccessException;

    void deleteFile(final String pathToFile) throws FileSystemAccessException;

    FileSize getFileSize(final String pathToFile) throws FileSystemAccessException;
}
