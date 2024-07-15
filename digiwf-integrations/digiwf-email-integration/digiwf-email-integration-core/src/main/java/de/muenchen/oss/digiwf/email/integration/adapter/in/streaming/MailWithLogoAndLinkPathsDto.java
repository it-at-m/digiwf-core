package de.muenchen.oss.digiwf.email.integration.adapter.in.streaming;

import de.muenchen.oss.digiwf.email.integration.domain.model.paths.BasicMailPaths;
import de.muenchen.oss.digiwf.email.integration.domain.model.paths.TemplateMailPaths;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class MailWithLogoAndLinkPathsDto extends BasicMailPaths {

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

    public MailWithLogoAndLinkPathsDto(String receivers, String receiversCc, String receiversBcc, String subject, String replyTo, String fileContext, String filePaths, String template, String text, String bottomBody, String buttonText, String buttonLink) {
        super(receivers, receiversCc, receiversBcc, subject, replyTo, fileContext, filePaths);
        this.template = template;
        this.text = text;
        this.bottomBody = bottomBody;
        this.buttonText = buttonText;
        this.buttonLink = buttonLink;
    }

    public TemplateMailPaths toTemplateMailPaths() {
        return new TemplateMailPaths(
                this.getReceivers(),
                this.getReceiversCc(),
                this.getReceiversBcc(),
                this.getSubject(),
                this.getReplyTo(),
                this.getFileContext(),
                this.getFilePaths(),
                this.getTemplate(),
                Map.of("mail", this)
        );
    }
}
