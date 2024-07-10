package de.muenchen.oss.digiwf.email.integration.domain.model.presigned;

import de.muenchen.oss.digiwf.email.integration.domain.model.BasicMail;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Deprecated
@EqualsAndHashCode(callSuper = true)
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class BasicMailPresigned extends BasicMail {
    @Valid
    private List<PresignedUrl> attachments;

    public BasicMailPresigned(String receivers, String receiversCc, String receiversBcc, String subject, String replyTo, List<PresignedUrl> attachments) {
        super(receivers, receiversCc, receiversBcc, subject, replyTo);
        this.attachments = attachments;
    }
}
