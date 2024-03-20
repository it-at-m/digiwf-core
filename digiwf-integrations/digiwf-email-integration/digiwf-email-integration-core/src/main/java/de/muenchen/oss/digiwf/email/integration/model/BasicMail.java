package de.muenchen.oss.digiwf.email.integration.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class BasicMail {

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
     * Reply to address
     */
    private String replyTo;

    @Valid
    private List<PresignedUrl> attachments;

}
