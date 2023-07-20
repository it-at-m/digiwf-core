package io.muenchendigital.digiwf.email.integration.application.port.out;

import io.muenchendigital.digiwf.email.integration.model.FileAttachment;
import io.muenchendigital.digiwf.email.integration.model.PresignedUrl;

public interface LoadMailAttachmentPort {

    FileAttachment loadAttachment(final PresignedUrl attachment);
}
