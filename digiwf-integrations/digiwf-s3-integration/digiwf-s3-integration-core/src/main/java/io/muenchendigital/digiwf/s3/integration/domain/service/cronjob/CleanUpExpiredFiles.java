package io.muenchendigital.digiwf.s3.integration.domain.service.cronjob;

import io.muenchendigital.digiwf.s3.integration.domain.service.FileHandlingService;
import io.muenchendigital.digiwf.s3.integration.infrastructure.entity.File;
import io.muenchendigital.digiwf.s3.integration.infrastructure.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
public class CleanUpExpiredFiles {

    private final FileRepository fileRepository;

    private final FileHandlingService fileHandlingService;

    /**
     * Cronjob scheduled method which deletes all folders in the S3 storage and database
     * for which the {@link File#getEndOfLife()} attribute is exceeded.
     */
    public void cleanUp() {
        log.info("S3 and database clean up for expired files started.");
        this.fileRepository.findAllByEndOfLifeNotNullAndEndOfLifeBefore(LocalDate.now())
                .forEach(this::deleteFile);
        log.info("S3 and database clean up for expired files finished.");
    }

    private void deleteFile(final File file) {
        try {
            this.fileHandlingService.deleteFile(file.getPathToFile());
        } catch (final Exception exception) {
            log.error("Error during cleanup happened.", exception);
        }
    }

}
