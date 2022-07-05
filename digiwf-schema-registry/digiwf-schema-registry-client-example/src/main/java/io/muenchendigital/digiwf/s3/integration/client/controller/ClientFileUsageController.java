package io.muenchendigital.digiwf.s3.integration.client.controller;

import io.muenchendigital.digiwf.s3.integration.client.exception.DocumentStorageClientErrorException;
import io.muenchendigital.digiwf.s3.integration.client.exception.DocumentStorageException;
import io.muenchendigital.digiwf.s3.integration.client.exception.DocumentStorageServerErrorException;
import io.muenchendigital.digiwf.s3.integration.client.exception.PropertyNotSetException;
import io.muenchendigital.digiwf.s3.integration.client.repository.DocumentStorageFileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.time.LocalDate;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/file")
public class ClientFileUsageController {

    private static final String FILENAME = "cat.jpg";

    private static final String PATH_TO_FILE = ClientFolderUsageController.FOLDER + "/" + FILENAME;

    private final DocumentStorageFileRepository documentStorageFileRepository;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public void getFile() throws DocumentStorageException, DocumentStorageClientErrorException, DocumentStorageServerErrorException, IOException, PropertyNotSetException {
        final byte[] binaryFile = this.documentStorageFileRepository.getFile(
                PATH_TO_FILE,
                3
        );
        final File tmpFile = File.createTempFile("test", ".jpg");
        Files.write(tmpFile.toPath(), binaryFile);
        log.info("File downloaded to {}.", tmpFile.toPath());
    }

    @GetMapping("/inputstream")
    @ResponseStatus(HttpStatus.OK)
    public void getFileInputStream() throws DocumentStorageException, DocumentStorageClientErrorException, DocumentStorageServerErrorException, IOException, PropertyNotSetException {
        try (final InputStream fileInputStream = this.documentStorageFileRepository.getFileInputStream(
                PATH_TO_FILE,
                3
        )) {
            final File tmpFile = File.createTempFile("test-from-inputstream", ".jpg");
            Files.write(tmpFile.toPath(), fileInputStream.readAllBytes());
            log.info("File InputStream downloaded to {}.", tmpFile.toPath());
        };
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void saveFile() throws IOException, DocumentStorageException, DocumentStorageClientErrorException, DocumentStorageServerErrorException, PropertyNotSetException {
        final File file = ResourceUtils.getFile("classpath:files/cat.jpg");
        final byte[] binaryFile = Files.readAllBytes(file.toPath());
        this.documentStorageFileRepository.saveFile(
                PATH_TO_FILE,
                binaryFile,
                3,
                LocalDate.now().plusMonths(1)
        );
        log.info("File saved.");
    }

    @PostMapping("/inputstream")
    @ResponseStatus(HttpStatus.OK)
    public void saveFileInputStream() throws IOException, DocumentStorageException, DocumentStorageClientErrorException, DocumentStorageServerErrorException, PropertyNotSetException {
        final File file = ResourceUtils.getFile("classpath:files/cat.jpg");
        try (final InputStream inputStream = new FileInputStream(file)) {
            this.documentStorageFileRepository.saveFileInputStream(
                    PATH_TO_FILE,
                    inputStream,
                    3,
                    LocalDate.now().plusMonths(1)
            );
            log.info("File InputStream saved.");
        }
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void updateFile() throws IOException, DocumentStorageException, DocumentStorageClientErrorException, DocumentStorageServerErrorException, PropertyNotSetException {
        final File file = ResourceUtils.getFile("classpath:files/sunflower.jpg");
        final byte[] binaryFile = Files.readAllBytes(file.toPath());
        // Overwrite file on S3 with sunflower.jpg
        this.documentStorageFileRepository.updateFile(
                PATH_TO_FILE,
                binaryFile,
                3,
                LocalDate.now().plusMonths(2)
        );
        log.info("File updated.");
    }

    @PutMapping("/inputstream")
    @ResponseStatus(HttpStatus.OK)
    public void updateFileInputStream() throws IOException, DocumentStorageException, DocumentStorageClientErrorException, DocumentStorageServerErrorException, PropertyNotSetException {
        final File file = ResourceUtils.getFile("classpath:files/sunflower.jpg");
        try (final InputStream inputStream = new FileInputStream(file)) {
            // Overwrite file on S3 with sunflower.jpg
            this.documentStorageFileRepository.updateFileInputStream(
                    PATH_TO_FILE,
                    inputStream,
                    3,
                    LocalDate.now().plusMonths(2)
            );
            log.info("File InputStream updated.");
        }
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public void updateEndOfLife() throws DocumentStorageException, DocumentStorageClientErrorException, DocumentStorageServerErrorException, PropertyNotSetException {
        this.documentStorageFileRepository.updateEndOfLife(
                PATH_TO_FILE,
                LocalDate.now().plusMonths(999)
        );
        log.info("End of life for file updated.");
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFile() throws DocumentStorageException, DocumentStorageClientErrorException, DocumentStorageServerErrorException, PropertyNotSetException {
        this.documentStorageFileRepository.deleteFile(
                PATH_TO_FILE,
                3
        );
        log.info("File deleted.");
    }

}
