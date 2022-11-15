package io.muenchendigital.digiwf.email.integration.domain.service;

import io.muenchendigital.digiwf.email.integration.domain.model.Mail;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.tika.Tika;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.validation.annotation.Validated;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.util.ByteArrayDataSource;
import javax.validation.Valid;
import java.io.InputStream;
import java.net.URL;

@Slf4j
@AllArgsConstructor
@Validated
public class MailingService {

    private final JavaMailSender mailSender;
    private final String fromAdress;

    /**
     * Send a mail.
     *
     * @param mail mail that is sent
     */
    public void sendMail(@Valid final Mail mail) throws RuntimeException {
        //handler
        final MimeMessagePreparator preparator = mimeMessage -> {
            mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail.getReceivers()));

            if (StringUtils.isNotEmpty(mail.getReceiversCc())) {
                mimeMessage.setRecipients(Message.RecipientType.CC, InternetAddress.parse(mail.getReceiversCc()));
            }
            if (StringUtils.isNotEmpty(mail.getReceiversBcc())) {
                mimeMessage.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(mail.getReceiversBcc()));
            }

            val helper = new MimeMessageHelper(mimeMessage, true);

            helper.setSubject(mail.getSubject());
            helper.setText(mail.getBody());
            helper.setFrom(this.fromAdress);

            if (StringUtils.isNotBlank(mail.getReplyTo())) {
                helper.setReplyTo(mail.getReplyTo());
            }

            // mail attachments
            if (CollectionUtils.isNotEmpty(mail.getAttachments())) {
                for (val attachment : mail.getAttachments()) {
                    try {
                        // download file from s3
                        final URL binaryFile = new URL(attachment.getUrl());
                        final Tika tika = new Tika();
                        final InputStream fileInputStream = binaryFile.openStream();
                        final ByteArrayDataSource file = new ByteArrayDataSource(fileInputStream, tika.detect(binaryFile));
                        final String fileName = StringUtils.substringAfterLast(attachment.getPath(), "/");
                        // add attachment
                        helper.addAttachment(fileName, file);
                    } catch (final java.io.IOException ex) {
                        log.error("An attachment could not be loaded: {}", attachment);
                        throw new RuntimeException(String.format("Could not download file %s", attachment.getPath()));
                    }
                }
            }
        };

        this.mailSender.send(preparator);
        log.info("Mail sent to: {})", mail.getReceivers());
    }

}
