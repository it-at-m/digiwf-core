package de.muenchen.oss.digiwf.email.integration.domain.model.presigned;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

@Deprecated
@EqualsAndHashCode(callSuper = true)
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class TemplateMailPresigned extends BasicMailPresigned {

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

    public TemplateMailPresigned(String receivers, String receiversCc, String receiversBcc, String subject, String replyTo, List<PresignedUrl> attachments, String template, Map<String, Object> content) {
        super(receivers, receiversCc, receiversBcc, subject, replyTo, attachments);
        this.template = template;
        this.content = content;
    }
}
