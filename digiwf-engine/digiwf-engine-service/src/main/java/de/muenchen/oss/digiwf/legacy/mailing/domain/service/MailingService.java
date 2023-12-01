/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.legacy.mailing.domain.service;

import de.muenchen.oss.digiwf.legacy.mailing.domain.configuration.MailConfigurationHandler;
import de.muenchen.oss.digiwf.legacy.mailing.domain.model.Mail;
import de.muenchen.oss.digiwf.legacy.mailing.domain.model.MailTemplate;
import de.muenchen.oss.digiwf.legacy.mailing.properties.MailProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import jakarta.mail.Message;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.util.ByteArrayDataSource;
import java.io.IOException;

/**
 * Service contains methods to handle mails.
 *
 * @author externer.dl.horn
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MailingService {

    private final JavaMailSender mailSender;
    private final MailProperties properties;
    private final MailConfigurationHandler mailConfigurationHandler;
    private final MailTemplateService mailTemplateService;

    /**
     * Send a mail.
     *
     * @param mail mail that is sent
     */
    public void sendMail(final Mail mail) {
        this.mailConfigurationHandler.handleMail(mail);

        //handler
        final MimeMessagePreparator preparator = mimeMessage -> {
            mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail.getReceivers()));

            final MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setText(mail.getBody());
            helper.setSubject(mail.getSubject());
            helper.setFrom(this.properties.getDefaultFromAddress());

            if (mail.hasReplyTo()) {
                helper.setReplyTo(mail.getReplyTo());
            }

            if (mail.hasAttachement()) {
                val attachment = new ByteArrayDataSource(mail.getAttachment().getContent(), MediaType.APPLICATION_PDF.toString());
                helper.addAttachment(mail.getAttachment().getName(), attachment);
            }
        };

        this.mailSender.send(preparator);

        log.info("mail send to: {})", mail.getReceivers());
    }

    /**
     * Send a mail template including a link.
     *
     * @param mail Mail that is sent
     */
    public void sendMailTemplateWithLink(final MailTemplate mail) throws IOException {

        this.mailConfigurationHandler.handleMail(mail);

        val mailTemplate = this.mailTemplateService.getMailTemplateWithLink();

        val text = mailTemplate.replaceAll("%%body_top%%", mail.getBody())
                .replaceAll("%%body_bottom%%",
                        StringUtils.isBlank(mail.getBottomText()) ? "Mit freundlichen Grüßen<br>Ihr DigiWF-Team" : mail.getBottomText())
                .replaceAll("%%button_link%%", mail.getLink())
                .replaceAll("%%button_text%%", mail.getButtonText())
                .replaceAll("%%footer%%", "DigiWF 2.0<br>IT-Referat der Stadt München");

        this.send(mail, text, null);
    }

    /**
     * Send a mail template.
     *
     * @param mail     Mail that is sent
     * @param logoPath Path to the logo that should be included
     */
    public void sendMailTemplate(final MailTemplate mail, final String logoPath) throws IOException {
        this.mailConfigurationHandler.handleMail(mail);
        val mailTemplate = this.mailTemplateService.getMailTemplate();

        val text = mailTemplate.replaceAll("%%body_top%%", mail.getBody())
                .replaceAll("%%body_bottom%%",
                        StringUtils.isBlank(mail.getBottomText()) ? "Mit freundlichen Grüßen<br>Ihr DigiWF-Team" : mail.getBottomText())
                .replaceAll("%%footer%%", "DigiWF 2.0<br>IT-Referat der Stadt München");

        this.send(mail, text, logoPath);
    }

    // helper Methods

    private void send(final MailTemplate mail, final String text, final String logoPath) {
        //handler
        final MimeMessagePreparator preparator = mimeMessage -> {
            mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail.getReceivers()));

            final MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setText(text, true);
            helper.setSubject(mail.getSubject());
            helper.setFrom(StringUtils.isBlank(mail.getSender()) ? this.properties.getDefaultFromAddress() : mail.getSender());
            helper.addInline("logo", StringUtils.isBlank(logoPath) ? this.mailTemplateService.getLogo() : this.mailTemplateService.getLogoByPath(logoPath));

            if (mail.hasReplyTo()) {
                helper.setReplyTo(mail.getReplyTo());
            }

            if (mail.hasAttachement()) {
                val attachment = new ByteArrayDataSource(mail.getAttachment().getContent(), MediaType.APPLICATION_PDF.toString());
                helper.addAttachment(mail.getAttachment().getName(), attachment);
            }
        };
        this.mailSender.send(preparator);
    }

}
