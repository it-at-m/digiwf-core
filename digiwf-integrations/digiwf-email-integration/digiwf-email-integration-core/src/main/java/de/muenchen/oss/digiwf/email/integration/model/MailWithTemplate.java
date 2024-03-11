package de.muenchen.oss.digiwf.email.integration.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class MailWithTemplate extends Mail{

    /**
     * Template of the mail.
     */
    @NotBlank(message = "No template given")
    private String template;

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

    /**
     * Footer of the mail.
     */
    @NotBlank(message = "No footer given")
    private String footer;

}
