package de.muenchen.oss.digiwf.email.integration.application.port.in;

import de.muenchen.oss.digiwf.email.integration.domain.model.presigned.TemplateMailPresigned;
import de.muenchen.oss.digiwf.email.integration.domain.model.presigned.TextMailPresigned;
import jakarta.validation.Valid;

public interface SendMailPresignedInPort {

    void sendMailWithText(@Valid final TextMailPresigned mail);

    void sendMailWithTemplate(@Valid final TemplateMailPresigned mail);

}
