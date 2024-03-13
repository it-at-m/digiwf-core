package de.muenchen.oss.digiwf.email.integration.application.usecase;

import de.muenchen.oss.digiwf.email.integration.application.port.in.SendMail;
import de.muenchen.oss.digiwf.email.integration.application.port.out.CorrelateMessagePort;
import de.muenchen.oss.digiwf.email.integration.application.port.out.LoadMailAttachmentPort;
import de.muenchen.oss.digiwf.email.integration.application.port.out.MailPort;
import de.muenchen.oss.digiwf.email.integration.model.Mail;
import de.muenchen.oss.digiwf.email.integration.model.MailWithTemplate;
import de.muenchen.oss.digiwf.email.model.FileAttachment;
import de.muenchen.oss.digiwf.message.process.api.error.BpmnError;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.validation.annotation.Validated;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Validated
public class SendMailUseCase implements SendMail {

    private final LoadMailAttachmentPort loadAttachmentPort;
    private final CorrelateMessagePort correlateMessagePort;
    private final MailPort mailPort;

    /**
     * Send a mail.
     *
     * @param mail mail that is sent
     */
    @Override
    public void sendMailWithText(final String processInstanceIde, final String type, final String integrationName, @Valid final Mail mail) throws BpmnError {
        // load Attachments
        final List<FileAttachment> attachments = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(mail.getAttachments())) {
            for (val attachment : mail.getAttachments()) {
                 attachments.add(this.loadAttachmentPort.loadAttachment(attachment));
            }
        }
        // send mail
        final de.muenchen.oss.digiwf.email.model.Mail mailModel = de.muenchen.oss.digiwf.email.model.Mail.builder()
                .receivers(mail.getReceivers())
                .subject(mail.getSubject())
                .body(mail.getBody())
                .replyTo(mail.getReplyTo())
                .receiversCc(mail.getReceiversCc())
                .receiversBcc(mail.getReceiversBcc())
                .attachments(attachments)
                .build();

        this.sendMail(processInstanceIde,type,integrationName,mailModel);
    }

    @Override
    public void sendMailWithTemplate(final String processInstanceIde, final String type, final String integrationName, @Valid final MailWithTemplate mail) throws BpmnError {
        // get body from template
        try {
            Map<String, Object> content = new HashMap<>();
            content.put("mail", mail);
            content.put("footer", "DigiWF 2.0<br>IT-Referat der Stadt München");
            String body = this.mailPort.getBodyFromTemplate(mail.getTemplate(), content);

            // load Attachments
            final List<FileAttachment> attachments = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(mail.getAttachments())) {
                for (val attachment : mail.getAttachments()) {
                    attachments.add(this.loadAttachmentPort.loadAttachment(attachment));
                }
            }

            // send mail
            final de.muenchen.oss.digiwf.email.model.Mail mailModel = de.muenchen.oss.digiwf.email.model.Mail.builder()
                    .receivers(mail.getReceivers())
                    .subject(mail.getSubject())
                    .body(body)
                    .htmlBody(true)
                    .replyTo(mail.getReplyTo())
                    .receiversCc(mail.getReceiversCc())
                    .receiversBcc(mail.getReceiversBcc())
                    .attachments(attachments)
                    .build();
            this.sendMail(processInstanceIde,type,integrationName,mailModel);

        } catch (IOException ioException) {
            throw new BpmnError("LOAD_TEMPLATE_FAILED", "The template " + mail.getTemplate() + " could not be loaded");
        } catch (TemplateException templateException) {
            throw new BpmnError("FILLING_TEMPLATE_FAILED", templateException.getMessage());
        }
    }

    private void sendMail(final String processInstanceIde, final String type, final String integrationName, de.muenchen.oss.digiwf.email.model.Mail mailModel) throws BpmnError {
        try {
                  this.mailPort.sendMail(mailModel);
            // correlate message
            final Map<String, Object> correlatePayload = new HashMap<>();
            correlatePayload.put("mailSentStatus", true);
            this.correlateMessagePort.correlateMessage(processInstanceIde, type, integrationName, correlatePayload);
        } catch (final MessagingException ex) {
            log.error("Sending mail failed with exception: {}", ex.getMessage());
            throw new BpmnError("MAIL_SENDING_FAILED", ex.getMessage());
        }
    }

}
