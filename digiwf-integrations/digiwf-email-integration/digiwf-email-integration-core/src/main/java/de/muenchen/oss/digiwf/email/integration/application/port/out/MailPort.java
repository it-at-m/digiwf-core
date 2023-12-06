package de.muenchen.oss.digiwf.email.integration.application.port.out;

import de.muenchen.oss.digiwf.email.model.Mail;
import jakarta.mail.MessagingException;

public interface MailPort {

    void sendMail(Mail mail) throws MessagingException;

}
