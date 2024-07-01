package de.muenchen.oss.digiwf.s3.integration.application.port.in;

public interface FileOperationsInPort {
    boolean fileExists(final String path) throws FileSystemAccessException;

    void deleteFile(final String pathToFile) throws FileSystemAccessException;
}
