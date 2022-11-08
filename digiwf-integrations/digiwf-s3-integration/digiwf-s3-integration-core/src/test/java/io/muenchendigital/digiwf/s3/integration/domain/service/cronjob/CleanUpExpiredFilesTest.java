package io.muenchendigital.digiwf.s3.integration.domain.service.cronjob;

import io.muenchendigital.digiwf.s3.integration.domain.service.FileHandlingService;
import io.muenchendigital.digiwf.s3.integration.infrastructure.entity.File;
import io.muenchendigital.digiwf.s3.integration.infrastructure.exception.S3AccessException;
import io.muenchendigital.digiwf.s3.integration.infrastructure.exception.S3AndDatabaseAsyncException;
import io.muenchendigital.digiwf.s3.integration.infrastructure.repository.FileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.time.LocalDate;
import java.util.stream.Stream;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CleanUpExpiredFilesTest {

    @Mock
    private FileRepository fileRepository;

    @Mock
    private FileHandlingService fileHandlingService;

    private CleanUpExpiredFiles cleanUpExpiredFiles;

    @BeforeEach
    public void beforeEach() {
        this.cleanUpExpiredFiles = new CleanUpExpiredFiles(this.fileRepository, this.fileHandlingService);
    }

    @Test
    void cleanUp() throws S3AccessException, S3AndDatabaseAsyncException {
        final var file1 = new File();
        file1.setPathToFile("file1");
        file1.setEndOfLife(LocalDate.now().minusYears(1));
        final var file2 = new File();
        file2.setPathToFile("file2");
        file2.setEndOfLife(LocalDate.now().minusYears(1));
        final var file3 = new File();
        file3.setPathToFile("file3");
        file3.setEndOfLife(LocalDate.now().minusYears(1));
        final Stream<File> folderStream = Stream.of(file1, file2, file3);

        Mockito.when(this.fileRepository.findAllByEndOfLifeNotNullAndEndOfLifeBefore(Mockito.any(LocalDate.class)))
                .thenReturn(folderStream);
        this.cleanUpExpiredFiles.cleanUp();
        Mockito.verify(this.fileHandlingService, Mockito.times(3)).deleteFile(Mockito.anyString());
    }

}
