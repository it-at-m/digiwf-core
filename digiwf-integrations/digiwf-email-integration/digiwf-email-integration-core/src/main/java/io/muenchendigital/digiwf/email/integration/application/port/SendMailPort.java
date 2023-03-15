package io.muenchendigital.digiwf.email.integration.application.port;

import org.springframework.mail.javamail.MimeMessagePreparator;

public interface SendMailPort {

    void sendMail(MimeMessagePreparator preparator);

}
