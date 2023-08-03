package de.muenchen.oss.digiwf.email.integration.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * Object contains all the information needed to send a mail.
 */
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Mail {

    /**
     * Receiver addresses of the mail, comma separated.
     */
    @NotBlank(message = "No receivers given")
    private String receivers;

    /**
     * CC-Receiver addresses of the mail, comma separated.
     */
    private String receiversCc;

    /**
     * BCC-Receiver addresses of the mail, comma separated.
     */
    private String receiversBcc;

    /**
     * Subject of the mail.
     */
    @NotBlank(message = "No subject given")
    private String subject;

    /**
     * Body of the mail.
     */
    @NotBlank(message = "No body given")
    private String body;

    /**
     * Reply to address
     */
    private String replyTo;

    @Valid
    private List<PresignedUrl> attachments;

}
