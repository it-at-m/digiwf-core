package de.muenchen.oss.digiwf.s3.integration.application.usecase;

import de.muenchen.oss.digiwf.s3.integration.adapter.in.rest.validation.FolderInFilePathValidator;
import de.muenchen.oss.digiwf.s3.integration.adapter.out.s3.S3Repository;
import de.muenchen.oss.digiwf.s3.integration.application.port.in.FileSystemAccessException;
import de.muenchen.oss.digiwf.s3.integration.application.port.in.FolderOperationsInPort;
import de.muenchen.oss.digiwf.s3.integration.domain.model.FilesInFolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class FolderOperationsUseCase implements FolderOperationsInPort {

    private final S3Repository s3Repository;

    /**
     * The method adds a path separator to the end of the parameter if no separator is already added.
     *
     * @param pathToFolder to add a separator.
     * @return the path to folder
     */
    public static String addPathSeparatorToTheEnd(final String pathToFolder) {
        String correctedPathToFolder = pathToFolder;
        if (StringUtils.isNotEmpty(pathToFolder) &&
                !StringUtils.endsWith(pathToFolder, FolderInFilePathValidator.SEPARATOR)) {
            correctedPathToFolder = correctedPathToFolder + FolderInFilePathValidator.SEPARATOR;
        }
        return correctedPathToFolder;
    }

    /**
     * Deletes the folder with all containing files specified in the parameter together with the corresponding database entries.
     *
     * @param pathToFolder identifies the path to the folder.
     * @throws FileSystemAccessException if the S3 storage cannot be accessed.
     */
    @Transactional
    @Override
    public void deleteFolder(@NotNull final String pathToFolder) throws FileSystemAccessException {
        final String pathToFolderWithSeparatorAtTheEnd = addPathSeparatorToTheEnd(pathToFolder);
        final Set<String> filePathsInFolder = this.s3Repository.getFilePathsFromFolder(pathToFolderWithSeparatorAtTheEnd);
        if (filePathsInFolder.isEmpty()) {
            log.info("Folder is empty in s3");
        } else {
            // Delete all files on S3
            log.info("Deleting {} files in folder {}", filePathsInFolder.size(), pathToFolderWithSeparatorAtTheEnd);
            for (final String pathToFile : filePathsInFolder) {
                // Delete file on S3
                this.s3Repository.deleteFile(pathToFile);
            }
        }
    }

    /**
     * Returns all files identified by file paths for all files contained within the folder and subfolder recursively.
     *
     * @param pathToFolder identifies the path to the folder.
     * @return the paths to the files within the folder and subfolder.
     * @throws FileSystemAccessException if the S3 storage cannot be accessed.
     */
    @NotNull
    @Override
    public FilesInFolder getAllFilesInFolderRecursively(@NotNull final String pathToFolder) throws FileSystemAccessException {
        final String pathToFolderWithSeparatorAtTheEnd = addPathSeparatorToTheEnd(pathToFolder);
        final FilesInFolder filesInFolder = new FilesInFolder();
        final Set<String> filePathsInFolder = this.s3Repository.getFilePathsFromFolder(pathToFolderWithSeparatorAtTheEnd);
        filesInFolder.setPathToFiles(filePathsInFolder);
        return filesInFolder;
    }

}
