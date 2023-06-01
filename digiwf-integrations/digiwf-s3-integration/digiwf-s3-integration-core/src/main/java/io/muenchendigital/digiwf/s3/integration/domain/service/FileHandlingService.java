package io.muenchendigital.digiwf.s3.integration.domain.service;

import io.minio.http.Method;
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
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileHandlingService {

    public static final int MIN_EXPIRES_IN_MINUTES = 1;

    private final S3Repository s3Repository;

    private final FileRepository fileRepository;

    /**
     * Get a list of presigned urls for all files in the paths.
     * If the path is a file the presigned url for the file is returned.
     * The end of life for the files to save is not set und therefore the files are not deleted automatically.
     *
     * @param paths            list of paths to files and/or folders
     * @param action           http method for the presigned url
     * @param expiresInMinutes presigned url expiration time
     * @return
     * @throws S3AccessException
     * @throws FileExistanceException
     */
    public List<PresignedUrl> getPresignedUrls(final List<String> paths, final Method action, final int expiresInMinutes) throws S3AccessException, FileExistanceException {
        return this.getPresignedUrls(paths, action, expiresInMinutes, null);
    }

    /**
     * Get a list of presigned urls for all files in the paths.
     * If the path is a file the presigned url for the file is returned.
     *
     * @param paths            list of paths to files and/or folders
     * @param action           http method for the presigned url
     * @param expiresInMinutes presigned url expiration time
     * @param endOfLife        the files endOfLife. May be null. If null, no end of life is set
     * @return
     * @throws S3AccessException
     * @throws FileExistanceException
     */
    public List<PresignedUrl> getPresignedUrls(final List<String> paths, final Method action, final int expiresInMinutes, LocalDate endOfLife) throws S3AccessException, FileExistanceException {
        final List<PresignedUrl> presignedUrls = new ArrayList<>();
        for (String p : paths) {
            presignedUrls.addAll(this.getPresignedUrls(p, action, expiresInMinutes, endOfLife));
        }
        return presignedUrls;
    }

    /**
     * Get a list of presigned urls for all files in the path.
     * If the path is a file the presigned url for the file is returned.
     *
     * @param path             path to file or folder
     * @param action           http method for the presigned url
     * @param expiresInMinutes presigned url expiration time
     * @param endOfLife        the files endOfLife. May be null. If null, no end of life is set
     * @return
     * @throws S3AccessException
     * @throws FileExistanceException
     */
    public List<PresignedUrl> getPresignedUrls(final String path, final Method action, final int expiresInMinutes, final LocalDate endOfLife) throws S3AccessException, FileExistanceException {
        // make sure the folder exists before saving files
        if (action.equals(Method.PUT) || action.equals(Method.POST)) {
            this.setupFile(path, endOfLife);
        }

        // special case file creation (POST)
        // Use method PUT and return a single presignedUrl for the file the user wants to create
        if (action.equals(Method.POST)) {
            return List.of(this.getPresignedUrl(path, Method.PUT, expiresInMinutes, endOfLife));
        }

        // PUT, GET, DELETE return single presignedUrl if path is file. Return list of presignedUrls if path is directory
        final List<String> paths = new ArrayList<>(this.s3Repository.getFilePathsFromFolder(path));
        final List<PresignedUrl> presignedUrlList = paths.stream()
                .map(filePath -> this.getPresignedUrlForFile(filePath, action, expiresInMinutes))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        if (presignedUrlList.isEmpty()) {
            final String message = String.format("The file %s does not exist.", path);
            log.error(message);
            throw new FileExistanceException(message);
        }

        return presignedUrlList;
    }

    /**
     * Get a single presigned url for the path.
     * The end of life for the files to save is not set und therefore the files are not deleted automatically.
     *
     * @param path             path to file or folder
     * @param action           http method for the presigned url
     * @param expiresInMinutes presigned url expiration time
     * @return
     * @throws S3AccessException
     */
    public PresignedUrl getPresignedUrl(final String path, final Method action, final int expiresInMinutes) throws S3AccessException {
        return this.getPresignedUrl(path, action, expiresInMinutes, null);
    }

    /**
     * Get a single presigned url for the path
     *
     * @param path             path to file or folder
     * @param action           http method for the presigned url
     * @param expiresInMinutes presigned url expiration time
     * @param endOfLife        the files endOfLife. May be null. If null, no end of life is set
     * @return
     * @throws S3AccessException
     */
    public PresignedUrl getPresignedUrl(final String path, final Method action, final int expiresInMinutes, final LocalDate endOfLife) throws S3AccessException {
        // make sure the file exists before saving files
        if (action.equals(Method.PUT) || action.equals(Method.POST)) {
            this.setupFile(path, endOfLife);
        }
        return new PresignedUrl(this.s3Repository.getPresignedUrl(path, action, expiresInMinutes), path, action.toString());
    }

    /**
     * Creates a presigned URL to fetch the file specified in the parameter from the S3 storage.
     *
     * @param pathToFile       identifies the path to file.
     * @param expiresInMinutes to define the validity period of the presigned URL.
     * @throws FileExistanceException if the file does not exist in the folder.
     * @throws S3AccessException      if the S3 storage cannot be accessed.
     */
    public PresignedUrl getFile(final String pathToFile, final int expiresInMinutes) throws FileExistanceException, S3AccessException {
        if (!this.fileExists(pathToFile)) {
            final String message = String.format("The file %s does not exists.", pathToFile);
            log.error(message);
            throw new FileExistanceException(message);
        }
        return this.getPresignedUrl(pathToFile, Method.GET, expiresInMinutes);
    }

    /**
     * Creates a presigned URL to store the file specified in the parameter within the S3 storage.
     * The file must not exist yet.
     *
     * @param fileData with the file metadata to save.
     * @throws FileExistanceException if the file already exists.
     * @throws S3AccessException      if the S3 storage cannot be accessed.
     */
    public PresignedUrl saveFile(final FileData fileData) throws S3AccessException, FileExistanceException {
        if (this.fileExists(fileData.getPathToFile())) {
            final String message = String.format("The file %s does exists.", fileData.getPathToFile());
            log.error(message);
            throw new FileExistanceException(message);
        }
        return this.updateFile(fileData);
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
        return this.getPresignedUrl(fileData.getPathToFile(), Method.PUT, fileData.getExpiresInMinutes(), fileData.getEndOfLife());
    }

    /**
     * Updates the end of life for the given file.
     *
     * @param pathToFile identifies the path to file.
     * @param endOfLife  the files endOfLife. May be null. If null, no end of life is set
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
        if (!this.fileExists(pathToFile)) {
            final String message = String.format("The file %s does not exists.", pathToFile);
            log.error(message);
            throw new FileExistanceException(message);
        }
        return this.getPresignedUrl(pathToFile, Method.DELETE, expiresInMinutes);
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

    /**
     * Creates a new file entity within the database, if no entity identified by pathToFile is available.
     * Otherwise the exisiting file entity is updated with endOfLife.
     *
     * @param pathToFile for which a file entity should be set up in the database
     * @param endOfLife  the files endOfLife. May be null. If null, no end of life is set
     */
    private void setupFile(final String pathToFile, final LocalDate endOfLife) {
        final Optional<File> fileOptional = this.fileRepository.findByPathToFile(pathToFile);
        if (fileOptional.isEmpty()) {
            log.info("The database entry for file ${} does not exist.", pathToFile);
            final var folder = new File();
            folder.setPathToFile(pathToFile);
            folder.setEndOfLife(endOfLife);
            this.fileRepository.save(folder);
        } else {
            log.info("The database entry for file ${} already exists.", pathToFile);
            final File folder = fileOptional.get();
            folder.setEndOfLife(endOfLife);
            this.fileRepository.save(folder);
        }
    }

    private boolean fileExists(final String filePath) throws S3AccessException {
        final String pathToFolder = this.getPathToFolder(filePath);
        final Set<String> filepathsInFolder = this.s3Repository.getFilePathsFromFolder(pathToFolder);
        // if file does not exist throw an error
        return filepathsInFolder.contains(filePath);
    }

    private PresignedUrl getPresignedUrlForFile(final String filePath, final Method action, final int expiresInMinutes) {
        try {
            final String presignedUrl = this.s3Repository.getPresignedUrl(filePath, action, expiresInMinutes);
            return new PresignedUrl(presignedUrl, filePath, action.toString());
        } catch (final S3AccessException e) {
            log.warn("File not found on path {}", filePath);
        }
        return null;
    }
}
