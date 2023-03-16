package io.muenchendigital.digiwf.email.integration.application.service;

import io.muenchendigital.digiwf.email.integration.application.dto.AttachmentDto;
import io.muenchendigital.digiwf.email.integration.application.model.Attachment;
import io.muenchendigital.digiwf.email.integration.application.port.LoadAttachementPort;
import lombok.RequiredArgsConstructor;

import javax.mail.util.ByteArrayDataSource;

@RequiredArgsConstructor
public class DummyMailAttachmentPort implements LoadAttachementPort {
    private final String fileName;

    @Override
    public Attachment loadAttachement(final AttachmentDto attachment) {
        return new Attachment(this.fileName, new ByteArrayDataSource("test".getBytes(), "text/plain"));
    }
}
