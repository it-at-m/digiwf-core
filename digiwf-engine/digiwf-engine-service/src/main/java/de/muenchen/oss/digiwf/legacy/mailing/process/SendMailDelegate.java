/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.legacy.mailing.process;

import de.muenchen.oss.digiwf.email.api.DigiwfEmailApi;
import de.muenchen.oss.digiwf.email.model.FileAttachment;
import de.muenchen.oss.digiwf.email.model.Mail;
import de.muenchen.oss.digiwf.legacy.document.domain.DocumentService;
import jakarta.mail.util.ByteArrayDataSource;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Mail delegate.
 *
 * @author externer.dl.horn
 */
@Component
@RequiredArgsConstructor
public class SendMailDelegate implements JavaDelegate {

    protected final DigiwfEmailApi digiwfEmailApi;
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

        final List<FileAttachment> fileAttachments = new ArrayList<>();
        if (attachmentGuid.isPresent()) {
            val document = this.documentService.createDocument(attachmentGuid.get(), delegateExecution.getProcessInstance().getVariables());
            fileAttachments.add(new FileAttachment(attachmentName.orElse("anhang.pdf"), new ByteArrayDataSource(document, "application/pdf")));
        }

        final Mail mail = Mail.builder()
                .receivers(receivers)
                .subject(subject)
                .body(body)
                .replyTo(replyTo.orElse(null))
                .attachments(fileAttachments)
                .build();
        this.digiwfEmailApi.sendMail(mail);
    }
}
