package de.muenchen.oss.digiwf.s3.integration.adapter.in.streaming;

import de.muenchen.oss.digiwf.message.process.api.error.BpmnError;
import de.muenchen.oss.digiwf.message.process.api.error.IncidentError;
import de.muenchen.oss.digiwf.s3.integration.adapter.in.rest.mapper.PresignedUrlMapper;
import de.muenchen.oss.digiwf.s3.integration.application.port.in.*;
import de.muenchen.oss.digiwf.s3.integration.application.port.out.IntegrationOutPort;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.messaging.Message;

import java.util.Map;
import java.util.function.Consumer;

@Slf4j
@RequiredArgsConstructor
public class MessageProcessor {
    /**
     * Key in the result map.
     */
    public static final String RESULT_PRESIGNED_URLS = "presignedUrls";
    private static final String VALIDATION_ERROR_CODE = "VALIDATION_ERROR";
    private static final String FILE_DOES_NOT_EXIST_ERROR_CODE = "FILE_DOES_NOT_EXIST_ERROR";

    private final CreatePresignedUrlsInPort createPresignedUrlsInPort;
    private final FolderOperationsInPort folderOperationsInPort;
    private final FileOperationsInPort fileOperationsInPort;
    private final IntegrationOutPort integration;
    private final PresignedUrlMapper presignedUrlMapper;

    /**
     * Create pre-signed urls for the given path in {@link CreatePresignedUrlEvent}.
     * Pre-signed urls are created for all files inside a directory if the path is a directory.
     * The result is streamed to the digiwf-engine with a correlateMessage event.
     */
    public Consumer<Message<CreatePresignedUrlEvent>> createPresignedUrl() {
        return message -> {

            val headers = message.getHeaders();
            try {
                val presignedUrls = createPresignedUrlsInPort.createPresignedUrls(message.getPayload());
                Map<String, Object> result = Map.of(RESULT_PRESIGNED_URLS, this.presignedUrlMapper.models2Dtos(presignedUrls));
                integration.correlateProcessMessage(headers, result);

            } catch (ConstraintViolationException cve) {
                integration.handleBpmnError(headers, new BpmnError(VALIDATION_ERROR_CODE, cve.getMessage()));
            } catch (FileExistenceException fee) {
                integration.handleBpmnError(headers, new BpmnError(FILE_DOES_NOT_EXIST_ERROR_CODE, fee.getMessage()));
            } catch (FileSystemAccessException sae) {
                integration.handleIncident(headers, new IncidentError(sae.getMessage()));
            }
        };
    }

    public Consumer<Message<PathsDTO>> deletePaths() {
        return message -> {
            try {
                val payload = message.getPayload();
                log.info("Delete paths request: {}", payload);
                for (String path : payload.getPathsAsList()) {
                    final String fullPath = payload.getFileContext() + "/" + path;
                    if (fullPath.endsWith("/")) {
                        this.folderOperationsInPort.deleteFolder(fullPath);
                    } else {
                        this.fileOperationsInPort.deleteFile(fullPath);
                    }
                }
                integration.correlateProcessMessage(message.getHeaders(), Map.of());
            } catch (ConstraintViolationException cve) {
                integration.handleBpmnError(message.getHeaders(), new BpmnError(VALIDATION_ERROR_CODE, cve.getMessage()));
            } catch (FileExistenceException fee) {
                integration.handleBpmnError(message.getHeaders(), new BpmnError(FILE_DOES_NOT_EXIST_ERROR_CODE, fee.getMessage()));
            } catch (FileSystemAccessException sae) {
                integration.handleIncident(message.getHeaders(), new IncidentError(sae.getMessage()));
            }
        };
    }
}
