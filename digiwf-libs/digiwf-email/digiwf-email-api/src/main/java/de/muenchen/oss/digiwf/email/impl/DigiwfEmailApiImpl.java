package de.muenchen.oss.digiwf.email.impl;

import de.muenchen.oss.digiwf.email.api.DigiwfEmailApi;
import de.muenchen.oss.digiwf.email.model.Mail;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.util.FileCopyUtils;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class DigiwfEmailApiImpl implements DigiwfEmailApi {

    private final JavaMailSender mailSender;
    private final ResourceLoader resourceLoader;
    private final String fromAddress;

    @Override
    public void sendMail(Mail mail) throws MessagingException {
        this.sendMail(mail, null);
    }

    @Override
    public void sendMailWithDefaultLogo(Mail mail) throws MessagingException {
        this.sendMail(mail, "bausteine/mail/email-logo.png");
    }

    @Override
    public void sendMail(Mail mail, String logoPath) throws MessagingException {
        final MimeMessage mimeMessage = this.mailSender.createMimeMessage();

        mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail.getReceivers()));

        if (mail.hasReceiversCc()) {
            mimeMessage.setRecipients(Message.RecipientType.CC, InternetAddress.parse(mail.getReceiversCc()));
        }
        if (mail.hasReceiversBcc()) {
            mimeMessage.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(mail.getReceiversBcc()));
        }

        final var helper = new MimeMessageHelper(mimeMessage, true);

        helper.setSubject(mail.getSubject());
        helper.setText(mail.getBody());
        // use custom sender
        helper.setFrom(mail.hasSender() ? mail.getSender() : this.fromAddress);

        if (mail.hasReplyTo()) {
            helper.setReplyTo(mail.getReplyTo());
        }

        // mail attachments
        if (mail.hasAttachement()) {
            for (val attachment : mail.getAttachments()) {
                helper.addAttachment(attachment.getFileName(), attachment.getFile());
            }
        }

        // logo
        if (logoPath != null) {
            final Resource logo = this.getRessourceFromClassPath(logoPath);
            helper.addInline("logo", logo);
        }

        this.mailSender.send(mimeMessage);
        log.info("Mail {} sent to {}.", mail.getSubject(), mail.getReceivers());
    }

    @Override
    public String getEmailBodyFromTemplate(String templatePath, Map<String, String> content) {
        String mailTemplate = this.getTemplate(templatePath);
        for (val entry : content.entrySet()) {
            mailTemplate = mailTemplate.replaceAll("%%" + entry.getKey() + "%%", entry.getValue());
        }
        // Make sure new lines are converted to <br> tags
        return mailTemplate.replaceAll("(\r\n|\n\r|\r|\n)", "<br/>");
    }

    private String getTemplate(String templatePath) {
        try {
            final Resource resource = this.getRessourceFromClassPath(templatePath);
            byte[] byteArray = FileCopyUtils.copyToByteArray(resource.getInputStream());
            return new String(byteArray, StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.warn("Failed to load file: {}", templatePath);
            throw new RuntimeException("Failed to load file: " + templatePath, e);
        }
    }

    private Resource getRessourceFromClassPath(String path) {
        return resourceLoader.getResource("classpath:" + path);
    }
}
