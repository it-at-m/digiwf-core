package de.muenchen.oss.digiwf.cosys.integration.adapter.out;

import de.muenchen.oss.digiwf.cosys.integration.application.port.out.SaveFileToStorageOutPort;
import de.muenchen.oss.digiwf.cosys.integration.model.DocumentStorageUrl;
import de.muenchen.oss.digiwf.cosys.integration.model.GenerateDocument;
import de.muenchen.oss.digiwf.message.process.api.error.BpmnError;
import de.muenchen.oss.digiwf.s3.integration.client.exception.DocumentStorageClientErrorException;
import de.muenchen.oss.digiwf.s3.integration.client.exception.DocumentStorageException;
import de.muenchen.oss.digiwf.s3.integration.client.exception.DocumentStorageServerErrorException;
import de.muenchen.oss.digiwf.s3.integration.client.repository.transfer.S3FileTransferRepository;
import de.muenchen.oss.digiwf.s3.integration.client.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.unit.DataSize;

@Slf4j
@RequiredArgsConstructor
public class S3Adapter implements SaveFileToStorageOutPort {

    private static final String S3_FILE_SAVE_ERROR = "S3_FILE_SAVE_ERROR";
    private static final String S3_FILE_SIZE_ERROR = "S3_FILE_SAVE_ERROR";
    private final S3FileTransferRepository s3FileTransferRepository;
    private final FileService fileService;

    @Override
    public void saveDocumentInStorage(final GenerateDocument generateDocument, final byte[] data) {
        try {
            validateFileSize(data);
            for (final DocumentStorageUrl presignedUrl : generateDocument.getDocumentStorageUrls()) {
                if (presignedUrl.getAction().equalsIgnoreCase("POST")) {
                    this.s3FileTransferRepository.saveFile(presignedUrl.getUrl(), data);
                } else if (presignedUrl.getAction().equalsIgnoreCase("PUT")) {
                    this.s3FileTransferRepository.updateFile(presignedUrl.getUrl(), data);
                } else {
                    throw new BpmnError(S3_FILE_SAVE_ERROR, String.format("Document storage action %s is not supported.", presignedUrl.getAction()));
                }
            }
        } catch (final DocumentStorageClientErrorException | DocumentStorageServerErrorException |
                DocumentStorageException ex) {
            log.debug("Document could not be saved.", ex);
            throw new BpmnError(S3_FILE_SAVE_ERROR, ex.getMessage());
        }
    }

    private void validateFileSize(final byte[] data) {
        if (!fileService.isValidFileSize(data))
            throw new BpmnError(S3_FILE_SIZE_ERROR,
                    String.format("Invalid file size %d MB. Allowed are %d MB.", DataSize.ofBytes(data.length).toMegabytes(),
                            fileService.getMaxFileSize().toMegabytes()));
    }

}
