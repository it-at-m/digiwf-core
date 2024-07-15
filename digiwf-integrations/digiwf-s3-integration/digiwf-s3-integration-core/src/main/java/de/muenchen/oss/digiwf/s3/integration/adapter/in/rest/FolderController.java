package de.muenchen.oss.digiwf.s3.integration.adapter.in.rest;

import de.muenchen.oss.digiwf.s3.integration.adapter.in.rest.dto.FilesInFolderDto;
import de.muenchen.oss.digiwf.s3.integration.adapter.in.rest.dto.FileSizesInFolderDto;
import de.muenchen.oss.digiwf.s3.integration.adapter.in.rest.mapper.FileSizesInFolderMapper;
import de.muenchen.oss.digiwf.s3.integration.adapter.in.rest.mapper.FilesInFolderMapper;
import de.muenchen.oss.digiwf.s3.integration.application.port.in.FolderOperationsInPort;
import de.muenchen.oss.digiwf.s3.integration.domain.model.FileSizesInFolder;
import de.muenchen.oss.digiwf.s3.integration.domain.model.FilesInFolder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "FolderAPI", description = "API to interact with folders")
@RequestMapping("/folder")
public class FolderController {


    private final FolderOperationsInPort folderOperations;
    private final FilesInFolderMapper filesInFolderMapper;
    private final FileSizesInFolderMapper fileSizesInFolderMapper;

    @DeleteMapping
    @Operation(description = "Deletes the folder specified in the parameter")
    public ResponseEntity<Void> delete(@RequestParam @NotEmpty final String pathToFolder) {
        try {
            log.info("Received a request for deletion of a certain folder.");
            folderOperations.deleteFolder(pathToFolder);
            return ResponseEntity.noContent().build();
        } catch (final Exception exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
    }

    @GetMapping
    @Operation(description = "Returns all file paths for the folder specified in the parameter")
    public ResponseEntity<FilesInFolderDto> getAllFilesInFolderRecursively(@RequestParam @NotEmpty final String pathToFolder) {
        try {
            log.info("Received a request for getting file paths for a certain folder.");
            final FilesInFolder filesInFolder = folderOperations.getAllFilesInFolderRecursively(pathToFolder);
            final FilesInFolderDto filesInFolderDto = this.filesInFolderMapper.model2Dto(filesInFolder);
            return ResponseEntity.ok(filesInFolderDto);
        } catch (final Exception exception) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        }
    }

    @GetMapping("/size")
    @Operation(description = "Returns all file sizes for the folder specified in the parameter")
    public ResponseEntity<FileSizesInFolderDto> getAllFileSizesInFolderRecursively(@RequestParam @NotEmpty final String pathToFolder) {
        try {
            log.info("Received a request for getting file sizes for a certain folder.");
            final FileSizesInFolder fileSizesInFolder = folderOperations.getAllFileSizesInFolderRecursively(pathToFolder);
            final FileSizesInFolderDto filesizesInFolderDto = this.fileSizesInFolderMapper.model2Dto(fileSizesInFolder);
            return ResponseEntity.ok(filesizesInFolderDto);
        } catch (final Exception exception) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        }
    }

}
