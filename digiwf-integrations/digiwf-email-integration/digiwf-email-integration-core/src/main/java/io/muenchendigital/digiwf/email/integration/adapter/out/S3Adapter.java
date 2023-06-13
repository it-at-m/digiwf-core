package io.muenchendigital.digiwf.email.integration.adapter.out;

import io.muenchendigital.digiwf.email.integration.application.port.out.LoadMailAttachmentPort;
import io.muenchendigital.digiwf.email.integration.model.FileAttachment;
import io.muenchendigital.digiwf.email.integration.model.PresignedUrl;
import io.muenchendigital.digiwf.message.process.api.error.BpmnError;
import io.muenchendigital.digiwf.s3.integration.client.exception.DocumentStorageException;
import io.muenchendigital.digiwf.s3.integration.client.repository.transfer.S3FileTransferRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.tika.Tika;

import javax.mail.util.ByteArrayDataSource;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
@RequiredArgsConstructor
public class S3Adapter implements LoadMailAttachmentPort {

    private final S3FileTransferRepository s3FileTransferRepository;

    @Override
    public FileAttachment loadAttachment(final PresignedUrl attachment) throws BpmnError {
        try {
            final String fileName = StringUtils.substringAfterLast(attachment.getPath(), "/");
            // Note: Tika throws an IOException that is immediately caught and logged. It may be confusing but the
            // IOException that is logged can be ignored. See https://stackoverflow.com/q/66592801
            final Tika tika = new Tika();
            final InputStream inputStream = this.s3FileTransferRepository.getFileInputStream(attachment.getUrl());
            final ByteArrayDataSource file = new ByteArrayDataSource(inputStream, tika.detect(inputStream));
            return new FileAttachment(fileName, file);
        } catch (final DocumentStorageException e) {
            log.error("An attachment could not be loaded from presigned url: {}", attachment);
            throw new BpmnError("LOAD_FILE_FAILED", "An attachment could not be loaded from presigned url: " + attachment);
        } catch (final IOException e) {
            log.error("File type not supported of the attachment: {}", attachment);
            throw new BpmnError("FILE_TYPE_NOT_SUPPORTED", "File type not supported of the attachment: " + attachment);
        }
    }
}
