package de.muenchen.oss.digiwf.email.integration.application.port.in;

import de.muenchen.oss.digiwf.email.integration.model.TextMail;
import de.muenchen.oss.digiwf.email.integration.model.TemplateMail;
import jakarta.validation.Valid;

public interface SendMailInPort {

    void sendMailWithText(final String processInstanceId, final String type, final String integrationName, @Valid final TextMail mail);

    void sendMailWithTemplate(final String processInstanceId, final String type, final String integrationName, @Valid final TemplateMail mail);

}
