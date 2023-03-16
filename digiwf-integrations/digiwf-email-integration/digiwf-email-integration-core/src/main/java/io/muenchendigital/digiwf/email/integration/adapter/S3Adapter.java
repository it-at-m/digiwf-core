package io.muenchendigital.digiwf.email.integration.adapter;

import io.muenchendigital.digiwf.email.integration.application.dto.AttachmentDto;
import io.muenchendigital.digiwf.email.integration.application.model.Attachment;
import io.muenchendigital.digiwf.email.integration.application.port.LoadAttachementPort;
import io.muenchendigital.digiwf.integration.core.api.TechnicalError;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.tika.Tika;
import org.springframework.stereotype.Component;

import javax.mail.util.ByteArrayDataSource;
import java.io.InputStream;
import java.net.URL;

@Slf4j
@Component
public class S3Adapter implements LoadAttachementPort {

    @Override
    public Attachment loadAttachement(final AttachmentDto attachment) throws TechnicalError {
        try {
            // download file from s3
            final URL binaryFile = new URL(attachment.getUrl());
            final Tika tika = new Tika();
            final InputStream fileInputStream = binaryFile.openStream();
            final ByteArrayDataSource file = new ByteArrayDataSource(fileInputStream, tika.detect(binaryFile));
            final String fileName = StringUtils.substringAfterLast(attachment.getPath(), "/");
            // return attachment
            return new Attachment(fileName, file);
        } catch (final java.io.IOException ex) {
            log.error("An attachment could not be loaded: {}", attachment);
            throw new TechnicalError("400", "An attachment could not be loaded: " + attachment);
        }
    }

}
