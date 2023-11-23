package de.muenchen.oss.digiwf.email.api;

import de.muenchen.oss.digiwf.email.model.Mail;
import jakarta.mail.MessagingException;

import java.util.Map;

public interface DigiwfEmailApi {

    void sendMail(Mail mail) throws MessagingException;

    void sendMailWithDefaultLogo(Mail mail) throws MessagingException;

    void sendMail(Mail mail, String logoPath) throws MessagingException;

    String getEmailBodyFromTemplate(String templatePath, Map<String, String> content);

}
