/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.humantask.api.resource;

import de.muenchen.oss.digiwf.humantask.domain.service.HumanTaskFileService;
import de.muenchen.oss.digiwf.shared.file.presignedUrlAdapters.PresignedUrlAction;
import de.muenchen.oss.digiwf.shared.security.AppAuthenticationProvider;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * API to perform actions on human task files
 *
 * @author externer.dl.horn
 */
@RestController
@Transactional
@RequestMapping("/rest/task/file")
@RequiredArgsConstructor
@Tag(name = "HumanTaskFileRestController", description = "API to perform actions on human task files")
public class HumanTaskFileRestController {

    private final HumanTaskFileService humanTaskFileService;
    private final AppAuthenticationProvider authenticationProvider;


    /**
     * Get file names for task and fieldKey.
     *
     * @param taskId Id of the human task
     * @param filePath    Key of the field in which the base path is saved
     * @return file names
     */
    @GetMapping("/{taskId}")
    public ResponseEntity<List<String>> getFileNames(@PathVariable final String taskId, @RequestParam final String filePath) {
        final List<String> fileNames = this.humanTaskFileService.getFileNames(
                taskId,
                filePath,
                this.authenticationProvider.getCurrentUserId(),
                this.authenticationProvider.getCurrentUserGroups());
        return ResponseEntity.ok(fileNames);
    }

    /**
     * Get a presigned url to load a file for a specific field key and file name.
     *
     * @param taskId   Id of the human task
     * @param filePath      Key of the field in which the base path is saved
     * @param fileName Name of the file
     * @return presignedUrl
     */
    @GetMapping("/{taskId}/{fileName}")
    public ResponseEntity<String> getPresignedUrlForFileDownload(@PathVariable final String taskId, @PathVariable final String fileName, @RequestParam final String filePath) {
        final String presignedUrl = this.humanTaskFileService.getPresignedUrl(
                PresignedUrlAction.GET,
                taskId,
                filePath,
                fileName,
                this.authenticationProvider.getCurrentUserId(),
                this.authenticationProvider.getCurrentUserGroups());
        return ResponseEntity.ok(presignedUrl);
    }

    /**
     * Get a presigned url to upload a file for a specific field key and file name.
     *
     * @param taskId   Id of the human task
     * @param filePath      Key of the field in which the base path is saved
     * @param filename Name of the file
     * @return presignedUrl
     */
    @PostMapping("/{taskId}/{filename}")
    public ResponseEntity<String> getPresignedUrlForFileUpload(@PathVariable final String taskId, @PathVariable final String filename, @RequestParam final String filePath) {
        final String presignedUrls = this.humanTaskFileService.getPresignedUrl(
                PresignedUrlAction.POST,
                taskId,
                filePath,
                filename,
                this.authenticationProvider.getCurrentUserId(),
                this.authenticationProvider.getCurrentUserGroups());
        return ResponseEntity.ok(presignedUrls);
    }

    /**
     * Get a presigned url to delete file for a specific field key and file name.
     *
     * @param taskId   Id of the human task
     * @param filePath      Key of the field in which the base path is saved
     * @param filename Name of the file
     * @return presignedUrl
     */
    //TODO I guess this should be only one url?
    @DeleteMapping("/{taskId}/{filename}")
    public ResponseEntity<String> getPresignedUrlForFileDeletion(@PathVariable final String taskId, @PathVariable final String filename, @RequestParam final String filePath) {
        final String presignedUrl = this.humanTaskFileService.getPresignedUrl(
                PresignedUrlAction.DELETE,
                taskId,
                filePath,
                filename,
                this.authenticationProvider.getCurrentUserId(),
                this.authenticationProvider.getCurrentUserGroups());
        return ResponseEntity.ok(presignedUrl);
    }

}

