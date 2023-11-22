package de.muenchen.oss.digiwf.email.impl;

import de.muenchen.oss.digiwf.email.api.DigiwfEmailApi;
import de.muenchen.oss.digiwf.email.model.FileAttachment;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class DigiwfEmailApiImpl implements DigiwfEmailApi {

    private final JavaMailSender mailSender;
    private final String fromAddress;

    @Override
    public void sendMail(String receivers, String subject, String body, String replyTo) throws MessagingException {
        this.sendMailWithAttachments(receivers, subject, body, replyTo, null, null, List.of());
    }

    @Override
    public void sendMail(String receivers, String subject, String body, String replyTo, String receiversCc, String receiversBcc) throws MessagingException {
        this.sendMailWithAttachments(receivers, subject, body, replyTo, receiversCc, receiversBcc, List.of());
    }

    @Override
    public void sendMailWithAttachments(String receivers, String subject, String body, String replyTo, List<FileAttachment> attachments) throws MessagingException {
        this.sendMailWithAttachments(receivers, subject, body, replyTo, null, null, attachments);
    }

    @Override
    public void sendMailWithAttachments(String receivers, String subject, String body, String replyTo, String receiversCc, String receiversBcc, List<FileAttachment> attachments) throws MessagingException {
        final MimeMessage mimeMessage = this.mailSender.createMimeMessage();

        mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receivers));

        if (StringUtils.isNotEmpty(receiversCc)) {
            mimeMessage.setRecipients(Message.RecipientType.CC, InternetAddress.parse(receiversCc));
        }
        if (StringUtils.isNotEmpty(receiversCc)) {
            mimeMessage.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(receiversBcc));
        }

        final var helper = new MimeMessageHelper(mimeMessage, true);

        helper.setSubject(subject);
        helper.setText(subject);
        helper.setFrom(this.fromAddress);

        if (StringUtils.isNotBlank(replyTo)) {
            helper.setReplyTo(replyTo);
        }

        // mail attachments
        if (attachments != null) {
            for (val attachment : attachments) {
                helper.addAttachment(attachment.getFileName(), attachment.getFile());
            }
        }

        this.mailSender.send(mimeMessage);
        log.info("Mail {} sent to {}.", subject, receivers);
    }

}
