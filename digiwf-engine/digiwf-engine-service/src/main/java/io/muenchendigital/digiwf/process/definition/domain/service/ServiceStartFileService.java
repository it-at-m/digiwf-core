/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.process.definition.domain.service;

import io.muenchendigital.digiwf.process.config.domain.model.ProcessConfig;
import io.muenchendigital.digiwf.process.config.domain.service.ProcessConfigService;
import io.muenchendigital.digiwf.process.config.process.ProcessConfigFunctions;
import io.muenchendigital.digiwf.process.definition.domain.model.StartContext;
import de.muenchen.oss.digiwf.s3.integration.client.repository.DocumentStorageFolderRepository;
import io.muenchendigital.digiwf.shared.exception.IllegalResourceAccessException;
import io.muenchendigital.digiwf.shared.exception.NoFileContextException;
import io.muenchendigital.digiwf.shared.file.AbstractFileService;
import io.muenchendigital.digiwf.shared.file.presignedUrlAdapters.PresignedUrlAction;
import io.muenchendigital.digiwf.shared.file.presignedUrlAdapters.PresignedUrlAdapter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * Service to handle files in service starts in DigiWF.
 *
 * @author externer.dl.horn
 */
@Slf4j
@Service
public class ServiceStartFileService extends AbstractFileService {

    private final ServiceStartContextService serviceStartContextService;
    private final ProcessConfigService processConfigService;

    public ServiceStartFileService(
            final DocumentStorageFolderRepository documentStorageFolderRepository,
            final ServiceStartContextService serviceStartContextService,
            final ProcessConfigService processConfigService,
            final List<PresignedUrlAdapter> presignedUrlAdapters,
            final ProcessConfigFunctions processConfigFunctions
    ) {
        super(documentStorageFolderRepository, presignedUrlAdapters, processConfigFunctions);
        this.serviceStartContextService = serviceStartContextService;
        this.processConfigService = processConfigService;
    }

    public List<String> getFileNames(final String definitionKey, final String filePath, final String userId, final List<String> groups) {
        this.checkReadAccess(definitionKey, filePath);
        final String fileContext = this.getFileContext(userId, definitionKey);
        return super.getFileNames(filePath, fileContext, this.getDocumentStorageUrl(definitionKey));
    }

    public String getPresignedUrl(final PresignedUrlAction action, final String definitionKey, final String filePath, final String fileName, final String userId, final List<String> groups) {
        if (action.equals(PresignedUrlAction.GET)) {
            this.checkReadAccess(definitionKey, filePath);
        } else {
            this.checkWriteAccess(definitionKey, filePath);
        }

        final String fileContext = this.getFileContext(userId, definitionKey);
        return super.getPresignedUrl(action, fileContext + "/" + filePath + "/" + fileName, this.getDocumentStorageUrl(definitionKey));
    }

    //---------------------------------------- helper methods ---------------------------------------- //

    private String getFileContext(final String userId, final String definitionKey) {
        return this.serviceStartContextService.getStartContext(userId, definitionKey)
                .map(StartContext::getFileContext)
                .orElseThrow(() -> new NoFileContextException("No file context found for task"));
    }

    private void checkReadAccess(final String identifier, final String filePath) {
        try {
            this.checkWriteAccess(identifier, filePath);
        } catch (final IllegalResourceAccessException ex) {
            final String filePathsReadonly = this.processConfigService.getProcessConfig(identifier)
                    .map(ProcessConfig::getFilePathsReadonly)
                    .orElse(null);
            if (StringUtils.isEmpty(filePathsReadonly)) {
                throw new IllegalResourceAccessException(ERRTEXT_ILLEGAL_ACCESS);
            }

            Arrays.stream(filePathsReadonly.split(FILEPATH_DELIMITER))
                    .filter(filePath::startsWith)
                    .findFirst()
                    .orElseThrow(() -> new IllegalResourceAccessException(ERRTEXT_ILLEGAL_ACCESS));
        }
    }

    private void checkWriteAccess(final String identifier, final String filePath) {

        final String filePaths = this.processConfigService.getProcessConfig(identifier)
                .map(ProcessConfig::getFilePaths)
                .orElse(null);
        if (StringUtils.isEmpty(filePaths)) {
            throw new IllegalResourceAccessException(ERRTEXT_ILLEGAL_ACCESS);
        }

        Arrays.stream(filePaths.split(FILEPATH_DELIMITER))
                .filter(filePath::startsWith)
                .findFirst()
                .orElseThrow(() -> new IllegalResourceAccessException(ERRTEXT_ILLEGAL_ACCESS));

    }

}
