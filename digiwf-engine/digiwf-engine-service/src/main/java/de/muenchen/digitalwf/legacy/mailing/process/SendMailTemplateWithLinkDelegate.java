/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.digitalwf.legacy.mailing.process;

import de.muenchen.digitalwf.legacy.document.domain.DocumentService;
import de.muenchen.digitalwf.legacy.mailing.domain.model.MailTemplate;
import de.muenchen.digitalwf.legacy.mailing.domain.service.MailingService;

import lombok.val;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Component;

import static de.muenchen.digitalwf.legacy.mailing.process.MailingVariables.*;

/**
 * Mail template with link delegate.
 *
 * @author externer.dl.horn
 */
@Component
public class SendMailTemplateWithLinkDelegate extends SendMailDelegate {

    public SendMailTemplateWithLinkDelegate(final MailingService mailingService, final DocumentService documentService) {
        super(mailingService, documentService);
    }

    @Override
    public void execute(final DelegateExecution delegateExecution) throws Exception {

        //INPUT
        val subject = SUBJECT.from(delegateExecution).getLocal();
        val receivers = RECEIVERS.from(delegateExecution).getLocal();
        val body = BODY.from(delegateExecution).getLocal();
        val replyTo = REPLY_TO.from(delegateExecution).getLocalOptional();
        val bottomText = BOTTOM_TEXT.from(delegateExecution).getLocal();
        val linkText = LINK_TEXT.from(delegateExecution).getLocal();
        val linkUrl = LINK_URL.from(delegateExecution).getLocal();
        val attachmentGuid = ATTACHMENT_GUID.from(delegateExecution).getLocalOptional();
        val attachmentName = ATTACHMENT_NAME.from(delegateExecution).getLocalOptional();

        //PROCESSING
        final MailTemplate mail = MailTemplate.builder()
                .body(body.replaceAll("(\r\n|\n\r|\r|\n)", "<br />"))
                .bottomText(bottomText.replaceAll("(\r\n|\n\r|\r|\n)", "<br />"))
                .link(linkUrl)
                .buttonText(linkText)
                .subject(subject)
                .receivers(receivers)
                .replyTo(replyTo.orElse(null))
                .build();

        this.addAttachment(delegateExecution, attachmentGuid, attachmentName, mail);
        this.mailingService.sendMailTemplateWithLink(mail);
    }
}
