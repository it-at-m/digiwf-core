package de.muenchen.oss.digiwf.email.integration.application.usecase;

import de.muenchen.oss.digiwf.email.integration.application.port.in.SendMailInPort;
import de.muenchen.oss.digiwf.email.integration.application.port.out.CorrelateMessageOutPort;
import de.muenchen.oss.digiwf.email.integration.application.port.out.LoadMailAttachmentOutPort;
import de.muenchen.oss.digiwf.email.integration.application.port.out.MailOutPort;
import de.muenchen.oss.digiwf.email.integration.model.BasicMail;
import de.muenchen.oss.digiwf.email.integration.model.TextMail;
import de.muenchen.oss.digiwf.email.integration.model.TemplateMail;
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
public class SendMailUseCase implements SendMailInPort {

    private final LoadMailAttachmentOutPort loadAttachmentOutPort;
    private final CorrelateMessageOutPort correlateMessageOutPort;
    private final MailOutPort mailOutPort;

    /**
     * Send a mail.
     *
     * @param mail mail that is sent
     */
    @Override
    public void sendMailWithText(final String processInstanceIde, final String type, final String integrationName, @Valid final TextMail mail) throws BpmnError {
        de.muenchen.oss.digiwf.email.model.Mail mailModel = createMail(mail);
        mailModel.setBody(mail.getBody());

        this.sendMail(processInstanceIde, type, integrationName, mailModel, null);
    }

    @Override
    public void sendMailWithTemplate(final String processInstanceIde, final String type, final String integrationName, @Valid final TemplateMail mail) throws BpmnError {
        // get body from template
        try {
            Map<String, Object> content = new HashMap<>(mail.getContent());
            content.put("footer", "DigiWF 2.0<br>IT-Referat der Stadt München");
            String body = this.mailOutPort.getBodyFromTemplate(mail.getTemplate(), content);

            de.muenchen.oss.digiwf.email.model.Mail mailModel = createMail(mail);
            mailModel.setBody(body);
            mailModel.setHtmlBody(true);

            this.sendMail(processInstanceIde, type, integrationName, mailModel, "templates/email-logo.png");

        } catch (IOException ioException) {
            throw new BpmnError("LOAD_TEMPLATE_FAILED", "The template " + mail.getTemplate() + " could not be loaded");
        } catch (TemplateException templateException) {
            throw new BpmnError("TEMPLATE_MERGING_FAILED", templateException.getMessage());
        }
    }

    private de.muenchen.oss.digiwf.email.model.Mail createMail(BasicMail mail) {
        // load Attachments
        final List<FileAttachment> attachments = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(mail.getAttachments())) {
            for (val attachment : mail.getAttachments()) {
                attachments.add(this.loadAttachmentOutPort.loadAttachment(attachment));
            }
        }
        // send mail
        return de.muenchen.oss.digiwf.email.model.Mail.builder()
                .receivers(mail.getReceivers())
                .subject(mail.getSubject())
                .replyTo(mail.getReplyTo())
                .receiversCc(mail.getReceiversCc())
                .receiversBcc(mail.getReceiversBcc())
                .attachments(attachments)
                .build();
    }

    private void sendMail(final String processInstanceIde, final String type, final String integrationName, de.muenchen.oss.digiwf.email.model.Mail mailModel, String logoPath) throws BpmnError {
        try {
            this.mailOutPort.sendMail(mailModel, logoPath);
            // correlate message
            final Map<String, Object> correlatePayload = new HashMap<>();
            correlatePayload.put("mailSentStatus", true);
            this.correlateMessageOutPort.correlateMessage(processInstanceIde, type, integrationName, correlatePayload);
        } catch (final MessagingException ex) {
            log.error("Sending mail failed with exception: {}", ex.getMessage());
            throw new BpmnError("MAIL_SENDING_FAILED", ex.getMessage());
        }
    }

}
