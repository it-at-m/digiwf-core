package de.muenchen.oss.digiwf.email.integration.application.port.in;

import de.muenchen.oss.digiwf.email.integration.model.Mail;
import jakarta.validation.Valid;

public interface SendMail {

    void sendMail(final String processInstanceId, final String type, final String integrationName, @Valid final Mail mail);

}
