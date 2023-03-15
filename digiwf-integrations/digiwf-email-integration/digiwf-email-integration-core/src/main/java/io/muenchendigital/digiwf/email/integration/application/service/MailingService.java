package io.muenchendigital.digiwf.email.integration.application.service;

import io.muenchendigital.digiwf.email.integration.application.port.LoadAttachementPort;
import io.muenchendigital.digiwf.email.integration.application.port.SendMailPort;
import io.muenchendigital.digiwf.email.integration.domain.Mail;
import io.muenchendigital.digiwf.integration.core.api.TechnicalError;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.validation.Valid;

@Slf4j
@Service
@RequiredArgsConstructor
@Validated
public class MailingService {

    private final SendMailPort sendMailPort;
    private final LoadAttachementPort loadAttachementPort;
    private final String fromAdress;

    /**
     * Send a mail.
     *
     * @param mail mail that is sent
     */
    public void sendMail(@Valid final Mail mail) throws TechnicalError {
        //handler
        final MimeMessagePreparator preparator = mimeMessage -> {
            mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail.getReceivers()));

            if (StringUtils.isNotEmpty(mail.getReceiversCc())) {
                mimeMessage.setRecipients(Message.RecipientType.CC, InternetAddress.parse(mail.getReceiversCc()));
            }
            if (StringUtils.isNotEmpty(mail.getReceiversBcc())) {
                mimeMessage.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(mail.getReceiversBcc()));
            }

            var helper = new MimeMessageHelper(mimeMessage, true);

            helper.setSubject(mail.getSubject());
            helper.setText(mail.getBody());
            helper.setFrom(this.fromAdress);

            if (StringUtils.isNotBlank(mail.getReplyTo())) {
                helper.setReplyTo(mail.getReplyTo());
            }

            // mail attachments
            if (CollectionUtils.isNotEmpty(mail.getAttachments())) {
                for (val attachment : mail.getAttachments()) {
                    helper = this.loadAttachementPort.loadAttachement(attachment, helper);
                }
            }
        };

        this.sendMailPort.sendMail(preparator);
        log.info("Mail sent to: {})", mail.getReceivers());
    }
}
