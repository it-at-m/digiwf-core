/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.legacy.mailing.process;

import de.muenchen.oss.digiwf.email.api.DigiwfEmailApi;
import de.muenchen.oss.digiwf.email.model.FileAttachment;
import de.muenchen.oss.digiwf.email.model.Mail;
import de.muenchen.oss.digiwf.legacy.document.domain.DocumentService;
import jakarta.mail.util.ByteArrayDataSource;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Mail template delegate.
 *
 * @author externer.dl.horn
 */
@Component
public class SendMailTemplateDelegate extends SendMailDelegate {

    public SendMailTemplateDelegate(final DigiwfEmailApi digiwfEmailApi, final DocumentService documentService) {
        super(digiwfEmailApi, documentService);
    }

    @Override
    public void execute(final DelegateExecution delegateExecution) throws Exception {

        //INPUT
        val subject = MailingVariables.SUBJECT.from(delegateExecution).getLocal();
        val receivers = MailingVariables.RECEIVERS.from(delegateExecution).getLocal();
        val body = MailingVariables.BODY.from(delegateExecution).getLocal();
        val replyTo = MailingVariables.REPLY_TO.from(delegateExecution).getLocalOptional();
        val bottomText = MailingVariables.BOTTOM_TEXT.from(delegateExecution).getLocal();
        val attachmentGuid = MailingVariables.ATTACHMENT_GUID.from(delegateExecution).getLocalOptional();
        val attachmentName = MailingVariables.ATTACHMENT_NAME.from(delegateExecution).getLocalOptional();
        val templateId = MailingVariables.TEMPLATE_ID.from(delegateExecution).getLocalOptional();
        val sender = MailingVariables.SENDER.from(delegateExecution).getLocalOptional();

        final String templatePath = "bausteine/mail/template/mail-template.tpl";
        // default logo ist bausteine/mail/email-logo.png
        final String logoPath = (templateId.isPresent() && templateId.get().equals("euro2020")) ? "bausteine/mail/euro2020-logo.png" : "bausteine/mail/email-logo.png";

        final Map<String, String> emailContent = Map.of(
                "%%body_top%%", body,
                "%%body_bottom%%", StringUtils.isBlank(bottomText) ? "Mit freundlichen Grüßen<br>Ihr DigiWF-Team" : bottomText,
                "%%footer%%", "DigiWF 2.0<br>IT-Referat der Stadt München"
        );
        final String emailBody = this.digiwfEmailApi.getEmailBodyFromTemplate(templatePath, emailContent);

        final List<FileAttachment> fileAttachments = new ArrayList<>();
        if (attachmentGuid.isPresent()) {
            val document = this.documentService.createDocument(attachmentGuid.get(), delegateExecution.getProcessInstance().getVariables());
            fileAttachments.add(new FileAttachment(attachmentName.orElse("anhang.pdf"), new ByteArrayDataSource(document, "application/pdf")));
        }

        final Mail mail = Mail.builder()
                .receivers(receivers)
                .subject(subject)
                .body(emailBody)
                .replyTo(replyTo.orElse(null))
                .sender(sender.orElse(null))
                .attachments(fileAttachments)
                .build();
        this.digiwfEmailApi.sendMail(mail, logoPath);
    }
}
