package de.muenchen.oss.digiwf.ticket.integration.adapter.out.s3;

import de.muenchen.oss.digiwf.message.process.api.error.BpmnError;
import de.muenchen.oss.digiwf.s3.integration.client.exception.DocumentStorageClientErrorException;
import de.muenchen.oss.digiwf.s3.integration.client.exception.DocumentStorageException;
import de.muenchen.oss.digiwf.s3.integration.client.exception.DocumentStorageServerErrorException;
import de.muenchen.oss.digiwf.s3.integration.client.exception.PropertyNotSetException;
import de.muenchen.oss.digiwf.s3.integration.client.repository.DocumentStorageFileRepository;
import de.muenchen.oss.digiwf.s3.integration.client.repository.DocumentStorageFolderRepository;
import de.muenchen.oss.digiwf.s3.integration.client.service.FileService;
import de.muenchen.oss.digiwf.s3.integration.client.service.S3StorageUrlProvider;
import de.muenchen.oss.digiwf.ticket.integration.application.port.out.LoadFileOutPort;
import de.muenchen.oss.digiwf.ticket.integration.domain.model.FileContent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.util.unit.DataSize;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class S3Adapter implements LoadFileOutPort {

    private static final String LOAD_FOLDER_FAILED = "LOAD_FOLDER_FAILED";
    private static final String FILE_SIZE_ERROR = "FILE_SIZE_ERROR";
    private static final String BATCH_SIZE_ERROR = "BATCH_SIZE_ERROR";
    private static final String FILE_TYPE_NOT_SUPPORTED = "FILE_TYPE_NOT_SUPPORTED";
    private static final String LOAD_FILE_FAILED = "LOAD_FILE_FAILED";

    private final DocumentStorageFileRepository documentStorageFileRepository;
    private final DocumentStorageFolderRepository documentStorageFolderRepository;
    private final FileService fileService;
    private final S3StorageUrlProvider s3StorageUrlProvider;

    @Override
    public List<FileContent> loadFiles(final List<String> filePaths, final String fileContext, final String processDefinition) {
        final String s3Storage;
        try {
            s3Storage = s3StorageUrlProvider.provideS3StorageUrl(processDefinition);
        } catch (PropertyNotSetException e) {
            throw new BpmnError(LOAD_FOLDER_FAILED, e.getMessage());
        }
        validateFileSizes(filePaths, fileContext, s3Storage);
        final List<FileContent> contents = new ArrayList<>();
        filePaths.forEach(path -> {
            final String fullPath = fileContext + "/" + path;
            if (fullPath.endsWith("/")) {
                contents.addAll(getFilesFromFolder(fullPath, s3Storage));
            } else {
                contents.add(getFile(fullPath, s3Storage));
            }
        });
        return contents;
    }

    private void validateFileSizes(final List<String> filePaths, final String fileContext, final String s3Storage) {
        // Collect file sizes along with their paths
        final Map<String, Long> fileSizesWithPaths = getFileSizesWithPaths(filePaths, fileContext, s3Storage);

        // Filter files exceeding maximum size
        final Map<String, Long> oversizedFiles = fileService.getOversizedFiles(fileSizesWithPaths);
        // Handle oversized files
        if (!oversizedFiles.isEmpty()) {
            final String filesOverMaxString = oversizedFiles.entrySet().stream()
                    .map(entry -> entry.getKey() + ": " + DataSize.ofBytes(entry.getValue()).toMegabytes() + " MB")
                    .collect(Collectors.joining(System.lineSeparator()));
            throw new BpmnError(FILE_SIZE_ERROR,
                    String.format("The following files exceed the maximum size of %d MB:%n%s", fileService.getMaxFileSize().toMegabytes(), filesOverMaxString));
        }

        // Validate total batch size
        final DataSize totalFileSize = fileService.getTotalBatchSize(fileSizesWithPaths);
        if (!fileService.isValidBatchSize(totalFileSize))
            throw new BpmnError(BATCH_SIZE_ERROR, String.format("Batch size of %d MB is too large. Allowed are %d MB.",
                    totalFileSize.toMegabytes(), fileService.getMaxBatchSize().toMegabytes()));
    }

    private Map<String, Long> getFileSizesWithPaths(final List<String> filePaths, final String fileContext, final String s3Storage) {
        return filePaths.stream()
                .map(path -> fileContext + "/" + path)
                .flatMap(path -> getFileSizeForPath(path, s3Storage).entrySet().stream())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private Map<String, Long> getFileSizeForPath(final String path, final String s3Storage) {
        if (path.endsWith("/")) {
            return getSizesInFolderRecursively(path, s3Storage);
        } else {
            return Map.of(path, getFileSize(path, s3Storage));
        }
    }

    private Map<String, Long> getSizesInFolderRecursively(final String folderPath, final String s3Storage) {
        try {
            return Objects.requireNonNull(documentStorageFolderRepository
                    .getAllFileSizesInFolderRecursively(folderPath, s3Storage)
                    .block());
        } catch (final DocumentStorageException | DocumentStorageServerErrorException | DocumentStorageClientErrorException e) {
            throw new BpmnError(LOAD_FOLDER_FAILED, "Metadata of a folder could not be loaded from url: " + folderPath);
        }
    }

    private long getFileSize(final String filePath, final String s3Storage) {
        try {
            return Objects.requireNonNull(documentStorageFileRepository
                    .getFileSize(filePath, s3Storage).block());
        } catch (final DocumentStorageException | DocumentStorageServerErrorException | DocumentStorageClientErrorException e) {
            throw new BpmnError(LOAD_FOLDER_FAILED, "Metadata of a folder could not be loaded from url: " + filePath);
        }
    }

    private List<FileContent> getFilesFromFolder(String folderPath, final String domainSpecificS3Storage) {
        try {
            final List<FileContent> contents = new ArrayList<>();
            final Set<String> filePath;
            filePath = documentStorageFolderRepository.getAllFilesInFolderRecursively(folderPath, domainSpecificS3Storage).block();
            if (Objects.isNull(filePath)) throw new BpmnError(LOAD_FOLDER_FAILED, "An folder could not be loaded from url: " + folderPath);
            filePath.forEach(file -> contents.add(getFile(file, domainSpecificS3Storage)));
            return contents;
        } catch (final NullPointerException | DocumentStorageException | DocumentStorageServerErrorException | DocumentStorageClientErrorException e) {
            throw new BpmnError(LOAD_FOLDER_FAILED, "An folder could not be loaded from url: " + folderPath);
        }
    }

    private FileContent getFile(String filepath, final String domainSpecificS3Storage) {
        try {
            final byte[] bytes;
            bytes = this.documentStorageFileRepository.getFile(filepath, 3, domainSpecificS3Storage);
            final String mimeType = fileService.detectFileType(bytes);
            final String filename = FilenameUtils.getName(filepath);

            // check if mimeType exists
            if (!fileService.isSupported(mimeType))
                throw new BpmnError(FILE_TYPE_NOT_SUPPORTED, "The type of this file is not supported: " + filepath);

            return new FileContent(mimeType, filename, bytes);
        } catch (final DocumentStorageException | DocumentStorageServerErrorException | DocumentStorageClientErrorException e) {
            throw new BpmnError(LOAD_FILE_FAILED, "A file could not be loaded from url: " + filepath);
        }
    }

}
