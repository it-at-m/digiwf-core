package de.muenchen.oss.digiwf.email.integration.application.port.out;

import de.muenchen.oss.digiwf.email.model.Mail;
import jakarta.mail.MessagingException;

import java.util.Map;

public interface MailPort {

    void sendMail(Mail mail) throws MessagingException;

    String getBodyFromTemplate(String templatePath, Map<String, Object> content);

}
