package io.muenchendigital.digiwf.s3.integration.application.port.in;

import io.muenchendigital.digiwf.s3.integration.domain.model.FilesInFolder;
import io.muenchendigital.digiwf.s3.integration.adapter.out.s3.S3AccessException;
import io.muenchendigital.digiwf.s3.integration.adapter.out.s3.S3AndDatabaseAsyncException;
import org.springframework.lang.NonNull;

/**
 * Describes operations on a folder.
 */
public interface FolderOperationsInPort {
  @NonNull
  FilesInFolder getAllFilesInFolderRecursively(@NonNull String pathToFolder) throws S3AccessException;

  void deleteFolder(@NonNull String pathToFolder) throws S3AccessException, S3AndDatabaseAsyncException;
}
