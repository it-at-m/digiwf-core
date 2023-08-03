/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.process.instance.domain.service;

import io.muenchendigital.digiwf.process.config.domain.model.ProcessConfig;
import io.muenchendigital.digiwf.process.config.domain.service.ProcessConfigService;
import io.muenchendigital.digiwf.process.config.process.ProcessConfigFunctions;
import io.muenchendigital.digiwf.process.instance.domain.model.ServiceInstance;
import de.muenchen.oss.digiwf.s3.integration.client.repository.DocumentStorageFolderRepository;
import io.muenchendigital.digiwf.shared.exception.IllegalResourceAccessException;
import io.muenchendigital.digiwf.shared.file.AbstractFileService;
import io.muenchendigital.digiwf.shared.file.presignedUrlAdapters.PresignedUrlAction;
import io.muenchendigital.digiwf.shared.file.presignedUrlAdapters.PresignedUrlAdapter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * Service to handle files in service instances in DigiWF.
 *
 * @author externer.dl.horn
 */
@Slf4j
@Service
public class ServiceInstanceFileService extends AbstractFileService {

    private final ProcessConfigService processConfigService;
    private final ServiceInstanceService serviceInstanceService;
    private final ServiceInstanceDataService serviceInstanceDataService;
    private final ServiceInstanceAuthService serviceInstanceAuthService;

    public ServiceInstanceFileService(
            final DocumentStorageFolderRepository documentStorageFolderRepository,
            final List<PresignedUrlAdapter> presignedUrlAdapters,
            final ServiceInstanceService serviceInstanceService,
            final ServiceInstanceDataService serviceInstanceDataService,
            final ServiceInstanceAuthService serviceInstanceAuthService,
            final ProcessConfigService processConfigService,
            final ProcessConfigFunctions processConfigFunctions
    ) {
        super(documentStorageFolderRepository, presignedUrlAdapters, processConfigFunctions);
        this.serviceInstanceService = serviceInstanceService;
        this.processConfigService = processConfigService;
        this.serviceInstanceDataService = serviceInstanceDataService;
        this.serviceInstanceAuthService = serviceInstanceAuthService;
    }

    public List<String> getFileNames(final String infoId, final String filePath, final String userId) {
        final ServiceInstance processInstance = this.getProcessInstanceId(infoId);
        final String processInstanceId = processInstance.getInstanceId();
        if (!this.serviceInstanceAuthService.hasAccess(processInstanceId, userId)) {
            throw new AccessDeniedException("403 returned");
        }

        this.checkReadAccess(processInstanceId, filePath);
        final String fileContext = this.getFileContext(processInstanceId);
        return super.getFileNames(filePath, fileContext, this.getDocumentStorageUrl(processInstance.getDefinitionKey()));
    }

    public String getPresignedUrl(final PresignedUrlAction action, final String infoId, final String filePath, final String fileName, final String userId) {
        final ServiceInstance processInstance = this.getProcessInstanceId(infoId);
        final String processInstanceId = processInstance.getInstanceId();
        if (!this.serviceInstanceAuthService.hasAccess(processInstanceId, userId)) {
            throw new AccessDeniedException("403 returned");
        }

        if (action.equals(PresignedUrlAction.GET)) {
            this.checkReadAccess(processInstanceId, filePath);
        } else {
            this.checkWriteAccess(processInstanceId, filePath);
        }

        final String fileContext = this.getFileContext(processInstanceId);
        return super.getPresignedUrl(action, fileContext + "/" + filePath + "/" + fileName, this.getDocumentStorageUrl(processInstance.getDefinitionKey()));
    }

    //---------------------------------------- helper methods ---------------------------------------- //

    private String getFileContext(final String instanceId) {
        return this.serviceInstanceDataService.getFileContext(instanceId);
    }

    private void checkReadAccess(final String identifier, final String filePath) {
        try {
            this.checkWriteAccess(identifier, filePath);
        } catch (final IllegalResourceAccessException ex) {
            final String defintionKey = this.getDefinitionKey(identifier);
            final String filePathsReadonly = this.processConfigService.getProcessConfig(defintionKey)
                    .map(ProcessConfig::getInstanceFilePathsReadonly)
                    .orElse(null);
            this.checkAccess(filePath, filePathsReadonly);
        }
    }

    private void checkWriteAccess(final String identifier, final String filePath) {
        final String defintionKey = this.getDefinitionKey(identifier);
        final String filePaths = this.processConfigService.getProcessConfig(defintionKey)
                .map(ProcessConfig::getInstanceFilePaths)
                .orElse(null);
        this.checkAccess(filePath, filePaths);
    }

    private void checkAccess(final String filePath, final String filePaths) {
        if (StringUtils.isEmpty(filePaths)) {
            throw new IllegalResourceAccessException(ERRTEXT_ILLEGAL_ACCESS);
        }

        Arrays.stream(filePaths.split(FILEPATH_DELIMITER))
                .filter(filePath::startsWith)
                .findFirst()
                .orElseThrow(() -> new IllegalResourceAccessException(ERRTEXT_ILLEGAL_ACCESS));
    }


    private String getDefinitionKey(final String instanceId) {
        return this.serviceInstanceService.getServiceInstanceByInstanceId(instanceId)
                .map(ServiceInstance::getDefinitionKey)
                .orElseThrow();
    }

    private ServiceInstance getProcessInstanceId(final String instanceId) {
        return this.serviceInstanceService.getServiceInstanceById(instanceId).orElseThrow();
    }

}
