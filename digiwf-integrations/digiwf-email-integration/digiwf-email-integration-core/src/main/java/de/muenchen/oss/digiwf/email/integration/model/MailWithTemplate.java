package de.muenchen.oss.digiwf.email.integration.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.util.List;

@Data
public class MailWithTemplate extends Mail{

    /**
     * Template of the mail.
     */
    @NotBlank(message = "No template given")
    private String template;

    /**
     * Bottom body of the mail.
     */
    @NotBlank(message = "No bottom body given")
    private String bottomBody;

    /**
     * Button text of the mail.
     */
    private String buttonText;

    /**
     * Button link of the mail.
     */
    private String buttonLink;

    public MailWithTemplate(String receivers, String receiversCc, String receiversBcc, String subject, String body, String replyTo, List<PresignedUrl> attachments, String template, String bottomBody, String buttonText, String buttonLink) {
        super(receivers,receiversCc,receiversBcc,subject,body,replyTo,attachments);
        this.template = template;
        this.bottomBody = bottomBody;
        this.buttonText = buttonText;
        this.buttonLink = buttonLink;
    }
}
