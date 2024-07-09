package de.muenchen.oss.digiwf.s3.integration.application.port.in;

import de.muenchen.oss.digiwf.s3.integration.domain.exception.FileSystemAccessException;
import de.muenchen.oss.digiwf.s3.integration.domain.model.FileSizesInFolder;
import de.muenchen.oss.digiwf.s3.integration.domain.model.FilesInFolder;
import org.springframework.lang.NonNull;

/**
 * Describes operations on a folder.
 */
public interface FolderOperationsInPort {

     /**
     * Retrieves a list of files in a folder.
     *
     *
     * @param pathToFolder path to folder.
     * @return list of files in folder.
     * @throws FileSystemAccessException on access errors.
     */
    @NonNull
    FilesInFolder getAllFilesInFolderRecursively(@NonNull String pathToFolder) throws FileSystemAccessException;

    /**
     * Retrieves the sizes of all files within a specified folder.
     *
     * @param pathToFolder the folder path for which to retrieve file sizes.
     * @return wraps a map where the keys are file paths and the values are the corresponding file sizes in bytes.
     * @throws FileSystemAccessException on access errors.
     */
    FileSizesInFolder getAllFileSizesInFolderRecursively(@NonNull String pathToFolder) throws FileSystemAccessException;

    void deleteFolder(@NonNull String pathToFolder) throws FileSystemAccessException;
}
