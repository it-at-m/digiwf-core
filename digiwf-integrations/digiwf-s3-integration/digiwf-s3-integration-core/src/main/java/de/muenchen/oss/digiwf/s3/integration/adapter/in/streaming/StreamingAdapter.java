package de.muenchen.oss.digiwf.s3.integration.adapter.in.streaming;

import de.muenchen.oss.digiwf.message.common.MessageConstants;
import de.muenchen.oss.digiwf.message.process.api.ErrorApi;
import de.muenchen.oss.digiwf.message.process.api.ProcessApi;
import de.muenchen.oss.digiwf.message.process.api.error.BpmnError;
import de.muenchen.oss.digiwf.message.process.api.error.IncidentError;
import de.muenchen.oss.digiwf.s3.integration.adapter.in.rest.mapper.PresignedUrlMapper;
import de.muenchen.oss.digiwf.s3.integration.application.port.in.CreatePresignedUrlsInPort;
import de.muenchen.oss.digiwf.s3.integration.application.port.in.FileOperationsInPort;
import de.muenchen.oss.digiwf.s3.integration.application.port.in.FolderOperationsInPort;
import de.muenchen.oss.digiwf.s3.integration.domain.exception.FileExistenceException;
import de.muenchen.oss.digiwf.s3.integration.domain.exception.FileSystemAccessException;
import de.muenchen.oss.digiwf.s3.integration.domain.model.CreatePresignedUrlEvent;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.lang.NonNull;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

@Slf4j
@RequiredArgsConstructor
public class StreamingAdapter {
    /**
     * Key in the result map.
     */
    public static final String RESULT_PRESIGNED_URLS = "presignedUrls";
    private static final String VALIDATION_ERROR_CODE = "VALIDATION_ERROR";
    private static final String FILE_DOES_NOT_EXIST_ERROR_CODE = "FILE_DOES_NOT_EXIST_ERROR";

    private final ProcessApi processApi;
    private final ErrorApi errorApi;

    private final CreatePresignedUrlsInPort createPresignedUrlsInPort;
    private final FolderOperationsInPort folderOperationsInPort;
    private final FileOperationsInPort fileOperationsInPort;
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
                this.correlateProcessMessage(headers, result);
            } catch (ConstraintViolationException cve) {
                errorApi.handleBpmnError(headers, new BpmnError(VALIDATION_ERROR_CODE, cve.getMessage()));
            } catch (FileExistenceException fee) {
                errorApi.handleBpmnError(headers, new BpmnError(FILE_DOES_NOT_EXIST_ERROR_CODE, fee.getMessage()));
            } catch (FileSystemAccessException sae) {
                errorApi.handleIncident(headers, new IncidentError(sae.getMessage()));
            }
        };
    }

    public Consumer<Message<FilesDTO>> deleteFiles() {
        return message -> {
            try {
                val payload = message.getPayload();
                log.info("deleteFiles request: {}", payload);
                for (String path : payload.getFilePathsAsList()) {
                    final String fullPath = payload.getFileContext() + "/" + path;
                    if (fullPath.endsWith("/")) {
                        this.folderOperationsInPort.deleteFolder(fullPath);
                    } else {
                        this.fileOperationsInPort.deleteFile(fullPath);
                    }
                }
                this.correlateProcessMessage(message.getHeaders(), Map.of());
            } catch (ConstraintViolationException cve) {
                errorApi.handleBpmnError(message.getHeaders(), new BpmnError(VALIDATION_ERROR_CODE, cve.getMessage()));
            } catch (FileExistenceException fee) {
                errorApi.handleBpmnError(message.getHeaders(), new BpmnError(FILE_DOES_NOT_EXIST_ERROR_CODE, fee.getMessage()));
            } catch (FileSystemAccessException sae) {
                errorApi.handleIncident(message.getHeaders(), new IncidentError(sae.getMessage()));
            }
        };
    }

    public void correlateProcessMessage(@NonNull MessageHeaders headers, Map<String, Object> payload) {
        final String processInstanceId = Objects.requireNonNull(headers.get(MessageConstants.DIGIWF_PROCESS_INSTANCE_ID)).toString();
        final String integrationName = Objects.requireNonNull(headers.get(MessageConstants.DIGIWF_INTEGRATION_NAME)).toString();
        final String type = Objects.requireNonNull(headers.get(MessageConstants.TYPE)).toString();
        if (payload == null) {
            payload = new HashMap<>();
        }
        this.processApi.correlateMessage(processInstanceId, type, integrationName, payload);
    }
}
