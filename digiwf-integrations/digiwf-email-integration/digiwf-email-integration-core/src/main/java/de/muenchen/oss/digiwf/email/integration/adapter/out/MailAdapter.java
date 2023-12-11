package de.muenchen.oss.digiwf.email.integration.adapter.out;

import de.muenchen.oss.digiwf.email.api.DigiwfEmailApi;
import de.muenchen.oss.digiwf.email.integration.application.port.out.MailPort;
import de.muenchen.oss.digiwf.email.model.Mail;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MailAdapter implements MailPort {

    private final DigiwfEmailApi digiwfEmailApi;

    @Override
    public void sendMail(Mail mail) throws MessagingException {
        this.digiwfEmailApi.sendMail(mail);
    }
}
