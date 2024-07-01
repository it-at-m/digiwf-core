package de.muenchen.oss.digiwf.email.integration.adapter.out.s3;

import de.muenchen.oss.digiwf.email.integration.application.port.out.LoadMailAttachmentOutPort;
import de.muenchen.oss.digiwf.email.integration.model.PresignedUrl;
import de.muenchen.oss.digiwf.email.model.FileAttachment;
import de.muenchen.oss.digiwf.message.process.api.error.BpmnError;
import de.muenchen.oss.digiwf.s3.integration.client.exception.DocumentStorageClientErrorException;
import de.muenchen.oss.digiwf.s3.integration.client.exception.DocumentStorageException;
import de.muenchen.oss.digiwf.s3.integration.client.exception.DocumentStorageServerErrorException;
import de.muenchen.oss.digiwf.s3.integration.client.repository.transfer.S3FileTransferRepository;
import de.muenchen.oss.digiwf.s3.integration.client.service.FileExtensionService;
import jakarta.mail.util.ByteArrayDataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
@RequiredArgsConstructor
public class S3Adapter implements LoadMailAttachmentOutPort {

    private final S3FileTransferRepository s3FileTransferRepository;
    private final FileExtensionService fileExtensionService;

    @Override
    public FileAttachment loadAttachment(final PresignedUrl attachment) throws BpmnError {
        try {
            final String fileName = StringUtils.substringAfterLast(attachment.getPath(), "/");
            final byte[] bytes = this.s3FileTransferRepository.getFile(attachment.getUrl());
            final String type = fileExtensionService.detectFileType(bytes);
            // Note: Create the ByteArrayDataSource with the bytes and the type to avoid auto type detection by ByteArrayDataSource
            // https://github.com/it-at-m/digiwf-core/issues/616
            final ByteArrayDataSource file = new ByteArrayDataSource(bytes, type);
            file.setName(fileName);
            return new FileAttachment(fileName, file);
        } catch (final DocumentStorageException | DocumentStorageServerErrorException |
                       DocumentStorageClientErrorException e) {
            log.debug("An attachment could not be loaded from presigned url: {}", attachment);
            throw new BpmnError("LOAD_FILE_FAILED", "An attachment could not be loaded from presigned url: " + attachment);
        }
    }
}
