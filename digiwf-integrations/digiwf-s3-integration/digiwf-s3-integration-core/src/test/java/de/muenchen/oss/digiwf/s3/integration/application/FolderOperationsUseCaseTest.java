package de.muenchen.oss.digiwf.s3.integration.application;

import de.muenchen.oss.digiwf.s3.integration.adapter.out.persistence.File;
import de.muenchen.oss.digiwf.s3.integration.adapter.out.persistence.FileRepository;
import de.muenchen.oss.digiwf.s3.integration.adapter.out.s3.S3Repository;
import de.muenchen.oss.digiwf.s3.integration.application.port.in.FileSystemAccessException;
import de.muenchen.oss.digiwf.s3.integration.domain.model.FileSizesInFolder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class FolderOperationsUseCaseTest {

  @Mock
  private S3Repository s3Repository;

  @Mock
  private FileRepository fileRepository;

  private FolderOperationsUseCase folderOperationsUseCase;

  @BeforeEach
  public void beforeEach() {
    this.folderOperationsUseCase = new FolderOperationsUseCase(this.s3Repository, this.fileRepository);
    Mockito.reset(this.s3Repository);
    Mockito.reset(this.fileRepository);
  }

  @Test
  void deleteFolderException() throws FileSystemAccessException {
    final String pathToFile = "folder/file.txt";
    final String pathToFolder = "folder";
    final String pathToFolderWithSeparator = pathToFolder + "/";
    final File file = new File();
    file.setPathToFile(pathToFile);

    when(this.s3Repository.getFilePathsFromFolder(pathToFolderWithSeparator)).thenReturn(new HashSet<>());
    when(this.fileRepository.findByPathToFileStartingWith(pathToFolderWithSeparator)).thenReturn(Stream.of(file));
    Assertions.assertThrows(FileSystemAccessException.class, () -> this.folderOperationsUseCase.deleteFolder(pathToFolder));

    when(this.s3Repository.getFilePathsFromFolder(pathToFolderWithSeparator)).thenReturn(new HashSet<>(List.of(pathToFile)));
    when(this.fileRepository.findByPathToFileStartingWith(pathToFolderWithSeparator)).thenReturn(Stream.empty());
    Assertions.assertThrows(FileSystemAccessException.class, () -> this.folderOperationsUseCase.deleteFolder(pathToFolder));

    when(this.s3Repository.getFilePathsFromFolder(pathToFolderWithSeparator)).thenReturn(new HashSet<>());
    when(this.fileRepository.findByPathToFileStartingWith(pathToFolderWithSeparator)).thenReturn(Stream.empty());
    Assertions.assertDoesNotThrow(() -> this.folderOperationsUseCase.deleteFolder(pathToFolder));
  }

  @Test
  void deleteFolder() throws FileSystemAccessException {
    final String pathToFile = "folder/file.txt";
    final String pathToFolder = "folder";
    final String pathToFolderWithSeparator = pathToFolder + "/";
    final File file = new File();
    file.setPathToFile(pathToFile);

    when(this.s3Repository.getFilePathsFromFolder(pathToFolderWithSeparator)).thenReturn(new HashSet<>(List.of(pathToFile)));
    when(this.fileRepository.findByPathToFileStartingWith(pathToFolderWithSeparator)).thenReturn(Stream.of(file));
    Assertions.assertDoesNotThrow(() -> this.folderOperationsUseCase.deleteFolder(pathToFolder));
    Mockito.verify(this.fileRepository, Mockito.times(1)).deleteByPathToFile(pathToFile);
    Mockito.verify(this.s3Repository, Mockito.times(1)).deleteFile(pathToFile);
  }

  @Test
  void addPathSeparatorToTheEnd() {
    assertThat(FolderOperationsUseCase.addPathSeparatorToTheEnd("folder/subfolder")).isEqualTo("folder/subfolder/");
    assertThat(FolderOperationsUseCase.addPathSeparatorToTheEnd("folder/subfolder/")).isEqualTo("folder/subfolder/");
    assertThat(FolderOperationsUseCase.addPathSeparatorToTheEnd("folder")).isEqualTo("folder/");
    assertThat(FolderOperationsUseCase.addPathSeparatorToTheEnd("folder/")).isEqualTo("folder/");
    assertThat(FolderOperationsUseCase.addPathSeparatorToTheEnd("folder//")).isEqualTo("folder//");
    assertThat(FolderOperationsUseCase.addPathSeparatorToTheEnd("")).isEqualTo("");
    assertThat(FolderOperationsUseCase.addPathSeparatorToTheEnd(null)).isNull();
  }

  @Test
  void testGetAllFileSizesInFolderRecursivelySuccess() throws FileSystemAccessException {
    final String pathToFolder = "path/to/folder/";
    final Map<String, Long> fileSizes = Map.of("path/to/folder/file1", 100L, "path/to/folder/file2", 200L);
    when(s3Repository.getFileSizesFromFolder(anyString())).thenReturn(fileSizes);
    FileSizesInFolder result = folderOperationsUseCase.getAllFileSizesInFolderRecursively(pathToFolder);
    assertEquals(fileSizes, result.getFileSizes());
  }

}
