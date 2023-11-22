package de.muenchen.oss.digiwf.email.integration.adapter.out;

import de.muenchen.oss.digiwf.email.api.DigiwfEmailApi;
import de.muenchen.oss.digiwf.email.integration.application.port.out.MailPort;
import de.muenchen.oss.digiwf.email.model.FileAttachment;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class MailAdapter implements MailPort {

    private final DigiwfEmailApi digiwfEmailApi;

    @Override
    public void sendMail(String receivers, String subject, String body, String replyTo, String receiversCc, String receiversBcc, List<FileAttachment> attachments) throws MessagingException {
        this.digiwfEmailApi.sendMailWithAttachments(receivers, subject, body, replyTo, receiversCc, receiversBcc, attachments);
    }
}
