package de.muenchen.oss.digiwf.email.integration.application.port.in;

import de.muenchen.oss.digiwf.email.integration.domain.model.TemplateMail;
import de.muenchen.oss.digiwf.email.integration.domain.model.TextMail;
import jakarta.validation.Valid;

public interface SendMailInPort {

    void sendMailWithText(@Valid final TextMail mail);

    void sendMailWithTemplate(@Valid final TemplateMail mail);

}
