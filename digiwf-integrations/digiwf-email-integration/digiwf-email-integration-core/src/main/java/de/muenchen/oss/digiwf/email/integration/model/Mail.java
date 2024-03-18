package de.muenchen.oss.digiwf.email.integration.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

/**
 * Object contains all the information needed to send a mail.
 */
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Mail extends BasicMail {

    /**
     * Body of the mail.
     */
    @NotBlank(message = "No body given")
    private String body;

    public Mail(String receivers, String receiversCc, String receiversBcc, String subject, String body, String replyTo, List<PresignedUrl> attachments) {
        super(receivers,receiversCc,receiversBcc,subject,replyTo,attachments);
        this.body = body;
    }

}
