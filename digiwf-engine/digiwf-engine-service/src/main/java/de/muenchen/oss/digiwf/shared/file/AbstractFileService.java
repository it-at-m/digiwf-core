package de.muenchen.oss.digiwf.shared.file;

import de.muenchen.oss.digiwf.process.config.process.ProcessConfigFunctions;
import de.muenchen.oss.digiwf.s3.integration.client.repository.DocumentStorageFolderRepository;
import de.muenchen.oss.digiwf.shared.file.presignedUrlAdapters.PresignedUrlAction;
import de.muenchen.oss.digiwf.shared.file.presignedUrlAdapters.PresignedUrlAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpServerErrorException;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * General logic for handling file storage.
 *
 * @author martin.dietrich
 */
@Slf4j
public abstract class AbstractFileService {

    public static final String FILEPATH_DELIMITER = ";";
    public static final String ERRTEXT_ILLEGAL_ACCESS = "No access to defined property";

    protected final DocumentStorageFolderRepository documentStorageFolderRepository;
    private final List<PresignedUrlAdapter> presignedUrlAdapters;
    private final ProcessConfigFunctions processConfigFunctions;

    public AbstractFileService(
            final DocumentStorageFolderRepository documentStorageFolderRepository,
            final List<PresignedUrlAdapter> presignedUrlAdapters,
            final ProcessConfigFunctions processConfigFunctions
    ) {
        this.documentStorageFolderRepository = documentStorageFolderRepository;
        this.presignedUrlAdapters = presignedUrlAdapters;
        this.processConfigFunctions = processConfigFunctions;
    }

    public List<String> getFileNames(final String filePath, final String fileContext, final Optional<String> documentStorageUrl) {
        try {
            final String pathToFolder = fileContext + "/" + filePath;
            if (documentStorageUrl.isPresent()) {
                return this.extractFilenamesFromFolder(this.documentStorageFolderRepository.getAllFilesInFolderRecursively(pathToFolder, documentStorageUrl.get()).block(), pathToFolder);
            }
            return this.extractFilenamesFromFolder(this.documentStorageFolderRepository.getAllFilesInFolderRecursively(pathToFolder).block(), pathToFolder);
        } catch (final Exception ex) {
            log.error("Getting all files of folder {} failed: {}", filePath, ex);
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, String.format("Getting all files of folder %s failed", filePath));
        }
    }

    protected String getPresignedUrl(final PresignedUrlAction action, final String pathToFile, final Optional<String> documentStorageUrl) {
        final Optional<PresignedUrlAdapter> handler = this.presignedUrlAdapters.stream()
                .filter(h -> h.isResponsibleForAction(action))
                .findAny();
        if (handler.isPresent()) {
            if (documentStorageUrl.isPresent()) {
                return handler.get().getPresignedUrl(documentStorageUrl.get(), pathToFile, 5);
            }
            return handler.get().getPresignedUrl(pathToFile, 5);
        }
        log.warn("No handler specified for action {}", action);
        throw new RuntimeException(String.format("No handler specified for action %s", action));
    }

    protected Optional<String> getDocumentStorageUrl(final String definitionKey) {
        return this.processConfigFunctions.get("app_file_s3_sync_config", definitionKey);
    }

    //---------------------------------------- helper methods ---------------------------------------- //

    /**
     * Extract the filenames from the given file list. Make sure that only filenames for files in the given folder are returned.
     * Don't return filenames for files in subfolders.
     *
     * @param fileList
     * @param pathToFolder
     * @return
     */
    private List<String> extractFilenamesFromFolder(final Set<String> fileList, final String pathToFolder) {
        final String basePath = (pathToFolder + "/").replace("//", "/");
        return fileList.stream()
                .map(file -> file = file.replace(basePath, ""))
                .filter(file -> !file.contains("/"))
                .toList();
    }

}
