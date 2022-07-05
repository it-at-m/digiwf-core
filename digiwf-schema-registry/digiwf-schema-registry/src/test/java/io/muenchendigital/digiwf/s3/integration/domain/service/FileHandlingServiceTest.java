package io.muenchendigital.digiwf.s3.integration.domain.service;

import io.muenchendigital.digiwf.s3.integration.domain.exception.FileExistanceException;
import io.muenchendigital.digiwf.s3.integration.domain.model.FileData;
import io.muenchendigital.digiwf.s3.integration.domain.model.PresignedUrl;
import io.muenchendigital.digiwf.s3.integration.infrastructure.entity.File;
import io.muenchendigital.digiwf.s3.integration.infrastructure.exception.S3AccessException;
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

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class FileHandlingServiceTest {

    @Mock
    private S3Repository s3Repository;

    @Mock
    private FileRepository fileRepository;

    private FileHandlingService fileHandlingService;

    @BeforeEach
    public void beforeEach() {
        this.fileHandlingService = new FileHandlingService(this.s3Repository, this.fileRepository);
        Mockito.reset(this.fileRepository);
        Mockito.reset(this.s3Repository);
    }

    @Test
    void getFileException() throws Exception {
        final String pathToFile = "folder/test.txt";
        final String pathToFolder = "folder";
        final int expiresInMinutes = 5;
        Mockito.when(this.s3Repository.getFilepathesFromFolder(pathToFolder)).thenReturn(new HashSet<>());
        Assertions.assertThrows(FileExistanceException.class, () -> this.fileHandlingService.getFile(pathToFile, expiresInMinutes));
    }

    @Test
    void getFile() throws S3AccessException, FileExistanceException {
        final String pathToFile = "folder/test.txt";
        final String pathToFolder = "folder";
        final int expiresInMinutes = 5;
        final String presignedUrl = "THE_PRESIGNED_URL";

        Mockito.when(this.s3Repository.getFilepathesFromFolder(pathToFolder)).thenReturn(new HashSet<>(List.of(pathToFile)));
        Mockito.when(this.s3Repository.getPresignedUrlForFileDownload(pathToFile, expiresInMinutes)).thenReturn(presignedUrl);

        final PresignedUrl result = this.fileHandlingService.getFile(pathToFile, expiresInMinutes);

        final PresignedUrl expected = new PresignedUrl(presignedUrl);

        assertThat(result, is(expected));
    }

    @Test
    void saveFile() throws S3AccessException {
        final String pathToFile = "folder/test.txt";
        final String pathToFolder = "folder";

        final FileData fileData = new FileData();
        fileData.setEndOfLife(LocalDate.of(2022, 1, 1));
        fileData.setPathToFile(pathToFile);
        fileData.setExpiresInMinutes(5);

        Mockito.when(this.s3Repository.getFilepathesFromFolder(pathToFolder)).thenReturn(new HashSet<>(List.of(pathToFile)));
        Assertions.assertThrows(FileExistanceException.class, () -> this.fileHandlingService.saveFile(fileData));
        // happy path is tested in updateFile
    }

    @Test
    void updateFile() throws IOException, S3AccessException {
        final String pathToFile = "folder/test.txt";

        final FileData fileData = new FileData();
        fileData.setEndOfLife(LocalDate.of(2022, 1, 1));
        fileData.setPathToFile(pathToFile);
        fileData.setExpiresInMinutes(5);

        // File not in Database
        Mockito.when(this.fileRepository.findByPathToFile(pathToFile)).thenReturn(Optional.empty());
        this.fileHandlingService.updateFile(fileData);
        final var fileToSave1 = new File();
        fileToSave1.setPathToFile(pathToFile);
        fileToSave1.setEndOfLife(fileData.getEndOfLife());
        Mockito.verify(this.fileRepository, Mockito.times(1)).save(fileToSave1);
        Mockito.verify(this.s3Repository, Mockito.times(1)).getPresignedUrlForFileUpload(pathToFile, fileData.getExpiresInMinutes());

        // File already in Database with older and of life
        Mockito.reset(this.fileRepository);
        Mockito.reset(this.s3Repository);
        final var fileToFind1 = new File();
        fileToFind1.setPathToFile(fileData.getPathToFile());
        fileToFind1.setEndOfLife(fileData.getEndOfLife().minusYears(1));
        Mockito.when(this.fileRepository.findByPathToFile(pathToFile)).thenReturn(Optional.of(fileToFind1));
        this.fileHandlingService.updateFile(fileData);
        final var folderToSave2 = new File();
        folderToSave2.setPathToFile(fileToFind1.getPathToFile());
        folderToSave2.setEndOfLife(fileData.getEndOfLife());
        Mockito.verify(this.fileRepository, Mockito.times(1)).save(folderToSave2);
        Mockito.verify(this.s3Repository, Mockito.times(1)).getPresignedUrlForFileUpload(pathToFile, fileData.getExpiresInMinutes());

        // File already in Database with older and of life
        Mockito.reset(this.fileRepository);
        Mockito.reset(this.s3Repository);
        final var folderToFind2 = new File();
        folderToFind2.setPathToFile(fileData.getPathToFile());
        folderToFind2.setEndOfLife(fileData.getEndOfLife().plusYears(1));
        Mockito.when(this.fileRepository.findByPathToFile(pathToFile)).thenReturn(Optional.of(folderToFind2));
        this.fileHandlingService.updateFile(fileData);
        final var folderToSave3 = new File();
        folderToSave3.setPathToFile(folderToFind2.getPathToFile());
        folderToSave3.setEndOfLife(fileData.getEndOfLife());
        Mockito.verify(this.fileRepository, Mockito.times(1)).save(folderToSave3);
        Mockito.verify(this.s3Repository, Mockito.times(1)).getPresignedUrlForFileUpload(pathToFile, fileData.getExpiresInMinutes());
    }

    @Test
    void updateEndOfLifeException() {
        final String pathToFile = "folder/test.txt";
        final LocalDate endOfLife = LocalDate.of(2022, 1, 1);

        Mockito.when(this.fileRepository.findByPathToFile(pathToFile)).thenReturn(Optional.empty());
        Assertions.assertThrows(FileExistanceException.class, () -> this.fileHandlingService.updateEndOfLife(pathToFile, endOfLife));
    }

    @Test
    void updateEndOfLife() throws FileExistanceException {
        final String pathToFile = "folder/test.txt";
        final LocalDate endOfLife = LocalDate.of(2022, 1, 1);

        final File fileToFind = new File();
        fileToFind.setPathToFile(pathToFile);
        fileToFind.setEndOfLife(null);
        final Optional<File> fileOptional = Optional.of(fileToFind);

        Mockito.when(this.fileRepository.findByPathToFile(pathToFile)).thenReturn(fileOptional);

        final File fileToSave = new File();
        fileToSave.setPathToFile(pathToFile);
        fileToSave.setEndOfLife(endOfLife);
        this.fileHandlingService.updateEndOfLife(pathToFile, endOfLife);
        Mockito.verify(this.fileRepository, Mockito.times(1)).save(fileToSave);
    }

    @Test
    void deleteFileException() throws S3AccessException {
        final String pathToFile = "folder/test.txt";
        final String pathToFolder = "folder";
        final int expiresInMinutes = 5;

        Mockito.reset(this.s3Repository);
        Mockito.when(this.s3Repository.getFilepathesFromFolder(pathToFolder)).thenReturn(new HashSet<>());
        Assertions.assertThrows(FileExistanceException.class, () -> this.fileHandlingService.deleteFile(pathToFile, expiresInMinutes));
    }

    @Test
    void deleteFile() throws S3AccessException, FileExistanceException {
        final String pathToFile = "folder/test.txt";
        final String pathToFolder = "folder";
        final int expiresInMinutes = 5;

        Mockito.reset(this.s3Repository);
        Mockito.when(this.s3Repository.getFilepathesFromFolder(pathToFolder)).thenReturn(new HashSet<>(List.of(pathToFile)));
        this.fileHandlingService.deleteFile(pathToFile, expiresInMinutes);
        Mockito.verify(this.s3Repository, Mockito.times(1)).getPresignedUrlForFileDeletion(pathToFile, expiresInMinutes);
        Mockito.verify(this.s3Repository, Mockito.times(1)).getFilepathesFromFolder(pathToFolder);
    }

    @Test
    void deleteFileInternal() throws S3AccessException {
        final String pathToFile = "folder/test.txt";
        this.fileHandlingService.deleteFile(pathToFile);
        Mockito.verify(this.fileRepository, Mockito.times(1)).deleteByPathToFile(pathToFile);
        Mockito.verify(this.s3Repository, Mockito.times(1)).deleteFile(pathToFile);

    }

    @Test
    void getPathToFolder() {
        assertThat(this.fileHandlingService.getPathToFolder("folder/file.txt"), is("folder"));
        assertThat(this.fileHandlingService.getPathToFolder("folder/subfolder/file.txt"), is("folder/subfolder"));
        assertThat(this.fileHandlingService.getPathToFolder("file.txt"), is(""));
        assertThat(this.fileHandlingService.getPathToFolder(""), is(""));
    }

}
