package io.muenchendigital.digiwf.s3.integration.domain.service;

import io.muenchendigital.digiwf.s3.integration.api.validator.FolderInFilePathValidator;
import io.muenchendigital.digiwf.s3.integration.domain.model.FilesInFolder;
import io.muenchendigital.digiwf.s3.integration.infrastructure.entity.File;
import io.muenchendigital.digiwf.s3.integration.infrastructure.exception.S3AccessException;
import io.muenchendigital.digiwf.s3.integration.infrastructure.exception.S3AndDatabaseAsyncException;
import io.muenchendigital.digiwf.s3.integration.infrastructure.repository.FileRepository;
import io.muenchendigital.digiwf.s3.integration.infrastructure.repository.S3Repository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.SetUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FolderHandlingService {

    private final S3Repository s3Repository;

    private final FileRepository fileRepository;

    private final FileHandlingService fileHandlingService;

    /**
     * Deletes the folder with all containing files specified in the parameter together with the corresponding database entries.
     *
     * @param pathToFolder identifies the path to the folder.
     * @throws S3AndDatabaseAsyncException if files exists in the S3 storage and not in the database and vice versa.
     * @throws S3AccessException           if the S3 storage cannot be accessed.
     */
    @Transactional
    public void deleteFolder(final String pathToFolder) throws S3AndDatabaseAsyncException, S3AccessException {
        final String pathToFolderWithSeparatorAtTheEnd = this.addPathSeparatorToTheEnd(pathToFolder);
        final Set<String> filepathesInDatabase = this.fileRepository.findByPathToFileStartingWith(pathToFolderWithSeparatorAtTheEnd)
                .map(File::getPathToFile)
                .collect(Collectors.toSet());
        final Set<String> filepathesInFolder = this.s3Repository.getFilePathsFromFolder(pathToFolderWithSeparatorAtTheEnd);
        if (filepathesInDatabase.isEmpty() && filepathesInFolder.isEmpty()) {
            log.info("Folder in S3 and file entities in database for this folder does not exist -> everything ok.");
        } else if (SetUtils.isEqualSet(filepathesInDatabase, filepathesInFolder)) {
            // Delete all files on S3
            log.info("All ${} files in folder ${} will be deleted.", filepathesInFolder.size(), pathToFolderWithSeparatorAtTheEnd);
            for (final String pathToFile : filepathesInFolder) {
                this.fileHandlingService.deleteFile(pathToFile);
            }
            log.info("All ${} files in folder ${} will be deleted..", filepathesInFolder.size(), pathToFolderWithSeparatorAtTheEnd);
        } else {
            // Out of sync
            final Set<String> filePathDisjunction = SetUtils.disjunction(filepathesInDatabase, filepathesInFolder).toSet();
            final StringBuilder message = new StringBuilder(String.format("The following files on S3 and the file entities in database for folder %s are out of sync.\n", pathToFolderWithSeparatorAtTheEnd));
            filePathDisjunction.stream()
                    .map(pathToFile -> pathToFile.concat("\n"))
                    .forEach(message::append);
            log.error(message.toString());
            throw new S3AndDatabaseAsyncException(message.toString());
        }
    }

    /**
     * Returns all files identified by file paths for all files contained within the folder and subfolder recursively.
     *
     * @param pathToFolder identifies the path to the folder.
     * @return the paths to the files within the folder and subfolder.
     * @throws S3AccessException if the S3 storage cannot be accessed.
     */
    public FilesInFolder getAllFilesInFolderRecursively(final String pathToFolder) throws S3AccessException {
        final String pathToFolderWithSeparatorAtTheEnd = this.addPathSeparatorToTheEnd(pathToFolder);
        final FilesInFolder filesInFolder = new FilesInFolder();
        final Set<String> filepathesInFolder = this.s3Repository.getFilePathsFromFolder(pathToFolderWithSeparatorAtTheEnd);
        filesInFolder.setPathToFiles(filepathesInFolder);
        return filesInFolder;
    }

    /**
     * The method adds a path separator to the end of the parameter if no separator is already added.
     *
     * @param pathToFolder to add a separator.
     * @return the path to folder
     */
    public String addPathSeparatorToTheEnd(final String pathToFolder) {
        String correctedPathToFolder = pathToFolder;
        if (StringUtils.isNotEmpty(pathToFolder) &&
                !StringUtils.endsWith(pathToFolder, FolderInFilePathValidator.SEPARATOR)) {
            correctedPathToFolder = correctedPathToFolder + FolderInFilePathValidator.SEPARATOR;
        }
        return correctedPathToFolder;
    }

}
