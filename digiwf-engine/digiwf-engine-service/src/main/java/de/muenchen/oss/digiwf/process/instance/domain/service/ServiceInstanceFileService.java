/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.process.instance.domain.service;

import de.muenchen.oss.digiwf.process.config.domain.model.ProcessConfig;
import de.muenchen.oss.digiwf.process.config.domain.service.ProcessConfigService;
import de.muenchen.oss.digiwf.process.config.process.ProcessConfigFunctions;
import de.muenchen.oss.digiwf.process.instance.domain.model.ServiceInstance;
import de.muenchen.oss.digiwf.s3.integration.client.exception.PropertyNotSetException;
import de.muenchen.oss.digiwf.s3.integration.client.repository.DocumentStorageFolderRepository;
import de.muenchen.oss.digiwf.s3.integration.client.service.S3StorageUrlProvider;
import de.muenchen.oss.digiwf.shared.exception.IllegalResourceAccessException;
import de.muenchen.oss.digiwf.shared.file.AbstractFileService;
import de.muenchen.oss.digiwf.shared.file.presignedUrlAdapters.PresignedUrlAction;
import de.muenchen.oss.digiwf.shared.file.presignedUrlAdapters.PresignedUrlAdapter;
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
    private final S3StorageUrlProvider s3StorageUrlProvider;

    public ServiceInstanceFileService(
            final DocumentStorageFolderRepository documentStorageFolderRepository,
            final List<PresignedUrlAdapter> presignedUrlAdapters,
            final ServiceInstanceService serviceInstanceService,
            final ServiceInstanceDataService serviceInstanceDataService,
            final ServiceInstanceAuthService serviceInstanceAuthService,
            final ProcessConfigService processConfigService,
            final S3StorageUrlProvider s3StorageUrlProvider
    ) {
        super(documentStorageFolderRepository, presignedUrlAdapters);
        this.serviceInstanceService = serviceInstanceService;
        this.processConfigService = processConfigService;
        this.serviceInstanceDataService = serviceInstanceDataService;
        this.serviceInstanceAuthService = serviceInstanceAuthService;
        this.s3StorageUrlProvider = s3StorageUrlProvider;
    }

    public List<String> getFileNames(final String infoId, final String filePath, final String userId) throws PropertyNotSetException {
        final ServiceInstance processInstance = this.getProcessInstanceId(infoId);
        final String processInstanceId = processInstance.getInstanceId();
        if (!this.serviceInstanceAuthService.hasAccess(processInstanceId, userId)) {
            throw new AccessDeniedException("403 returned");
        }

        this.checkReadAccess(processInstanceId, filePath);
        final String fileContext = this.getFileContext(processInstanceId);
        return super.getFileNames(filePath, fileContext, s3StorageUrlProvider.provideS3StorageUrl(processInstance.getDefinitionKey()));
    }

    public String getPresignedUrl(final PresignedUrlAction action, final String infoId, final String filePath, final String fileName, final String userId)
            throws PropertyNotSetException {
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
        return super.getPresignedUrl(action, fileContext + "/" + filePath + "/" + fileName, this.s3StorageUrlProvider.provideS3StorageUrl(processInstance.getDefinitionKey()));
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
