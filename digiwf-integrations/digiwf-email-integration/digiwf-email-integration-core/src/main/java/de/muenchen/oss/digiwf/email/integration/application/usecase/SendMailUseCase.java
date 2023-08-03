package de.muenchen.oss.digiwf.email.integration.application.usecase;

import de.muenchen.oss.digiwf.email.integration.model.FileAttachment;
import de.muenchen.oss.digiwf.email.integration.application.port.in.SendMail;
import de.muenchen.oss.digiwf.email.integration.application.port.out.CorrelateMessagePort;
import de.muenchen.oss.digiwf.email.integration.application.port.out.LoadMailAttachmentPort;
import de.muenchen.oss.digiwf.email.integration.model.Mail;
import de.muenchen.oss.digiwf.message.process.api.error.BpmnError;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.validation.annotation.Validated;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Validated
public class SendMailUseCase implements SendMail {

    private final JavaMailSender mailSender;
    private final LoadMailAttachmentPort loadAttachmentPort;
    private final CorrelateMessagePort correlateMessagePort;
    private final String fromAddress;

    /**
     * Send a mail.
     *
     * @param mail mail that is sent
     */
    @Override
    public void sendMail(final String processInstanceIde, final String messageName, @Valid final Mail mail) throws BpmnError {
        final MimeMessage mimeMessage = this.mailSender.createMimeMessage();

        try {
            mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail.getReceivers()));

            if (StringUtils.isNotEmpty(mail.getReceiversCc())) {
                mimeMessage.setRecipients(Message.RecipientType.CC, InternetAddress.parse(mail.getReceiversCc()));
            }
            if (StringUtils.isNotEmpty(mail.getReceiversBcc())) {
                mimeMessage.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(mail.getReceiversBcc()));
            }

            final var helper = new MimeMessageHelper(mimeMessage, true);

            helper.setSubject(mail.getSubject());
            helper.setText(mail.getBody());
            helper.setFrom(this.fromAddress);

            if (StringUtils.isNotBlank(mail.getReplyTo())) {
                helper.setReplyTo(mail.getReplyTo());
            }

            // mail attachments
            if (CollectionUtils.isNotEmpty(mail.getAttachments())) {
                for (val attachment : mail.getAttachments()) {
                    final FileAttachment mailAttachment = this.loadAttachmentPort.loadAttachment(attachment);
                    helper.addAttachment(mailAttachment.getFileName(), mailAttachment.getFile());
                }
            }
        } catch (final MessagingException ex) {
            log.error(ex.getMessage());
            throw new BpmnError("MESSAGING_EXCEPTION", ex.getMessage());
        }

        try {
            this.mailSender.send(mimeMessage);
            log.info("Mail sent to {}.", mail.getReceivers());
        } catch (final MailException ex) {
            log.error("Sending mail failed with exception: {}", ex.getMessage());
            throw new BpmnError("MAIL_SENDING_FAILED", ex.getMessage());
        }

        final Map<String, Object> correlatePayload = new HashMap<>();
        correlatePayload.put("mailSentStatus", true);
        this.correlateMessagePort.correlateMessage(processInstanceIde, messageName, correlatePayload);
    }
}
