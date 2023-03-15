package io.muenchendigital.digiwf.email.integration.application.port;

import io.muenchendigital.digiwf.email.integration.domain.Attachment;
import org.springframework.mail.javamail.MimeMessageHelper;

public interface LoadAttachementPort {

    MimeMessageHelper loadAttachement(final Attachment attachment, final MimeMessageHelper mimeMessageHelper);
}
