package de.muenchen.oss.digiwf.cosys.integration.adapter.out.s3;

import de.muenchen.oss.digiwf.cosys.integration.application.port.out.SaveFileToStorageOutPort;
import de.muenchen.oss.digiwf.cosys.integration.domain.model.DocumentStorageUrl;
import de.muenchen.oss.digiwf.message.process.api.error.BpmnError;
import de.muenchen.oss.digiwf.s3.integration.client.exception.DocumentStorageClientErrorException;
import de.muenchen.oss.digiwf.s3.integration.client.exception.DocumentStorageException;
import de.muenchen.oss.digiwf.s3.integration.client.exception.DocumentStorageServerErrorException;
import de.muenchen.oss.digiwf.s3.integration.client.repository.DocumentStorageFileRepository;
import de.muenchen.oss.digiwf.s3.integration.client.repository.transfer.S3FileTransferRepository;
import de.muenchen.oss.digiwf.s3.integration.client.service.FileService;
import de.muenchen.oss.digiwf.s3.integration.client.service.S3StorageUrlProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.util.unit.DataSize;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class S3Adapter implements SaveFileToStorageOutPort {

    private static final String S3_FILE_SAVE_ERROR = "S3_FILE_SAVE_ERROR";
    private static final String S3_FILE_SIZE_ERROR = "S3_FILE_SIZE_ERROR";
    private final S3FileTransferRepository s3FileTransferRepository;
    private final DocumentStorageFileRepository documentStorageFileRepository;
    private final FileService fileService;
    private final S3StorageUrlProvider s3DomainService;

    @Override
    public void saveDocumentInStorage(
            final List<DocumentStorageUrl> documentStorageUrls,
            final byte[] data
    ) {
        try {
            validateFileSize(data);
            for (final DocumentStorageUrl presignedUrl : documentStorageUrls) {
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

    @Override
    public void saveDocumentInStorage(
            final String fileContext,
            final String filePath,
            final byte[] data
    ) {
        final String s3Storage = s3DomainService.getDefaultDocumentStorageUrl();
        val fullFilePath = String.format("%s/%s", fileContext, filePath).replace("//", "/");
        try {
            validateFileSize(data);
            documentStorageFileRepository.saveFile(fullFilePath, data, 1, s3Storage);
        } catch (DocumentStorageException | DocumentStorageClientErrorException |
                 DocumentStorageServerErrorException e) {
            log.debug("Document could not be saved.", e);
            throw new BpmnError(S3_FILE_SAVE_ERROR, e.getMessage());
        }
    }

    private void validateFileSize(final byte[] data) {
        if (!fileService.isValidFileSize(data))
            throw new BpmnError(S3_FILE_SIZE_ERROR,
                    String.format("Invalid file size %d MB. Allowed are %d MB.", DataSize.ofBytes(data.length).toMegabytes(),
                            fileService.getMaxFileSize().toMegabytes()));
    }

}
