/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.legacy.mailing.process;

import de.muenchen.oss.digiwf.legacy.document.domain.DocumentService;
import de.muenchen.oss.digiwf.legacy.mailing.domain.model.Attachment;
import de.muenchen.oss.digiwf.legacy.mailing.domain.model.Mail;
import de.muenchen.oss.digiwf.legacy.mailing.domain.service.MailingService;
import lombok.RequiredArgsConstructor;

import lombok.val;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

/**
 * Mail delegate.
 *
 * @author externer.dl.horn
 */
@Component
@RequiredArgsConstructor
public class SendMailDelegate implements JavaDelegate {

    protected final MailingService mailingService;
    protected final DocumentService documentService;

    @Override
    public void execute(final DelegateExecution delegateExecution) throws Exception {

        //INPUT
        val subject = MailingVariables.SUBJECT.from(delegateExecution).getLocal();
        val receivers = MailingVariables.RECEIVERS.from(delegateExecution).getLocal();
        val body = MailingVariables.BODY.from(delegateExecution).getLocal();
        val replyTo = MailingVariables.REPLY_TO.from(delegateExecution).getLocalOptional();
        val attachmentGuid = MailingVariables.ATTACHMENT_GUID.from(delegateExecution).getLocalOptional();
        val attachmentName = MailingVariables.ATTACHMENT_NAME.from(delegateExecution).getLocalOptional();

        //PROCESSING
        final Mail mail = Mail.builder()
                .body(body)
                .subject(subject)
                .receivers(receivers)
                .replyTo(replyTo.orElse(null))
                .build();

        this.addAttachment(delegateExecution, attachmentGuid, attachmentName, mail);

        this.mailingService.sendMail(mail);
    }

    protected void addAttachment(final DelegateExecution delegateExecution, final java.util.Optional<String> attachmentGuid,
            final java.util.Optional<String> attachmentName, final Mail mail) {
        if (attachmentGuid.isPresent()) {
            val document = this.documentService.createDocument(attachmentGuid.get(), delegateExecution.getProcessInstance().getVariables());
            val attachement = Attachment.builder()
                    .content(document)
                    .name(attachmentName.orElse("anhang.pdf"))
                    .build();
            mail.setAttachment(attachement);
        }
    }
}
