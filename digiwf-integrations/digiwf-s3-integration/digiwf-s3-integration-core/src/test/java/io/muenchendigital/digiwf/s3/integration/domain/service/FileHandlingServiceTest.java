package io.muenchendigital.digiwf.s3.integration.domain.service;

import io.minio.http.Method;
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

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
    void getPresignedUrl() {
        final String pathToFile = "folder/test.txt";
        final int expiresInMinutes = 5;
        final List<Method> actions = List.of(Method.GET, Method.POST, Method.PUT, Method.DELETE);

        final String examplePresignedUrl = "some-presigned-url";

        actions.forEach(action -> {
            try {
                Mockito.when(this.s3Repository.getPresignedUrl(pathToFile, action, expiresInMinutes)).thenReturn(examplePresignedUrl);

                final PresignedUrl presignedUrl = this.fileHandlingService.getPresignedUrl(pathToFile, action, expiresInMinutes);

                Assertions.assertEquals(examplePresignedUrl, presignedUrl.getUrl());
                Assertions.assertEquals(action.toString(), presignedUrl.getAction());
                Assertions.assertEquals(pathToFile, presignedUrl.getPath());
            } catch (final S3AccessException e) {
                Assertions.fail(e.getMessage());
            }
        });

    }

    @Test
    void getPresignedUrlForFile() throws S3AccessException, FileExistanceException {
        final String pathToFile = "folder/test.txt";
        final int expiresInMinutes = 5;
        final List<Method> actions = List.of(Method.GET, Method.PUT, Method.DELETE);

        final String examplePresignedUrl = "some-presigned-url";

        // GET, PUT, DELETE
        actions.forEach(action -> {
            try {
                Mockito.when(this.s3Repository.getFilePathsFromFolder(pathToFile)).thenReturn(Set.of(pathToFile));
                Mockito.when(this.s3Repository.getPresignedUrl(pathToFile, action, expiresInMinutes)).thenReturn(examplePresignedUrl);

                final List<PresignedUrl> presignedUrls = this.fileHandlingService.getPresignedUrls(List.of(pathToFile), action, expiresInMinutes);

                Assertions.assertEquals(1, presignedUrls.size());
                presignedUrls.forEach(presignedUrl -> {
                    Assertions.assertEquals(presignedUrl.getUrl(), examplePresignedUrl);
                    Assertions.assertEquals(presignedUrl.getAction(), action.toString());
                    Assertions.assertEquals(presignedUrl.getPath(), pathToFile);
                });
                Mockito.reset();
            } catch (final FileExistanceException | S3AccessException e) {
                Assertions.fail(e.getMessage());
            }
        });

        // POST
        // special case POST is converted to PUT
        Mockito.when(this.s3Repository.getPresignedUrl(pathToFile, Method.PUT, expiresInMinutes)).thenReturn(examplePresignedUrl);

        final List<PresignedUrl> presignedUrls = this.fileHandlingService.getPresignedUrls(List.of(pathToFile), Method.POST, expiresInMinutes);

        Assertions.assertEquals(1, presignedUrls.size());
        presignedUrls.forEach(presignedUrl -> {
            Assertions.assertEquals(presignedUrl.getUrl(), examplePresignedUrl);
            Assertions.assertEquals(presignedUrl.getAction(), Method.PUT.toString());
            Assertions.assertEquals(presignedUrl.getPath(), pathToFile);
        });
    }

    @Test
    void getPresignedUrlsForDirectory() throws S3AccessException, FileExistanceException {
        final String pathToDirectory = "folder/";
        final Set<String> files = Set.of("folder/test.txt", "folder/test1.txt");
        final int expiresInMinutes = 5;
        final List<Method> actions = List.of(Method.GET, Method.PUT, Method.DELETE);

        final String examplePresignedUrl = "some-presigned-url";

        // GET, PUT, DELETE
        actions.forEach(action -> {
            try {
                Mockito.when(this.s3Repository.getFilePathsFromFolder(pathToDirectory)).thenReturn(files);
                for (final String file : files) {
                    Mockito.when(this.s3Repository.getPresignedUrl(file, action, expiresInMinutes)).thenReturn(examplePresignedUrl);
                }

                final List<PresignedUrl> presignedUrls = this.fileHandlingService.getPresignedUrls(List.of(pathToDirectory), action, expiresInMinutes);

                Assertions.assertEquals(2, presignedUrls.size());
                presignedUrls.forEach(presignedUrl -> {
                    Assertions.assertEquals(presignedUrl.getUrl(), examplePresignedUrl);
                    Assertions.assertEquals(presignedUrl.getAction(), action.toString());
                    Assertions.assertTrue(files.stream().anyMatch(file -> file.equals(presignedUrl.getPath())));
                });
                Mockito.reset();
            } catch (final FileExistanceException | S3AccessException e) {
                Assertions.fail(e.getMessage());
            }
        });

        // POST
        // special case POST is converted to PUT
        Mockito.when(this.s3Repository.getPresignedUrl(pathToDirectory, Method.PUT, expiresInMinutes)).thenReturn(examplePresignedUrl);

        final List<PresignedUrl> presignedUrls = this.fileHandlingService.getPresignedUrls(List.of(pathToDirectory), Method.POST, expiresInMinutes);

        Assertions.assertEquals(1, presignedUrls.size());
        presignedUrls.forEach(presignedUrl -> {
            Assertions.assertEquals(presignedUrl.getUrl(), examplePresignedUrl);
            Assertions.assertEquals(presignedUrl.getAction(), Method.PUT.toString());
            Assertions.assertEquals(presignedUrl.getPath(), pathToDirectory);
        });
    }

    @Test
    void getPresignedUrlsForMultipleFiles() throws S3AccessException, FileExistanceException {
        final List<String> pathToFiles = List.of("folder/first.txt", "folder/second.txt", "folder/third.txt");
        final int expiresInMinutes = 5;
        final List<Method> actions = List.of(Method.GET, Method.PUT, Method.DELETE);

        final String examplePresignedUrl = "some-presigned-url";

        // GET, PUT, DELETE
        actions.forEach(action -> {
            try {
                for (String file : pathToFiles) {
                    Mockito.when(this.s3Repository.getFilePathsFromFolder(file)).thenReturn(Set.of(file));
                    Mockito.when(this.s3Repository.getPresignedUrl(file, action, expiresInMinutes)).thenReturn(examplePresignedUrl);
                }

                final List<PresignedUrl> presignedUrls = this.fileHandlingService.getPresignedUrls(pathToFiles, action, expiresInMinutes);

                Assertions.assertEquals(3, presignedUrls.size());
                presignedUrls.forEach(presignedUrl -> {
                    Assertions.assertEquals(presignedUrl.getUrl(), examplePresignedUrl);
                    Assertions.assertEquals(presignedUrl.getAction(), action.toString());
                });
                Mockito.reset();
            } catch (final FileExistanceException | S3AccessException e) {
                Assertions.fail(e.getMessage());
            }
        });

        // POST
        // special case POST is converted to PUT
        for (String file : pathToFiles) {
            Mockito.when(this.s3Repository.getPresignedUrl(file, Method.PUT, expiresInMinutes)).thenReturn(examplePresignedUrl);
        }

        final List<PresignedUrl> presignedUrls = this.fileHandlingService.getPresignedUrls(pathToFiles, Method.POST, expiresInMinutes);

        Assertions.assertEquals(3, presignedUrls.size());
        presignedUrls.forEach(presignedUrl -> {
            Assertions.assertEquals(presignedUrl.getUrl(), examplePresignedUrl);
            Assertions.assertEquals(presignedUrl.getAction(), Method.PUT.toString());
            Assertions.assertTrue(pathToFiles.stream().anyMatch(file -> file.equals(presignedUrl.getPath())));
        });
    }

    @Test
    void getFileException() throws Exception {
        final String pathToFile = "folder/test.txt";
        final String pathToFolder = "folder";
        final int expiresInMinutes = 5;
        Mockito.when(this.s3Repository.getFilePathsFromFolder(pathToFolder)).thenReturn(new HashSet<>());
        Assertions.assertThrows(FileExistanceException.class, () -> this.fileHandlingService.getFile(pathToFile, expiresInMinutes));
    }

    @Test
    void getFile() throws S3AccessException, FileExistanceException {
        final String pathToFile = "folder/test.txt";
        final String pathToFolder = "folder";
        final int expiresInMinutes = 5;
        final String presignedUrl = "THE_PRESIGNED_URL";

        Mockito.when(this.s3Repository.getFilePathsFromFolder(pathToFolder)).thenReturn(new HashSet<>(List.of(pathToFile)));
        Mockito.when(this.s3Repository.getPresignedUrl(pathToFile, Method.GET, expiresInMinutes)).thenReturn(presignedUrl);

        final PresignedUrl result = this.fileHandlingService.getFile(pathToFile, expiresInMinutes);

        final PresignedUrl expected = new PresignedUrl(presignedUrl, pathToFile, "GET");

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

        Mockito.when(this.s3Repository.getFilePathsFromFolder(pathToFolder)).thenReturn(new HashSet<>(List.of(pathToFile)));
        Assertions.assertThrows(FileExistanceException.class, () -> this.fileHandlingService.saveFile(fileData));
        // happy path is tested in updateFile
    }

    @Test
    void updateFile() throws S3AccessException {
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
        Mockito.verify(this.s3Repository, Mockito.times(1)).getPresignedUrl(pathToFile, Method.PUT, fileData.getExpiresInMinutes());

        // File already in Database with older end of life
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
        Mockito.verify(this.s3Repository, Mockito.times(1)).getPresignedUrl(pathToFile, Method.PUT, fileData.getExpiresInMinutes());

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
        Mockito.verify(this.s3Repository, Mockito.times(1)).getPresignedUrl(pathToFile, Method.PUT, fileData.getExpiresInMinutes());
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
        Mockito.when(this.s3Repository.getFilePathsFromFolder(pathToFolder)).thenReturn(new HashSet<>());
        Assertions.assertThrows(FileExistanceException.class, () -> this.fileHandlingService.deleteFile(pathToFile, expiresInMinutes));
    }

    @Test
    void deleteFile() throws S3AccessException, FileExistanceException {
        final String pathToFile = "folder/test.txt";
        final String pathToFolder = "folder";
        final int expiresInMinutes = 5;

        Mockito.reset(this.s3Repository);
        Mockito.when(this.s3Repository.getFilePathsFromFolder(pathToFolder)).thenReturn(new HashSet<>(List.of(pathToFile)));
        this.fileHandlingService.deleteFile(pathToFile, expiresInMinutes);
        Mockito.verify(this.s3Repository, Mockito.times(1)).getPresignedUrl(pathToFile, Method.DELETE, expiresInMinutes);
        Mockito.verify(this.s3Repository, Mockito.times(1)).getFilePathsFromFolder(pathToFolder);
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
