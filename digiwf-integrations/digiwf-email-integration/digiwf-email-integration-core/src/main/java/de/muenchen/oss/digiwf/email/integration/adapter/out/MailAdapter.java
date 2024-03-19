package de.muenchen.oss.digiwf.email.integration.adapter.out;

import de.muenchen.oss.digiwf.email.api.DigiwfEmailApi;
import de.muenchen.oss.digiwf.email.integration.application.port.out.MailOutPort;
import de.muenchen.oss.digiwf.email.model.Mail;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.Map;

@RequiredArgsConstructor
public class MailAdapter implements MailOutPort {

    private final DigiwfEmailApi digiwfEmailApi;

    @Override
    public void sendMail(Mail mail) throws MessagingException {
        this.digiwfEmailApi.sendMail(mail);
    }

    @Override
    public String getBodyFromTemplate(String templateName, Map<String, Object> content) throws TemplateException, IOException {
        return this.digiwfEmailApi.getBodyFromTemplate(templateName, content);
    }
}
