/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.legacy.mailing.process;

import io.muenchendigital.digiwf.legacy.document.domain.DocumentService;
import io.muenchendigital.digiwf.legacy.mailing.domain.model.MailTemplate;
import io.muenchendigital.digiwf.legacy.mailing.domain.service.MailingService;

import lombok.val;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Mail template delegate.
 *
 * @author externer.dl.horn
 */
@Component
public class SendMailTemplateDelegate extends SendMailDelegate {

    public SendMailTemplateDelegate(final MailingService mailingService, final DocumentService documentService) {
        super(mailingService, documentService);
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

        //PROCESSING
        final MailTemplate mail = MailTemplate.builder()
                .body(body.replaceAll("(\r\n|\n\r|\r|\n)", "<br />"))
                .bottomText(bottomText.replaceAll("(\r\n|\n\r|\r|\n)", "<br />"))
                .subject(subject)
                .receivers(receivers)
                .replyTo(replyTo.orElse(null))
                .sender(sender.orElse(""))
                .build();

        this.addAttachment(delegateExecution, attachmentGuid, attachmentName, mail);

        this.mailingService.sendMailTemplate(mail, this.getLogoPathByTemplateId(templateId));
    }

    //TODO refacotring
    private String getLogoPathByTemplateId(final Optional<String> tempalteId) {

        if (!tempalteId.isPresent()) {
            return null;
        }

        if ("euro2020".equals(tempalteId.get())) {
            return "bausteine/mail/euro2020-logo.png";
        }

        return null;
    }
}
