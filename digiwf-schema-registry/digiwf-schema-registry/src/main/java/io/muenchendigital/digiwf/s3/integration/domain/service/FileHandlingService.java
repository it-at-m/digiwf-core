package io.muenchendigital.digiwf.s3.integration.domain.service;

import io.muenchendigital.digiwf.s3.integration.api.validator.FolderInFilePathValidator;
import io.muenchendigital.digiwf.s3.integration.domain.exception.FileExistanceException;
import io.muenchendigital.digiwf.s3.integration.domain.model.FileData;
import io.muenchendigital.digiwf.s3.integration.domain.model.PresignedUrl;
import io.muenchendigital.digiwf.s3.integration.infrastructure.entity.File;
import io.muenchendigital.digiwf.s3.integration.infrastructure.exception.S3AccessException;
import io.muenchendigital.digiwf.s3.integration.infrastructure.repository.FileRepository;
import io.muenchendigital.digiwf.s3.integration.infrastructure.repository.S3Repository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileHandlingService {

    public static final int MIN_EXPIRES_IN_MINUTES = 1;

    private final S3Repository s3Repository;

    private final FileRepository fileRepository;

    /**
     * Creates a presigned URL to fetch the file specified in the parameter from the S3 storage.
     *
     * @param pathToFile       identifies the path to file.
     * @param expiresInMinutes to define the validity period of the presigned URL.
     * @throws FileExistanceException if the file does not exist in the folder.
     * @throws S3AccessException      if the S3 storage cannot be accessed.
     */
    public PresignedUrl getFile(final String pathToFile, final int expiresInMinutes) throws FileExistanceException, S3AccessException {
        final String pathToFolder = this.getPathToFolder(pathToFile);
        final Set<String> filepathesInFolder = this.s3Repository.getFilepathesFromFolder(pathToFolder);
        if (filepathesInFolder.contains(pathToFile)) {
            final String presignedUrl = this.s3Repository.getPresignedUrlForFileDownload(pathToFile, expiresInMinutes);
            return new PresignedUrl(presignedUrl);
        } else {
            final String message = String.format("The file %s does not exist.", pathToFile);
            log.error(message);
            throw new FileExistanceException(message);
        }
    }

    /**
     * Creates a presigned URL to store the file specified in the parameter within the S3 storage.
     * The file must not exist yet.
     *
     * @param fileData with the file metadata to save.
     * @throws FileExistanceException if the file already exists.
     * @throws S3AccessException      if the S3 storage cannot be accessed.
     */
    public PresignedUrl saveFile(final FileData fileData) throws FileExistanceException, S3AccessException {
        final String pathToFolder = this.getPathToFolder(fileData.getPathToFile());
        final Set<String> filepathesInFolder = this.s3Repository.getFilepathesFromFolder(pathToFolder);
        if (filepathesInFolder.contains(fileData.getPathToFile())) {
            final String message = String.format("The file %s already exists.", fileData.getPathToFile());
            log.error(message);
            throw new FileExistanceException(message);
        } else {
            log.info("The new file ${} will be saved.", fileData.getPathToFile());
            return this.updateFile(fileData);
        }
    }

    /**
     * Creates a presigned URL to overwrite the file specified in the parameter within the S3 storage.
     * Furthermore, the entry regarding {@link File#getEndOfLife()} is adjusted in the database.
     * <p>
     * If the file does not yet exist in the S3 storage, it is newly created and a
     * corresponding {@link File} is persisted in the database.
     *
     * @param fileData with the file metadata for resaving.
     * @throws S3AccessException if the S3 storage cannot be accessed.
     */
    @Transactional
    public PresignedUrl updateFile(final FileData fileData) throws S3AccessException {
        final Optional<File> fileOptional = this.fileRepository.findByPathToFile(fileData.getPathToFile());
        if (fileOptional.isEmpty()) {
            log.info("The database entry for file ${} does not exist.", fileData.getPathToFile());
            final var folder = new File();
            folder.setPathToFile(fileData.getPathToFile());
            folder.setEndOfLife(fileData.getEndOfLife());
            this.fileRepository.save(folder);
        } else {
            log.info("The database entry for file ${} already exists.", fileData.getPathToFile());
            final File folder = fileOptional.get();
            folder.setEndOfLife(fileData.getEndOfLife());
            this.fileRepository.save(folder);
        }
        final String presignedUrl = this.s3Repository.getPresignedUrlForFileUpload(
                fileData.getPathToFile(),
                fileData.getExpiresInMinutes()
        );
        return new PresignedUrl(presignedUrl);
    }

    /**
     * Updates the end of life for the given file.
     *
     * @param pathToFile identifies the path to file.
     * @param endOfLife  the new end of life or null.
     * @throws FileExistanceException if no database entry exists.
     */
    @Transactional
    public void updateEndOfLife(final String pathToFile, final LocalDate endOfLife) throws FileExistanceException {
        final Optional<File> fileOptional = this.fileRepository.findByPathToFile(pathToFile);
        if (fileOptional.isPresent()) {
            final File file = fileOptional.get();
            file.setEndOfLife(endOfLife);
            this.fileRepository.save(file);
            log.info("End of life updated for file ${} to ${}", file, endOfLife);
        } else {
            final String message = String.format("No database entry for file %s is found.", pathToFile);
            log.error(message);
            throw new FileExistanceException(message);
        }
    }

    /**
     * Creates a presigned URL to delete the file specified in the parameter from the S3 storage.
     *
     * @param pathToFile       identifies the path to file.
     * @param expiresInMinutes to define the validity period of the presigned URL.
     * @throws FileExistanceException if the file does not exist in the folder.
     * @throws S3AccessException      if the S3 storage cannot be accessed.
     */
    @Transactional
    public PresignedUrl deleteFile(final String pathToFile, final int expiresInMinutes) throws FileExistanceException, S3AccessException {
        final String pathToFolder = this.getPathToFolder(pathToFile);
        final Set<String> filepathesInFolder = this.s3Repository.getFilepathesFromFolder(pathToFolder);
        if (filepathesInFolder.contains(pathToFile)) {
            log.info("The file ${} exists.", pathToFile);
            final String presignedUrl = this.s3Repository.getPresignedUrlForFileDeletion(pathToFile, expiresInMinutes);
            this.fileRepository.deleteByPathToFile(pathToFile);
            return new PresignedUrl(presignedUrl);
        } else {
            final String message = String.format("The file %s does not exist.", pathToFile);
            log.error(message);
            throw new FileExistanceException(message);
        }
    }

    /**
     * Deletes the file given in the parameter on S3 storage and within the database.
     *
     * @param pathToFile identifies the path to file.
     * @throws S3AccessException if the S3 storage cannot be accessed.
     */
    @Transactional
    public void deleteFile(final String pathToFile) throws S3AccessException {
        // Delete file on S3
        this.s3Repository.deleteFile(pathToFile);
        // Delete database entry
        this.fileRepository.deleteByPathToFile(pathToFile);
    }

    /**
     * Return the path to the folder for the given file path in the parameter.
     * <p>
     * pathToFile: FOLDER/SUBFOLDER/file.txt
     * pathToFolder: FOLDER/SUBFOLDER
     *
     * @param pathToFile for which the path to folder should be returned.
     * @return the path to the folder for the given path to file.
     */
    public String getPathToFolder(final String pathToFile) {
        return StringUtils.contains(pathToFile, FolderInFilePathValidator.SEPARATOR)
                ? StringUtils.substringBeforeLast(pathToFile, FolderInFilePathValidator.SEPARATOR)
                : StringUtils.EMPTY;
    }

}
