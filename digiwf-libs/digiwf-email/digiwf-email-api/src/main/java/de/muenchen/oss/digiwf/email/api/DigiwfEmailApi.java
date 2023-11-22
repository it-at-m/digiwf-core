package de.muenchen.oss.digiwf.email.api;

import de.muenchen.oss.digiwf.email.model.FileAttachment;
import jakarta.mail.MessagingException;

import java.util.List;

public interface DigiwfEmailApi {


    void sendMail(String receivers, String subject, String body, String replyTo) throws MessagingException;

    void sendMail(String receivers, String subject, String body, String replyTo, String receiversCc, String receiversBcc) throws MessagingException;

    void sendMailWithAttachments(String receivers, String subject, String body, String replyTo, List<FileAttachment> attachments) throws MessagingException;

    void sendMailWithAttachments(String receivers, String subject, String body, String replyTo, String receiversCc, String receiversBcc, List<FileAttachment> attachments) throws MessagingException;

}
