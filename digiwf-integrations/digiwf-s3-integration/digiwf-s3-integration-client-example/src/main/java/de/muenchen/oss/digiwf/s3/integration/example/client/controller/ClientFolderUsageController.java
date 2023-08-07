package de.muenchen.oss.digiwf.s3.integration.example.client.controller;

import de.muenchen.oss.digiwf.s3.integration.client.exception.DocumentStorageClientErrorException;
import de.muenchen.oss.digiwf.s3.integration.client.exception.DocumentStorageException;
import de.muenchen.oss.digiwf.s3.integration.client.exception.DocumentStorageServerErrorException;
import de.muenchen.oss.digiwf.s3.integration.client.exception.PropertyNotSetException;
import de.muenchen.oss.digiwf.s3.integration.client.repository.DocumentStorageFolderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/folder")
public class ClientFolderUsageController {

    public static final String FOLDER = UUID.randomUUID().toString();

    private final DocumentStorageFolderRepository documentStorageFolderRepository;

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFolder() throws DocumentStorageException, DocumentStorageClientErrorException, DocumentStorageServerErrorException, PropertyNotSetException {
        this.documentStorageFolderRepository.deleteFolder(FOLDER);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void getAllFilesInFolderRecursively() throws DocumentStorageException, DocumentStorageClientErrorException, DocumentStorageServerErrorException, PropertyNotSetException {
        this.documentStorageFolderRepository.getAllFilesInFolderRecursively(FOLDER).block().forEach(log::info);
    }

}
