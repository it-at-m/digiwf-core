package io.muenchendigital.digiwf.s3.integration.domain.service;

import io.muenchendigital.digiwf.s3.integration.infrastructure.entity.File;
import io.muenchendigital.digiwf.s3.integration.infrastructure.exception.S3AccessException;
import io.muenchendigital.digiwf.s3.integration.infrastructure.exception.S3AndDatabaseAsyncException;
import io.muenchendigital.digiwf.s3.integration.infrastructure.repository.FileRepository;
import io.muenchendigital.digiwf.s3.integration.infrastructure.repository.S3Repository;
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
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class FolderHandlingServiceTest {

    @Mock
    private S3Repository s3Repository;

    @Mock
    private FileRepository fileRepository;

    @Mock
    private FileHandlingService fileHandlingService;

    private FolderHandlingService folderHandlingService;

    @BeforeEach
    public void beforeEach() {
        this.folderHandlingService = new FolderHandlingService(this.s3Repository, this.fileRepository, this.fileHandlingService);
        Mockito.reset(this.s3Repository);
        Mockito.reset(this.fileRepository);
        Mockito.reset(this.fileHandlingService);
    }

    @Test
    void deleteFolderException() throws S3AccessException {
        final String pathToFile = "folder/file.txt";
        final String pathToFolder = "folder";
        final String pathToFolderWithSeparator = pathToFolder + "/";
        final File file = new File();
        file.setPathToFile(pathToFile);

        Mockito.when(this.s3Repository.getFilePathsFromFolder(pathToFolderWithSeparator)).thenReturn(new HashSet<>());
        Mockito.when(this.fileRepository.findByPathToFileStartingWith(pathToFolderWithSeparator)).thenReturn(Stream.of(file));
        Assertions.assertThrows(S3AndDatabaseAsyncException.class, () -> this.folderHandlingService.deleteFolder(pathToFolder));

        Mockito.when(this.s3Repository.getFilePathsFromFolder(pathToFolderWithSeparator)).thenReturn(new HashSet<>(List.of(pathToFile)));
        Mockito.when(this.fileRepository.findByPathToFileStartingWith(pathToFolderWithSeparator)).thenReturn(Stream.empty());
        Assertions.assertThrows(S3AndDatabaseAsyncException.class, () -> this.folderHandlingService.deleteFolder(pathToFolder));

        Mockito.when(this.s3Repository.getFilePathsFromFolder(pathToFolderWithSeparator)).thenReturn(new HashSet<>());
        Mockito.when(this.fileRepository.findByPathToFileStartingWith(pathToFolderWithSeparator)).thenReturn(Stream.empty());
        Assertions.assertDoesNotThrow(() -> this.folderHandlingService.deleteFolder(pathToFolder));
    }

    @Test
    void deleteFolder() throws S3AccessException {
        final String pathToFile = "folder/file.txt";
        final String pathToFolder = "folder";
        final String pathToFolderWithSeparator = pathToFolder + "/";
        final File file = new File();
        file.setPathToFile(pathToFile);

        Mockito.when(this.s3Repository.getFilePathsFromFolder(pathToFolderWithSeparator)).thenReturn(new HashSet<>(List.of(pathToFile)));
        Mockito.when(this.fileRepository.findByPathToFileStartingWith(pathToFolderWithSeparator)).thenReturn(Stream.of(file));
        Assertions.assertDoesNotThrow(() -> this.folderHandlingService.deleteFolder(pathToFolder));
        Mockito.verify(this.fileHandlingService, Mockito.times(1)).deleteFile(pathToFile);
    }

    @Test
    void addPathSeparatorToTheEnd() {
        assertThat(this.folderHandlingService.addPathSeparatorToTheEnd("folder/subfolder"), is("folder/subfolder/"));
        assertThat(this.folderHandlingService.addPathSeparatorToTheEnd("folder/subfolder/"), is("folder/subfolder/"));
        assertThat(this.folderHandlingService.addPathSeparatorToTheEnd("folder"), is("folder/"));
        assertThat(this.folderHandlingService.addPathSeparatorToTheEnd("folder/"), is("folder/"));
        assertThat(this.folderHandlingService.addPathSeparatorToTheEnd("folder//"), is("folder//"));
        assertThat(this.folderHandlingService.addPathSeparatorToTheEnd(""), is(""));
        assertThat(this.folderHandlingService.addPathSeparatorToTheEnd(null), is(nullValue()));
    }

}
