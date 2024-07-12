package de.muenchen.oss.digiwf.email.integration.application.port.out;

import de.muenchen.oss.digiwf.email.integration.domain.model.presigned.PresignedUrl;
import de.muenchen.oss.digiwf.email.model.FileAttachment;

import java.util.List;

public interface LoadMailAttachmentOutPort {

    FileAttachment loadAttachment(final PresignedUrl attachment);

    List<FileAttachment> loadAttachments(final String fileContext, final List<String> filePaths);
}
