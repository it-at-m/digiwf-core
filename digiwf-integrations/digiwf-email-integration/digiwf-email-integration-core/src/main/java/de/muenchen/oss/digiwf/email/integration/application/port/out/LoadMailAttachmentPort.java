package de.muenchen.oss.digiwf.email.integration.application.port.out;

import de.muenchen.oss.digiwf.email.integration.model.PresignedUrl;
import de.muenchen.oss.digiwf.email.model.FileAttachment;

public interface LoadMailAttachmentPort {

    FileAttachment loadAttachment(final PresignedUrl attachment);
}
