package de.muenchen.oss.digiwf.email.api;

import de.muenchen.oss.digiwf.email.model.Mail;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;

import java.util.Map;

public interface DigiwfEmailApi {

    void sendMail(@Valid Mail mail) throws MessagingException;

    void sendMailWithDefaultLogo(@Valid Mail mail) throws MessagingException;

    void sendMail(@Valid Mail mail, String logoPath) throws MessagingException;

    String getEmailBodyFromTemplate(String templatePath, Map<String, String> content);

}
