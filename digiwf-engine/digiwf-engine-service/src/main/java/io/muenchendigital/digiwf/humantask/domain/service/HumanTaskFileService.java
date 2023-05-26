/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.humantask.domain.service;

import io.muenchendigital.digiwf.humantask.process.ProcessTaskConstants;
import io.muenchendigital.digiwf.process.config.process.ProcessConfigFunctions;
import io.muenchendigital.digiwf.process.instance.process.ProcessConstants;
import io.muenchendigital.digiwf.s3.integration.client.repository.DocumentStorageFolderRepository;
import io.muenchendigital.digiwf.shared.exception.IllegalResourceAccessException;
import io.muenchendigital.digiwf.shared.exception.NoFileContextException;
import io.muenchendigital.digiwf.shared.file.AbstractFileService;
import io.muenchendigital.digiwf.shared.file.presignedUrlAdapters.PresignedUrlAction;
import io.muenchendigital.digiwf.shared.file.presignedUrlAdapters.PresignedUrlAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Service to handle files in tasks in DigiWF.
 *
 * @author externer.dl.horn
 */
@Slf4j
@Service
public class HumanTaskFileService extends AbstractFileService {

    private final HumanTaskDataService humanTaskDataService;
    private final HumanTaskService humanTaskService;

    public HumanTaskFileService(
            final DocumentStorageFolderRepository documentStorageFolderRepository,
            final List<PresignedUrlAdapter> presignedUrlAdapters,
            final ProcessConfigFunctions processConfigFunctions,
            final HumanTaskService humanTaskService,
            final HumanTaskDataService humanTaskDataService
    ) {
        super(documentStorageFolderRepository, presignedUrlAdapters, processConfigFunctions);
        this.humanTaskDataService = humanTaskDataService;
        this.humanTaskService = humanTaskService;
    }

    public List<String> getFileNames(final String taskId, final String filePath, final String userId, final List<String> groups) {
        if (!this.humanTaskService.hasAccess(taskId, userId, groups)) {
            throw new AccessDeniedException("403 returned");
        }

        this.checkReadAccess(taskId, filePath);
        final String fileContext = this.getFileContext(taskId);
        return super.getFileNames(filePath, fileContext, this.getDocumentStorageUrl(taskId));
    }

    public String getPresignedUrl(final PresignedUrlAction action, final String taskId, final String filePath, final String fileName, final String userId, final List<String> groups) {
        if (!this.humanTaskService.hasAccess(taskId, userId, groups)) {
            throw new AccessDeniedException("403 returned");
        }

        if (action.equals(PresignedUrlAction.GET)) {
            this.checkReadAccess(taskId, filePath);
        } else {
            this.checkWriteAccess(taskId, filePath);
        }

        final String fileContext = this.getFileContext(taskId);
        return super.getPresignedUrl(action, fileContext + "/" + filePath + "/" + fileName, this.getDocumentStorageUrl(taskId));
    }

    //---------------------------------------- helper methods ---------------------------------------- //

    @Override
    protected Optional<String> getDocumentStorageUrl(final String taskId) {
        return this.humanTaskDataService.getVariable(taskId, ProcessConstants.PROCESS_S3_SYNC_CONFIG);
    }

    private String getFileContext(final String taskId) {
        return this.humanTaskDataService.getFileContext(taskId)
                .orElseThrow(() -> new NoFileContextException("No file context found for task"));
    }

    private void checkReadAccess(final String identifier, final String filePath) {
        try {
            this.checkAccess(identifier, filePath, ProcessTaskConstants.FILE_PATHS);
        } catch (final IllegalResourceAccessException ex) {
            this.checkAccess(identifier, filePath, ProcessTaskConstants.FILE_PATHS_READONLY);
        }
    }

    private void checkWriteAccess(final String identifier, final String filePath) {
        this.checkAccess(identifier, filePath, ProcessTaskConstants.FILE_PATHS);
    }

    private void checkAccess(final String identifier, final String filePath, final String variable) {


        final Optional<String> filePaths = this.humanTaskDataService.getVariable(identifier, variable);
        if (filePaths.isEmpty()) {
            throw new IllegalResourceAccessException(ERRTEXT_ILLEGAL_ACCESS);
        }
        Arrays.stream(filePaths.get().split(FILEPATH_DELIMITER))
                .filter(filePath::startsWith)
                .findFirst()
                .orElseThrow(() -> new IllegalResourceAccessException(ERRTEXT_ILLEGAL_ACCESS));
    }
}
