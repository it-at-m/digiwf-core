package io.muenchendigital.digiwf.s3.integration.api.controller;

import io.muenchendigital.digiwf.s3.integration.api.dto.FilesInFolderDto;
import io.muenchendigital.digiwf.s3.integration.api.mapper.FilesInFolderMapper;
import io.muenchendigital.digiwf.s3.integration.domain.model.FilesInFolder;
import io.muenchendigital.digiwf.s3.integration.domain.service.FolderHandlingService;
import io.muenchendigital.digiwf.s3.integration.infrastructure.repository.FileRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "FolderAPI", description = "API to interact with folders")
@RequestMapping("/folder")
public class FolderController {

    private final FolderHandlingService folderHandlingService;

    private final FilesInFolderMapper filesInFolderMapper;

    @DeleteMapping
    @Operation(description = "Deletes the folder specified in the parameter together with the corresponding database entry")
    public ResponseEntity<Void> delete(@RequestParam @NotEmpty @Size(max = FileRepository.LENGTH_PATH_TO_FILE) final String pathToFolder) {
        try {
            log.info("Received a request for deletion of a certain folder.");
            this.folderHandlingService.deleteFolder(pathToFolder);
            return ResponseEntity.noContent().build();
        } catch (final Exception exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
    }

    @GetMapping
    @Operation(description = "Returns all file paths for the folder specified in the parameter")
    public ResponseEntity<FilesInFolderDto> getAllFilesInFolderRecursively(@RequestParam @NotEmpty @Size(max = FileRepository.LENGTH_PATH_TO_FILE) final String pathToFolder) {
        try {
            log.info("Received a request for getting file paths for a certain folder.");
            final FilesInFolder filesInFolder = this.folderHandlingService.getAllFilesInFolderRecursively(pathToFolder);
            final FilesInFolderDto filesInFolderDto = this.filesInFolderMapper.model2Dto(filesInFolder);
            return ResponseEntity.ok(filesInFolderDto);
        } catch (final Exception exception) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        }
    }

}
