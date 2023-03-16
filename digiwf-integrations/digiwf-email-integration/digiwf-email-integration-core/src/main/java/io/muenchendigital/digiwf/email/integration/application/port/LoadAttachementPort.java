package io.muenchendigital.digiwf.email.integration.application.port;

import io.muenchendigital.digiwf.email.integration.application.dto.AttachmentDto;
import io.muenchendigital.digiwf.email.integration.application.model.Attachment;

public interface LoadAttachementPort {

    Attachment loadAttachement(final AttachmentDto attachment);
}
