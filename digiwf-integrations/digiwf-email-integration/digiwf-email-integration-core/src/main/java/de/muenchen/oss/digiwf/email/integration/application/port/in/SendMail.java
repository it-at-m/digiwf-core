package de.muenchen.oss.digiwf.email.integration.application.port.in;

import de.muenchen.oss.digiwf.email.integration.model.Mail;
import de.muenchen.oss.digiwf.email.integration.model.MailWithTemplate;
import jakarta.validation.Valid;

public interface SendMail {

    void sendMailWithText(final String processInstanceId, final String type, final String integrationName, @Valid final Mail mail);
    void sendMailWithTemplate(final String processInstanceId, final String type, final String integrationName, @Valid final MailWithTemplate mail);

}
