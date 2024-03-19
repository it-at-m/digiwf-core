package de.muenchen.oss.digiwf.email.integration.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;


@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class TemplateMail extends BasicMail {

    /**
     * Template of the mail.
     */
    @NotBlank(message = "No template given")
    private String template;

    /**
     * Bottom body of the mail.
     */
    @NotEmpty(message = "No content given")
    private Map<String, Object> content;

    public TemplateMail(String receivers, String receiversCc, String receiversBcc, String subject, String replyTo, List<PresignedUrl> attachments, String template, Map<String, Object> content) {
        super(receivers, receiversCc, receiversBcc, subject, replyTo, attachments);
        this.template = template;
        this.content = content;
    }
}
