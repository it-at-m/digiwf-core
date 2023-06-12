package io.muenchendigital.digiwf.s3.integration.application;

import io.muenchendigital.digiwf.s3.integration.application.port.in.FolderOperationsInPort;
import io.muenchendigital.digiwf.s3.integration.domain.model.FilesInFolder;
import io.muenchendigital.digiwf.s3.integration.domain.service.FolderHandlingService;
import io.muenchendigital.digiwf.s3.integration.adapter.out.s3.S3AccessException;
import io.muenchendigital.digiwf.s3.integration.adapter.out.s3.S3AndDatabaseAsyncException;
import io.muenchendigital.digiwf.s3.integration.adapter.out.persistence.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Size;

@RequiredArgsConstructor
@Validated
public class FolderOperationsUseCase implements FolderOperationsInPort {
  private final FolderHandlingService folderHandlingService;

  @Override
  @NonNull
  public FilesInFolder getAllFilesInFolderRecursively(@Size(max = FileRepository.LENGTH_PATH_TO_FILE) @NonNull String pathToFolder) throws S3AccessException {
    return folderHandlingService.getAllFilesInFolderRecursively(pathToFolder);
  }

  @Override
  public void deleteFolder(@Size(max = FileRepository.LENGTH_PATH_TO_FILE) @NonNull String pathToFolder) throws S3AccessException, S3AndDatabaseAsyncException {
    folderHandlingService.deleteFolder(pathToFolder);
  }
}
