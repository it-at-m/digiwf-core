package de.muenchen.oss.digiwf.s3.integration.application;

import de.muenchen.oss.digiwf.s3.integration.adapter.out.s3.S3Repository;
import de.muenchen.oss.digiwf.s3.integration.application.port.in.FileOperationsInPort;
import de.muenchen.oss.digiwf.s3.integration.application.port.in.FileSystemAccessException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FileOperationsUseCase implements FileOperationsInPort {
    private final S3Repository s3Repository;

    @Override
    public boolean fileExists(final String path) throws FileSystemAccessException {
        return s3Repository.fileExists(path);
    }

    @Override
    public void deleteFile(final String pathToFile) throws FileSystemAccessException {
        s3Repository.deleteFile(pathToFile);
    }
}
