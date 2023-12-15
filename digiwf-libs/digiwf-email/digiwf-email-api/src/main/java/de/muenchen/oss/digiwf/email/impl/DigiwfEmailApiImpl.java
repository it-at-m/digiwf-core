package de.muenchen.oss.digiwf.email.impl;

import de.muenchen.oss.digiwf.email.api.DigiwfEmailApi;
import de.muenchen.oss.digiwf.email.model.Mail;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;
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
    // use a prepackaged sanitizer to prevent XSS
    private final PolicyFactory policy = Sanitizers.BLOCKS
            .and(Sanitizers.FORMATTING)
            .and(Sanitizers.LINKS)
            .and(Sanitizers.TABLES);

    @Override
    public void sendMail(@Valid Mail mail) throws MessagingException {
        this.sendMail(mail, null);
    }

    @Override
    public void sendMailWithDefaultLogo(@Valid Mail mail) throws MessagingException {
        this.sendMail(mail, "bausteine/mail/email-logo.png");
    }

    @Override
    public void sendMail(@Valid Mail mail, String logoPath) throws MessagingException {
        final MimeMessage mimeMessage = this.mailSender.createMimeMessage();

        mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail.getReceivers()));

        if (mail.hasReceiversCc()) {
            mimeMessage.setRecipients(Message.RecipientType.CC, InternetAddress.parse(mail.getReceiversCc()));
        }
        if (mail.hasReceiversBcc()) {
            mimeMessage.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(mail.getReceiversBcc()));
        }

        if (mail.hasReplyTo()) {
            mimeMessage.setReplyTo(InternetAddress.parse(mail.getReplyTo()));
        }

        final var helper = new MimeMessageHelper(mimeMessage, true, StandardCharsets.UTF_8.name());

        helper.setSubject(mail.getSubject());
        helper.setText(mail.getBody(), mail.isHtmlBody());
        // use custom sender
        helper.setFrom(mail.hasSender() ? mail.getSender() : this.fromAddress);

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
            // make sure inputs are sanitized to prevent XSS
            final String value = policy.sanitize(entry.getValue());
            // Make sure new lines are converted to <br> tags
            mailTemplate = mailTemplate.replaceAll(entry.getKey(), value.replaceAll("(\r\n|\n\r|\r|\n)", "<br/>"));
        }
        return mailTemplate;
    }

    private String getTemplate(String templatePath) {
        final Resource resource = this.getRessourceFromClassPath(templatePath);
        if (!resource.exists()) {
            log.error("Email Template not found: {}", templatePath);
            throw new RuntimeException("Email Template not found: " + templatePath);
        }

        try {
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
