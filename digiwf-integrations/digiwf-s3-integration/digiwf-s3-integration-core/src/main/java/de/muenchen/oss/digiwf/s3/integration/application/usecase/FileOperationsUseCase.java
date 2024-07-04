package de.muenchen.oss.digiwf.s3.integration.application.usecase;

import de.muenchen.oss.digiwf.s3.integration.application.port.in.FileOperationsInPort;
import de.muenchen.oss.digiwf.s3.integration.application.port.out.S3OutPort;
import de.muenchen.oss.digiwf.s3.integration.domain.exception.FileSystemAccessException;
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
}
