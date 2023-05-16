package io.muenchendigital.digiwf.task.service.application.usecase;

import io.muenchendigital.digiwf.s3.integration.client.repository.DocumentStorageFolderRepository;
import io.muenchendigital.digiwf.task.service.application.port.in.WorkOnTaskFile;
import io.muenchendigital.digiwf.task.service.application.port.out.file.PresignedUrlPort;
import io.muenchendigital.digiwf.task.service.domain.PresignedUrlAction;
import io.muenchendigital.digiwf.task.service.domain.ProcessConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class WorkOnTaskFileUseCase implements WorkOnTaskFile {

    public static final String FILEPATH_DELIMITER = ";";
    public static final String ERRTEXT_ILLEGAL_ACCESS = "No access to defined property";
    protected final DocumentStorageFolderRepository documentStorageFolderRepository;

    private final PresignedUrlPort presignedUrlPort;

    private final WorkOnUserTaskUseCase workOnUserTaskUseCase;


    public List<String> getFileNames(final String taskId, final String filePath, final String userId, final List<String> groups) {

        if (!workOnUserTaskUseCase.hasAccess(taskId, userId, groups)) {
            throw new AccessDeniedException("403 returned");
        }

        //this.checkReadAccess(taskId, filePath);

        final String fileContext = this.getFileContext(taskId);

        try {
            String documentStorageUrl = this.getDocumentStorageUrl(taskId);
            if (!documentStorageUrl.isEmpty()) {
                return this.removeFolderFromPaths(this.documentStorageFolderRepository.getAllFilesInFolderRecursively(fileContext + "/" + filePath, documentStorageUrl).block());
            }
            return this.removeFolderFromPaths(this.documentStorageFolderRepository.getAllFilesInFolderRecursively(fileContext + "/" + filePath).block());
        } catch (final Exception ex) {
            log.error("Getting all files of folder {} failed: {}", filePath, ex);
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, String.format("Getting all files of folder %s failed", filePath));
        }
    }

    public String getPresignedUrl(final PresignedUrlAction action, final String taskId, final String filePath, final String fileName, final String userId, final List<String> groups) {

        if (!this.workOnUserTaskUseCase.hasAccess(taskId, userId, groups)) {
            throw new AccessDeniedException("403 returned");
        }

        /*
        if (action.equals(PresignedUrlAction.GET)) {
            this.checkReadAccess(taskId, filePath);
        } else {
            this.checkWriteAccess(taskId, filePath);
        }
        */

        final String fileContext = this.getFileContext(taskId);

        String documentStorageUrl = this.getDocumentStorageUrl(taskId);
        String pathToFile = fileContext + "/" + filePath + "/" + fileName;

        if (!documentStorageUrl.isEmpty()) {
            return presignedUrlPort.getPresignedUrl(documentStorageUrl, pathToFile, 5, action);
        }
        return presignedUrlPort.getPresignedUrl(pathToFile, 5, action);
    }

    protected String getDocumentStorageUrl(final String taskId) {
        return this.workOnUserTaskUseCase.getVariableOfUserTask(taskId, ProcessConstants.PROCESS_S3_SYNC_CONFIG);
    }

    private String getFileContext(final String taskId) {

        String fileContext =  this.workOnUserTaskUseCase.getVariableOfUserTask(taskId, ProcessConstants.PROCESS_FILE_CONTEXT);

        if (!fileContext.isEmpty()) {
            return fileContext;
        }
        //throw  new NoFileContextException("No file context found for task");
        return null;
    }

//    private void checkReadAccess(final String identifier, final String filePath) {
//        try {
//            this.checkAccess(identifier, filePath, ProcessTaskConstants.FILE_PATHS);
//        } catch (final IllegalResourceAccessException ex) {
//            this.checkAccess(identifier, filePath, ProcessTaskConstants.FILE_PATHS_READONLY);
//        }
//    }
//
//    private void checkWriteAccess(final String identifier, final String filePath) {
//        this.checkAccess(identifier, filePath, ProcessTaskConstants.FILE_PATHS);
//    }
//
//    private void checkAccess(final String identifier, final String filePath, final String variable) {
//
//
//        final String filePaths = this.workOnUserTaskUseCase.getVariableOfUserTask(identifier, variable);
//        if (filePaths.isEmpty()) {
//            throw new IllegalResourceAccessException(ERRTEXT_ILLEGAL_ACCESS);
//        }
//        Arrays.stream(filePaths.split(FILEPATH_DELIMITER))
//                .filter(filePath::startsWith)
//                .findFirst()
//                .orElseThrow(() -> new IllegalResourceAccessException(ERRTEXT_ILLEGAL_ACCESS));
//    }

    private List<String> removeFolderFromPaths(final Set<String> fileList) {
        return fileList.stream()
                .map(file -> file.substring(file.lastIndexOf("/") + 1))
                .collect(Collectors.toList());
    }



}
