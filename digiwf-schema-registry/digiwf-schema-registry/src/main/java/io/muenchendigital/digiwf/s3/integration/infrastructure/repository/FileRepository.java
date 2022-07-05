package io.muenchendigital.digiwf.s3.integration.infrastructure.repository;

import io.muenchendigital.digiwf.s3.integration.infrastructure.entity.File;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

public interface FileRepository extends PagingAndSortingRepository<File, UUID> {

    int LENGTH_PATH_TO_FILE = 1024;

    void deleteByPathToFile(final String pathToFile);

    Stream<File> findByPathToFileStartingWith(final String pathToFolder);

    Optional<File> findByPathToFile(final String pathToFile);

    Stream<File> findAllByEndOfLifeNotNullAndEndOfLifeBefore(final LocalDate date);

    Stream<File> findAllByEndOfLifeIsNull();

}
