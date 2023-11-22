package de.muenchen.oss.digiwf.email.integration.application.port.out;

import de.muenchen.oss.digiwf.email.model.FileAttachment;
import jakarta.mail.MessagingException;

import java.util.List;

public interface MailPort {

    void sendMail(String receivers, String subject, String body, String replyTo, String receiversCc, String receiversBcc, List<FileAttachment> attachments) throws MessagingException;

}
