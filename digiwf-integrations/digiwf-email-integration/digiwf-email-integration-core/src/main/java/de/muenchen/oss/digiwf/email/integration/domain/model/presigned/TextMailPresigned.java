package de.muenchen.oss.digiwf.email.integration.domain.model.presigned;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * Object contains all the information needed to send a mail.
 */
@Deprecated
@EqualsAndHashCode(callSuper = true)
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class TextMailPresigned extends BasicMailPresigned {

    /**
     * Body of the mail.
     */
    @NotBlank(message = "No body given")
    private String body;

    public TextMailPresigned(String receivers, String receiversCc, String receiversBcc, String subject, String body, String replyTo, List<PresignedUrl> attachments) {
        super(receivers, receiversCc, receiversBcc, subject, replyTo, attachments);
        this.body = body;
    }

}
