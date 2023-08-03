package de.muenchen.oss.digiwf.email.integration.application.port.out;

import de.muenchen.oss.digiwf.email.integration.model.FileAttachment;
import de.muenchen.oss.digiwf.email.integration.model.PresignedUrl;

public interface LoadMailAttachmentPort {

    FileAttachment loadAttachment(final PresignedUrl attachment);
}
