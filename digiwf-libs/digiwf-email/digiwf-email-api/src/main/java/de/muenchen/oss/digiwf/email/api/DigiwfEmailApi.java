package de.muenchen.oss.digiwf.email.api;

import de.muenchen.oss.digiwf.email.model.Mail;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;

import java.io.IOException;
import java.util.Map;

public interface DigiwfEmailApi {

    void sendMail(@Valid Mail mail) throws MessagingException;

    void sendMailWithDefaultLogo(@Valid Mail mail) throws MessagingException;

    void sendMail(@Valid Mail mail, String logoPath) throws MessagingException;

    String getBodyFromTemplate(String templateName, Map<String, Object> content) throws IOException, TemplateException;

    String getEmailBodyFromTemplate(String templatePath, Map<String, String> content);

}
