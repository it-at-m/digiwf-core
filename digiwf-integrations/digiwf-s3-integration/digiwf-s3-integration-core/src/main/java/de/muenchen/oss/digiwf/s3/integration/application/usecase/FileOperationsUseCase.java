package de.muenchen.oss.digiwf.s3.integration.application.usecase;

import de.muenchen.oss.digiwf.s3.integration.application.port.in.FileOperationsInPort;
import de.muenchen.oss.digiwf.s3.integration.application.port.out.S3OutPort;
import de.muenchen.oss.digiwf.s3.integration.domain.exception.FileSystemAccessException;
import de.muenchen.oss.digiwf.s3.integration.domain.model.FileSize;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FileOperationsUseCase implements FileOperationsInPort {
    private final S3OutPort s3OutPort;

    @Override
    public boolean fileExists(final String path) throws FileSystemAccessException {
        return s3OutPort.fileExists(path);
    }

    @Override
    public void deleteFile(final String pathToFile) throws FileSystemAccessException {
        s3OutPort.deleteFile(pathToFile);
    }

    /**
     * Retrieves the size of the specified file.
     *
     * @param pathToFile path to the file whose file size is to be retrieved.
     * @return {@link FileSize} - containing the file size.
     * @throws FileSystemAccessException if the S3 storage cannot be accessed.
     */
    @Override
    public FileSize getFileSize(String pathToFile) throws FileSystemAccessException {
        return new FileSize(s3OutPort.getFileSize(pathToFile));
    }
}
