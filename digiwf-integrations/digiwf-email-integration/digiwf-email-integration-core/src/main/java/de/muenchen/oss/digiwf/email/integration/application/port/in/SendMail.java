package de.muenchen.oss.digiwf.email.integration.application.port.in;

import de.muenchen.oss.digiwf.email.integration.model.Mail;

import javax.validation.Valid;

public interface SendMail {

    void sendMail(final String processInstanceId, final String messageName, @Valid final Mail mail);

}
