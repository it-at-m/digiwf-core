package de.muenchen.oss.digiwf.email.integration.adapter.in.streaming;

import de.muenchen.oss.digiwf.email.integration.domain.model.presigned.BasicMailPresigned;
import de.muenchen.oss.digiwf.email.integration.domain.model.presigned.PresignedUrl;
import de.muenchen.oss.digiwf.email.integration.domain.model.presigned.TemplateMailPresigned;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Deprecated
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class MailWithLogoAndLinkPresignedDto extends BasicMailPresigned {

    /**
     * Template of the mail.
     */
    @NotBlank(message = "No template given")
    private String template;

    /**
     * Body of the mail.
     */
    @NotBlank(message = "No text given")
    private String text;

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

    public MailWithLogoAndLinkPresignedDto(String receivers, String receiversCc, String receiversBcc, String subject, String replyTo, List<PresignedUrl> attachments, String template, String text, String bottomBody, String buttonText, String buttonLink) {
        super(receivers, receiversCc, receiversBcc, subject, replyTo, attachments);
        this.template = template;
        this.text = text;
        this.bottomBody = bottomBody;
        this.buttonText = buttonText;
        this.buttonLink = buttonLink;
    }

    public TemplateMailPresigned toTemplateMailPresiged() {
        return new TemplateMailPresigned(
                this.getReceivers(),
                this.getReceiversCc(),
                this.getReceiversBcc(),
                this.getSubject(),
                this.getReplyTo(),
                this.getAttachments(),
                this.getTemplate(),
                Map.of("mail", this)
        );
    }
}
