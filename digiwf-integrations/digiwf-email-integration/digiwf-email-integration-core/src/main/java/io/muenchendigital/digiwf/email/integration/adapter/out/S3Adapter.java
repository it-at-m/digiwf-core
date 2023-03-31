package io.muenchendigital.digiwf.email.integration.adapter.out;

import io.muenchendigital.digiwf.email.integration.application.port.out.LoadMailAttachmentPort;
import io.muenchendigital.digiwf.email.integration.model.FileAttachment;
import io.muenchendigital.digiwf.email.integration.model.PresignedUrl;
import io.muenchendigital.digiwf.message.process.api.error.BpmnError;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.tika.Tika;
import org.springframework.stereotype.Component;

import javax.mail.util.ByteArrayDataSource;
import java.io.InputStream;
import java.net.URL;

@Slf4j
@Component
public class S3Adapter implements LoadMailAttachmentPort {

    @Override
    public FileAttachment loadAttachment(final PresignedUrl attachment) throws BpmnError {
        try {
            // TODO use digiwf s3 client to download the file instead of the stream below

            // download file from s3
            final URL binaryFile = new URL(attachment.getUrl());
            final Tika tika = new Tika();
            final InputStream fileInputStream = binaryFile.openStream();
            final ByteArrayDataSource file = new ByteArrayDataSource(fileInputStream, tika.detect(binaryFile));
            final String fileName = StringUtils.substringAfterLast(attachment.getPath(), "/");

            return new FileAttachment(fileName, file);
        } catch (final java.io.IOException ex) {
            log.error("An attachment could not be loaded: {}", attachment);
            throw new BpmnError("400", "An attachment could not be loaded: " + attachment);
        }
    }
}
