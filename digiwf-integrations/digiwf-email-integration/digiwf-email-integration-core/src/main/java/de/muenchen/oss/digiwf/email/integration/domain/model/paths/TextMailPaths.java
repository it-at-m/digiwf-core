package de.muenchen.oss.digiwf.email.integration.domain.model.paths;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

/**
 * Object contains all the information needed to send a mail.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class TextMailPaths extends BasicMailPaths {

    /**
     * Body of the mail.
     */
    @NotBlank(message = "No body given")
    private String body;

    public TextMailPaths(String receivers, String receiversCc, String receiversBcc, String subject, String body, String replyTo, String fileContext, String filePath) {
        super(receivers, receiversCc, receiversBcc, subject, replyTo, fileContext, filePath);
        this.body = body;
    }

}
