package io.muenchendigital.digiwf.task.service.application.usecase;

import io.muenchendigital.digiwf.s3.integration.client.repository.DocumentStorageFolderRepository;
import io.muenchendigital.digiwf.task.service.application.port.in.WorkOnTaskFile;
import io.muenchendigital.digiwf.task.service.domain.PresignedUrlAction;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;

import java.util.List;

@Component
@RequiredArgsConstructor
public class WorkOnTaskFileUseCase implements WorkOnTaskFile {

    public static final String FILEPATH_DELIMITER = ";";
    public static final String ERRTEXT_ILLEGAL_ACCESS = "No access to defined property";
    protected final DocumentStorageFolderRepository documentStorageFolderRepository;

    public List<String> getFileNames(final String taskId, final String filePath, final String userId, final List<String> groups) {
        /*
        if (!this.humanTaskService.hasAccess(taskId, userId, groups)) {
            throw new AccessDeniedException("403 returned");
        }


        //this.checkReadAccess(taskId, filePath);
        //final String fileContext = this.getFileContext(taskId);
        try {
            if (documentStorageUrl.isPresent()) {
                return this.removeFolderFromPaths(this.documentStorageFolderRepository.getAllFilesInFolderRecursively(fileContext + "/" + filePath, documentStorageUrl.get()).block());
            }
            return this.removeFolderFromPaths(this.documentStorageFolderRepository.getAllFilesInFolderRecursively(fileContext + "/" + filePath).block());
        } catch (final Exception ex) {
            log.error("Getting all files of folder {} failed: {}", filePath, ex);
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, String.format("Getting all files of folder %s failed", filePath));
        }
        return super.getFileNames(filePath, fileContext, this.getDocumentStorageUrl(taskId));
        */
        return null;

    }

    public String getPresignedUrl(final PresignedUrlAction action, final String taskId, final String filePath, final String fileName, final String userId, final List<String> groups) {
        /*
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

         */
        return null;
    }


}
