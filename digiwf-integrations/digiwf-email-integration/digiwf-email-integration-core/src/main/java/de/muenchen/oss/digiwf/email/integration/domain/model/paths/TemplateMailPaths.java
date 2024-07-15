package de.muenchen.oss.digiwf.email.integration.domain.model.paths;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class TemplateMailPaths extends BasicMailPaths {

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

    public TemplateMailPaths(String receivers, String receiversCc, String receiversBcc, String subject, String replyTo, String fileContext, String filePaths, String template, Map<String, Object> content) {
        super(receivers, receiversCc, receiversBcc, subject, replyTo, fileContext, filePaths);
        this.template = template;
        this.content = content;
    }
}
